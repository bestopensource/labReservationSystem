/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.facade.LabReservationFacade;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.LabReservation;
import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;

/**
 *
 * @author Łukasz Markiewicz
 */
@Stateful
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LabReservationInMemoryFromDatabase extends AbstractInMemoryFromDatabase<LabReservation> implements Serializable {

    private LabReservationFacade facade;

    public LabReservationInMemoryFromDatabase() {}
    
    @Inject
    public void init(LabReservationFacade facade) {
        this.facade = facade;
    }
    
    @Override
    protected LabReservationFacade getFacade() {
        return facade;
    }

    public List<LabReservation> findArchivedFor(long accountId, boolean archived) {
        return facade.findArchivedFor(accountId, archived);
    }

    public List<LabReservation> findCanceledFor(long accountId, boolean canceled) {
        return facade.findCanceledFor(accountId, canceled);
    }

    public List<LabReservation> findArchivedAll(boolean archived) {
        return facade.findArchivedAll(archived);
    }

    public List<LabReservation> findCanceledAll(boolean canceled) {
        return facade.findCanceledAll(canceled);
    }

    public void updateOutOfDate() {
        facade.updateOutOfDate();
    }
}