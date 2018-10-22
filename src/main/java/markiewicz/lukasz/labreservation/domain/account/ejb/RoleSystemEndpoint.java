/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.RoleSystemInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.domain.account.entity.RoleSystem;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RoleSystemEndpoint extends AbstractEndpoint<RoleSystem> implements Serializable {

    private RoleSystemInMemoryFromDatabase databaseInMemory;
    
    @Inject
    public void init(RoleSystemInMemoryFromDatabase databaseInMemory) {
        super.init(new RoleSystem(), databaseInMemory);
        this.databaseInMemory = databaseInMemory;
    }

    public List<RoleSystem> getItemsWithoutAdmin() {
        return databaseInMemory.getItems().stream().filter(a -> !a.getName().equals("admin")).collect(Collectors.toList());
    }

    @Override
    protected String getCallerPrincipalRoleName() {
        return "";
    }

    public List<RoleSystem> getItems() {
        return databaseInMemory.getItems();
    }

    public RoleSystem getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }

    public void setItems(List<RoleSystem> items) {
        databaseInMemory.setItems(items);
    }
}
