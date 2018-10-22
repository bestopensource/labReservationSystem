/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.ejb;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.ejb.AccountEndpoint;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.ComputerReservationInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.LabReservationInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.LabReservation;
import markiewicz.lukasz.labreservation.domain.reservation.util.AccountSessionUtil;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LabReservationEndpoint extends AbstractEndpoint<LabReservation> implements Serializable {

    private LabReservationInMemoryFromDatabase databaseInMemory;
    private ComputerReservationInMemoryFromDatabase computerReservationInMemoryFromDatabase;
    private AccountEndpoint accountEndpoint;
    @Resource
    protected SessionContext sessionContext;
    private Lab lab;

    
    @Inject
    public void init(LabReservationInMemoryFromDatabase databaseInMemory, 
                        ComputerReservationInMemoryFromDatabase computerReservationInMemoryFromDatabase, 
                        AccountEndpoint accountEndpoint) {
        
        super.init(new LabReservation(), databaseInMemory);
        this.databaseInMemory = databaseInMemory;
        this.computerReservationInMemoryFromDatabase = computerReservationInMemoryFromDatabase;
        this.accountEndpoint = accountEndpoint;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }
    
    @Override
    public String create() {
        LabReservation labReservation = super.getSelected();
        if(labReservation.getAccount() == null) {
            Optional<Account> account = getAccount();
            if(account.isPresent())
                labReservation.setAccount(account.get());
        }
        labReservation.setArchived(false);
        labReservation.setCanceled(false);
        return super.create();
    }

    @Override
    public String getCallerPrincipalRoleName() {
        Optional<Account> account = getAccount();
        if(account.isPresent())
            return account.get().getRoleSystem().getName();
        return "";
    }
    
    public String cancel() {
        LabReservation labReservation = super.getSelected();
        labReservation.setCanceled(true);
        labReservation.setArchived(true);
        super.update();
        return "";
    }

    public List<LabReservation> findArchivedForUserCurrent(boolean archived) {
        databaseInMemory.updateOutOfDate();
        computerReservationInMemoryFromDatabase.updateOutOfDate();
        Optional<Account> account = getAccount();
        if(account.isPresent())
            return databaseInMemory.findArchivedFor(account.get().getId(), archived);
        return Collections.emptyList();
    }
    
    public List<LabReservation> findCanceledForUserCurrent(boolean canceled) {
        databaseInMemory.updateOutOfDate();
        computerReservationInMemoryFromDatabase.updateOutOfDate();
        Optional<Account> account = getAccount();
        if(account.isPresent())
            return databaseInMemory.findCanceledFor(account.get().getId(), canceled);
        return Collections.emptyList();
    }
    
    public List<LabReservation> findCanceledAll(boolean canceled) {
        databaseInMemory.updateOutOfDate();
        computerReservationInMemoryFromDatabase.updateOutOfDate();
        return databaseInMemory.findCanceledAll(canceled);
    }
    
    public List<LabReservation> findArchivedAll(boolean archived) {
        databaseInMemory.updateOutOfDate();
        computerReservationInMemoryFromDatabase.updateOutOfDate();
        return databaseInMemory.findArchivedAll(archived);
    }

    public void updateOutOfDate() {
        databaseInMemory.updateOutOfDate();
    }

    public List<LabReservation> getItems() {
        return databaseInMemory.getItems();
    }

    public LabReservation getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }
    
    private Optional<Account> getAccount() {
        return accountEndpoint.getAccountByUsername(AccountSessionUtil.getUsernameCurrent(sessionContext));
    }
}
