/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.ejb.memory.facade;

import markiewicz.lukasz.labreservation.domain.account.entity.Privilege;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import markiewicz.lukasz.labreservation.schema.ejb.memory.facade.AbstractFacade;
import markiewicz.lukasz.labreservation.infrastructure.metrics.logging.LoggingInterceptorBinding;

@Stateless
@LoggingInterceptorBinding
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PrivilegeFacade extends AbstractFacade<Privilege> {

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

    public PrivilegeFacade() {
        super(Privilege.class);
    }
}
