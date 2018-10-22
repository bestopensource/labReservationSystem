/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.ejb;

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
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.ComputerReservation;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.LabReservationInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.domain.reservation.util.AccountSessionUtil;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ComputerReservationEndpoint extends AbstractEndpoint<ComputerReservation> implements Serializable {
    
    private ComputerReservationInMemoryFromDatabase databaseInMemory;
    private LabReservationInMemoryFromDatabase labReservationInMemoryFromDatabase;
    private AccountEndpoint accountEndpoint;
    @Resource
    protected SessionContext sessionContext;
    
    @Inject
    public void init(ComputerReservationInMemoryFromDatabase databaseInMemory, 
            LabReservationInMemoryFromDatabase labReservationInMemoryFromDatabase, 
            AccountEndpoint accountEndpoint) {
        
        super.init(new ComputerReservation(), databaseInMemory);
        this.databaseInMemory = databaseInMemory;
        this.labReservationInMemoryFromDatabase = labReservationInMemoryFromDatabase;
        this.accountEndpoint = accountEndpoint;
    }

    @Override
    public String create() {
        ComputerReservation computerReservation = super.getSelected();
        if(computerReservation.getAccount() == null) {
            Optional<Account> account = getAccount();
            if(account.isPresent())
                computerReservation.setAccount(account.get());
        }
        computerReservation.setArchived(false);
        computerReservation.setCanceled(false);
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
        ComputerReservation computerReservation = super.getSelected();
        computerReservation.setCanceled(true);
        computerReservation.setArchived(true);
        super.update();
        return "";
    }

    public List<ComputerReservation> findArchivedForUserCurrent(boolean archived) {
        labReservationInMemoryFromDatabase.updateOutOfDate();
        databaseInMemory.updateOutOfDate();
        Optional<Account> accounts = getAccount();
        if(accounts.isPresent())
            return databaseInMemory.findArchivedFor(accounts.get().getId(), archived);
        return Collections.emptyList();
    }
    
    public List<ComputerReservation> findCanceledForUserCurrent(boolean canceled) {
        labReservationInMemoryFromDatabase.updateOutOfDate();
        databaseInMemory.updateOutOfDate();
        Optional<Account> accounts = getAccount();
        if(accounts.isPresent())
            return databaseInMemory.findCanceledFor(accounts.get().getId(), canceled);
        return Collections.emptyList();
    }


    
    public List<ComputerReservation> findCanceledAll(boolean canceled) {
        labReservationInMemoryFromDatabase.updateOutOfDate();
        databaseInMemory.updateOutOfDate();
        return databaseInMemory.findCanceledAll(canceled);
    }
    
    public List<ComputerReservation> findArchivedAll(boolean archived) {
        labReservationInMemoryFromDatabase.updateOutOfDate();
        databaseInMemory.updateOutOfDate();
        return databaseInMemory.findArchivedAll(archived);
    }

    public List<ComputerReservation> getItems() {
        return databaseInMemory.getItems();
    }

    public ComputerReservation getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }

    private Optional<Account> getAccount() {
        return accountEndpoint.getAccountByUsername(AccountSessionUtil.getUsernameCurrent(sessionContext));
    }
}
