/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.facade;

import java.util.List;
import markiewicz.lukasz.labreservation.schema.ejb.memory.facade.AbstractFacade;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.ComputerReservation;
import markiewicz.lukasz.labreservation.infrastructure.metrics.logging.LoggingInterceptorBinding;

@Stateless
@LoggingInterceptorBinding
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ComputerReservationFacade extends AbstractFacade<ComputerReservation> {

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
    public ComputerReservationFacade() {
        super(ComputerReservation.class);
    }
    
    public List<ComputerReservation> findArchivedFor(long accountId, boolean archived) {
        Query query = entityManager.createNamedQuery("ComputerReservation.findArchivedFor");
        query.setParameter("account_id", accountId);
        query.setParameter("archived", archived);
        return query.getResultList();
    }
    
    public List<ComputerReservation> findCanceledFor(long accountId, boolean canceled) {
        Query query = entityManager.createNamedQuery("ComputerReservation.findCanceledFor");
        query.setParameter("account_id", accountId);
        query.setParameter("canceled", canceled);
        return query.getResultList();
    }
    
    public List<ComputerReservation> findArchivedAll(boolean archived) {
        Query query = entityManager.createNamedQuery("ComputerReservation.findArchivedAll");
        query.setParameter("archived", archived);
        return query.getResultList();
    }
    
    public List<ComputerReservation> findCanceledAll(boolean canceled) {
        Query query = entityManager.createNamedQuery("ComputerReservation.findCanceledAll");
        query.setParameter("canceled", canceled);
        return query.getResultList();
    }
    
    public void updateOutOfDate() {
        Query query = entityManager.createNamedQuery("ComputerReservation.updateOutOfDate");
        query.executeUpdate();
    }
}
