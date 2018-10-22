/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.ejb.memory;

import java.io.Serializable;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.facade.AccountFacade;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.facade.PrivilegeFacade;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.facade.RoleSystemFacade;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.domain.account.entity.Privilege;
import markiewicz.lukasz.labreservation.domain.account.entity.RoleSystem;
import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;

/**
 *
 * @author Łukasz Markiewicz
 */
@Stateful
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RoleSystemInMemoryFromDatabase extends AbstractInMemoryFromDatabase<RoleSystem> implements Serializable {

    private RoleSystemFacade facade;

    public RoleSystemInMemoryFromDatabase() {}
    
    @Inject
    public void init(RoleSystemFacade facade) {
        this.facade = facade;
    }
    
    @Override
    protected RoleSystemFacade getFacade() {
        return facade;
    }

}
