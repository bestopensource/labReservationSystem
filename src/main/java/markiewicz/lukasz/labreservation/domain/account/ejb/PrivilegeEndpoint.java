/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.ejb;

import java.io.Serializable;
import java.util.List;
import markiewicz.lukasz.labreservation.domain.account.entity.Privilege;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.PrivilegeInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PrivilegeEndpoint extends AbstractEndpoint<Privilege> implements Serializable  {
    
    private PrivilegeInMemoryFromDatabase databaseInMemory;
    
    @Inject
    public void init(PrivilegeInMemoryFromDatabase databaseInMemory) {
        super.init(new Privilege(), databaseInMemory);
        this.databaseInMemory = databaseInMemory;
    }

    @Override
    protected String getCallerPrincipalRoleName() {
        return "";
    }

    public List<Privilege> getItems() {
        return databaseInMemory.getItems();
    }

    public Privilege getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }

    public void setItems(List<Privilege> items) {
        databaseInMemory.setItems(items);
    }
}
