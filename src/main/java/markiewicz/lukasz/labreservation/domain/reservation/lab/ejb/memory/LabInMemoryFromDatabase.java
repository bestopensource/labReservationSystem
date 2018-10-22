/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.facade.LabFacade;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;

/**
 *
 * @author Łukasz Markiewicz
 */
@Stateful
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LabInMemoryFromDatabase extends AbstractInMemoryFromDatabase<Lab> implements Serializable {

    private LabFacade facade;

    public LabInMemoryFromDatabase() {}
    
    @Inject
    public void init(LabFacade facade) {
        this.facade = facade;
    }
    
    @Override
    protected LabFacade getFacade() {
        return facade;
    }

    public List<Lab> findReserved(Date startDate, Date endDate) {
        return facade.findReserved(startDate, endDate);
    }

    public List<Lab> findAvailable(Date startDate, Date endDate) {
        return facade.findAvailable(startDate, endDate);
    }

    public Long countComputers(Lab lab) {
        return facade.countComputers(lab);
    }

}