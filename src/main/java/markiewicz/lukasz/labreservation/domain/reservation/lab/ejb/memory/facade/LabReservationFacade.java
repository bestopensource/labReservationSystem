/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.facade;

import java.util.List;
import markiewicz.lukasz.labreservation.schema.ejb.memory.facade.AbstractFacade;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.LabReservation;
import markiewicz.lukasz.labreservation.infrastructure.metrics.logging.LoggingInterceptorBinding;

@Stateless
@LoggingInterceptorBinding
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class LabReservationFacade extends AbstractFacade<LabReservation> {

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

    public LabReservationFacade() {
        super(LabReservation.class);
    }
        
    public List<LabReservation> findArchivedFor(long accountId, boolean archived) {
        Query query = entityManager.createNamedQuery("LabReservation.findArchivedFor");
        query.setParameter("account_id", accountId);
        query.setParameter("archived", archived);
        return query.getResultList();
    }
    
    public List<LabReservation> findCanceledFor(long accountId, boolean canceled) {
        Query query = entityManager.createNamedQuery("LabReservation.findCanceledFor");
        query.setParameter("account_id", accountId);
        query.setParameter("canceled", canceled);
        return query.getResultList();
    }
    
    public List<LabReservation> findArchivedAll(boolean archived) {
        Query query = entityManager.createNamedQuery("LabReservation.findArchivedAll");
        query.setParameter("archived", archived);
        return query.getResultList();
    }
    
    public List<LabReservation> findCanceledAll(boolean canceled) {
        Query query = entityManager.createNamedQuery("LabReservation.findCanceledAll");
        query.setParameter("canceled", canceled);
        return query.getResultList();
    }
    
    public void updateOutOfDate() {
        Query query = entityManager.createNamedQuery("LabReservation.updateOutOfDate");
        query.executeUpdate();
    }
}
