/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.facade;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import markiewicz.lukasz.labreservation.schema.ejb.memory.facade.AbstractFacade;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import markiewicz.lukasz.labreservation.domain.reservation.util.DateFormatUtil;
import markiewicz.lukasz.labreservation.infrastructure.metrics.logging.LoggingInterceptorBinding;

@Stateless
@LoggingInterceptorBinding
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class LabFacade extends AbstractFacade<Lab> {

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

    public LabFacade() {
        super(Lab.class);
    }
    
    public List<Lab> findReserved(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("Lab.findReserved");
        query.setParameter("startDate", DateFormatUtil.formatted(startDate));
        query.setParameter("endDate", DateFormatUtil.formatted(endDate));
        return query.getResultList();
    }
    
    public List<Lab> findAvailable(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("Lab.findAvailable");
        query.setParameter("startDate", DateFormatUtil.formatted(startDate));
        query.setParameter("endDate", DateFormatUtil.formatted(endDate));
        return query.getResultList();
    }

    public Long countComputers(Lab lab) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root<Computer> root = criteriaQuery.from(Computer.class);
        criteriaQuery.select(criteriaBuilder.count(root)).where(criteriaBuilder.equal(root.get("lab"), lab));
        Query query = entityManager.createQuery(criteriaQuery);
        return (Long) query.getSingleResult();
    }
}