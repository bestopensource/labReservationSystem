/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.infrastructure.security.util;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import makiewicz.lukasz.labreservation.infrastructure.security.authentication.view.LogoutCotroller;

public class SecurityUtil {
    
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/faces/login/Login.xhtml", "/faces/index.xhtml", "")
    ));
    
    public static boolean isAllowedUrl(HttpServletRequest request) {
        String path = getPath(request);
        if(!isResourcesUrl(request))
            System.out.println("path: " + path);
        return ALLOWED_PATHS.contains(path);
    }

    private static String getPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        int length = contextPath.length();
        return requestURI.substring(length).replaceAll("[/]+$", "");
    }
    
    public static boolean isResourcesUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("javax.faces.resource");
    }
    
    public static String getLoginUrl(HttpServletRequest request) {
        return request.getContextPath() + "/faces/login/Login.xhtml";
    }
    
    public static boolean logout(HttpServletRequest request) {
        System.out.println("logout()");
        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(LogoutCotroller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        request.getSession().invalidate();
        return true;
    }  
    
    public static boolean isLoggedIn(SecurityContext securityContext) {
        Principal principal = securityContext.getCallerPrincipal();
        return principal != null && principal.getName() != null 
                && !"Anonymous".equalsIgnoreCase(principal.getName());
    }
}
