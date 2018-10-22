/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.ejb.memory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.facade.AccountFacade;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;

/**
 *
 * @author Łukasz Markiewicz
 */
@Stateful
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountInMemoryFromDatabase extends AbstractInMemoryFromDatabase<Account> implements Serializable {

    transient private AccountFacade facade;

    public AccountInMemoryFromDatabase() {}
    
    @Inject
    public void init(AccountFacade facade) {
        this.facade = facade;
    }
    
    @Override
    protected AccountFacade getFacade() {
        return facade;
    }
}