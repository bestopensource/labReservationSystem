/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.facade.AccountFacade;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.facade.ComputerFacade;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.facade.ComputerReservationFacade;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.ComputerReservation;
import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;

/**
 *
 * @author Łukasz Markiewicz
 */
@Stateful
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ComputerReservationInMemoryFromDatabase extends AbstractInMemoryFromDatabase<ComputerReservation> implements Serializable {

    private ComputerReservationFacade facade;

    public ComputerReservationInMemoryFromDatabase() {}
    
    @Inject
    public void init(ComputerReservationFacade facade) {
        this.facade = facade;
    }
    
    @Override
    protected ComputerReservationFacade getFacade() {
        return facade;
    }

    public List<ComputerReservation> findArchivedFor(long accountId, boolean archived) {
        return facade.findArchivedFor(accountId, archived);
    }

    public List<ComputerReservation> findCanceledFor(long accountId, boolean canceled) {
        return facade.findCanceledFor(accountId, canceled);
    }

    public List<ComputerReservation> findArchivedAll(boolean archived) {
        return facade.findArchivedAll(archived);
    }

    public List<ComputerReservation> findCanceledAll(boolean canceled) {
        return facade.findCanceledAll(canceled);
    }

    public void updateOutOfDate() {
        facade.updateOutOfDate();
    }
}