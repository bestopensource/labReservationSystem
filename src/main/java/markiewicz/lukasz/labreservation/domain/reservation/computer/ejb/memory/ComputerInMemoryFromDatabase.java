/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.facade.ComputerFacade;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;

/**
 *
 * @author Łukasz Markiewicz
 */
@Stateful
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ComputerInMemoryFromDatabase extends AbstractInMemoryFromDatabase<Computer> implements Serializable {

    private ComputerFacade facade;

    public ComputerInMemoryFromDatabase() {}
    
    @Inject
    public void init(ComputerFacade facade) {
        this.facade = facade;
    }
    
    @Override
    protected ComputerFacade getFacade() {
        return facade;
    }

    public List<Computer> findReserved(Date startDate, Date endDate) {
        return facade.findReserved(startDate, endDate);
    }

    public List<Computer> findAvailable(Date startDate, Date endDate) {
        return facade.findAvailable(startDate, endDate);
    }
}