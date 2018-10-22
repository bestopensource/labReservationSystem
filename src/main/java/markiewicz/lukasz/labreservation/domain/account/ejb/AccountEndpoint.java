/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.ejb;

import java.io.Serializable;
import java.util.List;
import markiewicz.lukasz.labreservation.domain.account.ejb.memory.AccountInMemoryFromDatabase;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.PasswordHash;
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.schema.util.JsfTools;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountEndpoint extends AbstractEndpoint<Account> implements Serializable {

    private transient PasswordHash hash;
    private ResourceBundle bundle;
    private AccountInMemoryFromDatabase databaseInMemory;

    @Inject
    public void init(AccountInMemoryFromDatabase databaseInMemory) {
        super.init(new Account(), databaseInMemory);
        this.hash = new Pbkdf2PasswordHashImpl();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        this.bundle = ResourceBundle.getBundle("Bundle", viewRoot.getLocale());
        this.databaseInMemory = databaseInMemory;
    }

    @Override
    public String create() {
        Account accountFromViewSelected = super.getSelected();
        if(accountFromViewSelected.getUsername() == null) {
            JsfTools.addErrorMessage(bundle.getString("CreateAccountUsernameEmpty"));
            return "";
        }
        if(accountFromViewSelected.getEmail() == null) {
            JsfTools.addErrorMessage(bundle.getString("CreateAccountEmailEmpty"));
            return "";
        }
        if(accountFromViewSelected.getPassword() == null) {
            JsfTools.addErrorMessage(bundle.getString("CreateAccountPasswordEmpty"));
            return "";
        }
        if(accountFromViewSelected.getRoleSystem() == null) {
            JsfTools.addErrorMessage(bundle.getString("CreateAccountRoleSystemEmpty"));
            return "";
        }

        Optional<Account> accountFromJpa = getAccountByUsername(accountFromViewSelected.getUsername());
        if(accountFromJpa.isPresent()) {
            JsfTools.addErrorMessage(bundle.getString("CreateAccountUsernameExists"));
            return "";
        }
        accountFromJpa = getAccountByEmail(accountFromViewSelected.getEmail());
        if(accountFromJpa.isPresent()) {
            JsfTools.addErrorMessage(bundle.getString("CreateAccountEmailExists"));
            return "";
        }
        accountFromViewSelected.setActivated(false);
        accountFromViewSelected.setPassword(hash.generate(accountFromViewSelected.getPassword().toCharArray()));
        return super.create();
    }
    
    public Optional<Account> getAccountByEmail(String email) {
        return databaseInMemory.getItems().stream().filter(a -> a.getEmail().equals(email)).findFirst();
    }
    
    public Optional<Account> getAccountByUsername(String username) {
        return databaseInMemory.getItems().stream().filter(a -> a.getUsername().equals(username)).findFirst();
    }
    
    @Override
    public void update() {
        Account account = super.getSelected();
        String password = account.getPassword();
        account.setPassword(hash.generate(password.toCharArray()));
        super.update();
    }

    @Override
    protected String getCallerPrincipalRoleName() {
        return "";
    }

    public String activate() {
        Account account = super.getSelected();
        account.setActivated(true);
        super.update();
        return "";
    }
    
    public String deactivate() {
        Account account = super.getSelected();
        account.setActivated(false);
        super.update();
        return "";
    }
    
    public boolean isActvated() {
        return super.getSelected().isActivated();
    }

    public List<Account> getItems() {
        return databaseInMemory.getItems();
    }

    public Account getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }

    public void setItems(List<Account> items) {
        databaseInMemory.setItems(items);
    }
}
