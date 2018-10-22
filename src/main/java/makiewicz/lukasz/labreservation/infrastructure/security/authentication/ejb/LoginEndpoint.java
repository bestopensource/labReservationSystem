/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makiewicz.lukasz.labreservation.infrastructure.security.authentication.ejb;

import java.util.Optional;
import java.util.ResourceBundle;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import markiewicz.lukasz.labreservation.domain.account.ejb.AccountEndpoint;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.domain.account.entity.RoleSystem;
import markiewicz.lukasz.labreservation.domain.reservation.util.AccountSessionUtil;
import markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil;
import markiewicz.lukasz.labreservation.schema.util.JsfTools;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LoginEndpoint {

    private ResourceBundle bundle;
    private SecurityContext securityContext;
    private AccountEndpoint accountEndpoint;
    private String username, password;

    @Inject
    public void init(SecurityContext securityContext, AccountEndpoint accountEndpoint) {
        this.securityContext = securityContext;
        this.accountEndpoint = accountEndpoint;
        this.bundle = ResourceBundle.getBundle("Bundle", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public String login() {
        if(isLoggedIn()) {
            JsfTools.addErrorMessage(bundle.getString("YouAreLoggedIn"));
            return "";
        }  
        if(isUsername()) {
            JsfTools.addErrorMessage(bundle.getString("UserNameRequired"));
            return "";
        }
        if(isPassword()) {
            JsfTools.addErrorMessage(bundle.getString("PasswordRequired"));
            return "";
        }
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
           JsfTools.getRequestFrom(),
           JsfTools.getResponseFrom(),
           withParams().credential(credential));
        
        if(isLoggedInThisAuthentication(status))
            return executeForLoggedIn();
        JsfTools.addErrorMessage(bundle.getString("AuthenticationFailed"));
        return "";
    }

    private static boolean isLoggedInThisAuthentication(AuthenticationStatus status) {
        return status == AuthenticationStatus.SUCCESS;
    }

    private boolean isLoggedIn() {
        return SecurityUtil.isLoggedIn(securityContext);
    }

    private boolean isPassword() {
        return password == null || password.isEmpty();
    }

    private boolean isUsername() {
        return username == null || username.isEmpty();
    }

    private String executeForLoggedIn() {
        Optional<Account> account = getAccountCurrent();
        if(!account.isPresent()) {
            JsfTools.addErrorMessage(bundle.getString("NoAccount"));
            return "";
        }
        Account accountFromJpa = account.get();
        if(!accountFromJpa.isActivated()) {
            JsfTools.addErrorMessage(bundle.getString("AccountIsNotActivated"));
            return "";
        }
        RoleSystem roleSystem = accountFromJpa.getRoleSystem();
        if(roleSystem == null) {
            JsfTools.addErrorMessage(bundle.getString("AccountHasNoSetRole"));
            return "";
        }
        return roleSystem.getName();
    }

    private Optional<Account> getAccountCurrent() {
        String usernameCurrent = AccountSessionUtil.getUsernameCurrent(securityContext);
        return accountEndpoint.getAccountByUsername(usernameCurrent);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
