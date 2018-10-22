/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.util;

import javax.ejb.SessionContext;
import javax.security.enterprise.SecurityContext;

public class AccountSessionUtil {
    public static String getUsernameCurrent(SessionContext sessionContext) throws IllegalStateException {
        return sessionContext.getCallerPrincipal().getName();
    }
    public static String getUsernameCurrent(SecurityContext securityContext) throws IllegalStateException {
        return securityContext.getCallerPrincipal().getName();
    }
}
