/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.facade;

import java.util.Date;
import java.util.List;
import markiewicz.lukasz.labreservation.schema.ejb.memory.facade.AbstractFacade;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import markiewicz.lukasz.labreservation.domain.reservation.util.DateFormatUtil;
import markiewicz.lukasz.labreservation.infrastructure.metrics.logging.LoggingInterceptorBinding;

@Stateless
@LoggingInterceptorBinding
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ComputerFacade extends AbstractFacade<Computer> {

    @PersistenceContext(unitName = "makiewicz.lukasz_LabReservation7_war_1.0.1PU")
    private EntityManager entityManager;
    
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public ComputerFacade() {
        super(Computer.class);
    }
    
    public List<Computer> findReserved(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("Computer.findReserved");
        query.setParameter("startDate", DateFormatUtil.formatted(startDate));
        query.setParameter("endDate", DateFormatUtil.formatted(endDate));
        return query.getResultList();
    }
    
    public List<Computer> findAvailable(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("Computer.findAvailable");
        query.setParameter("startDate", DateFormatUtil.formatted(startDate));
        query.setParameter("endDate", DateFormatUtil.formatted(endDate));
        return query.getResultList();
    }
}
