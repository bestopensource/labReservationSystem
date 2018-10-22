/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makiewicz.lukasz.labreservation.infrastructure.security.authentication.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil;

@Named("logoutController")
@RequestScoped
public class LogoutCotroller {

    private final HttpServletRequest request;

    @Inject
    public LogoutCotroller(HttpServletRequest request) {
        this.request = request;
    }
    
    public String logout() {
        if(SecurityUtil.logout(request)) {
            return "logout";
        }
        return "";
    }
}
