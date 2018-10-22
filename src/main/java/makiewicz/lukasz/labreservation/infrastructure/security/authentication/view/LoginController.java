/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makiewicz.lukasz.labreservation.infrastructure.security.authentication.view;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import makiewicz.lukasz.labreservation.infrastructure.security.authentication.ejb.LoginEndpoint;

@Named("loginController")
@RequestScoped
public class LoginController implements Serializable {

    private final LoginEndpoint loginEndpoint;

    @Inject
    public LoginController(LoginEndpoint loginEndpoint) {
        this.loginEndpoint = loginEndpoint;
    }

    public String login() {
        return loginEndpoint.login();
    }
    
    public String getUsername() {
        return loginEndpoint.getUsername();
    }

    public void setUsername(String username) {
        loginEndpoint.setUsername(username);
    }

    public String getPassword() {
        return loginEndpoint.getPassword();
    }

    public void setPassword(String password) {
        loginEndpoint.setPassword(password);
    }
}
