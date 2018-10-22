/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.infrastructure.security.authorization.filter;

import java.io.IOException;
import java.util.Optional;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import markiewicz.lukasz.labreservation.domain.account.ejb.AccountEndpoint;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil;
import static markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil.getLoginUrl;
import static markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil.isAllowedUrl;
import static markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil.isResourcesUrl;

@WebFilter(urlPatterns = "/*")
public class AuthorizationFilter implements Filter {

    private final AccountEndpoint endpoint;

    @Inject
    public AuthorizationFilter(AccountEndpoint facade) {
        this.endpoint = facade;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if(!isResourcesUrl(request)) {
            System.out.println("isAccessByRequest(request) " + isAccessByRequest(request));
            System.out.println("isAccessByAccount(request) " + isAccessByAccount(request));
        }
        if(isAccessByRequest(request) || isAccessByAccount(request)) {
            chain.doFilter(request, response);
            return;
        }
        logoutAndRedirect(request, response);
    }

    private static boolean isStartPageAndNoLoggedIn(HttpServletRequest request) {
        return isAllowedUrl(request) && !isLoggedIn(request);
    }
    
    private boolean isAccessByRequest(HttpServletRequest request) {
        return isResourcesUrl(request) || isStartPageAndNoLoggedIn(request);
    }
    
    private boolean isAccessByAccount(HttpServletRequest request) {
        if(!isLoggedIn(request))
            return false;
        Optional<Account> accountFromDatabase = endpoint.getAccountByUsername(getNameAccountCurrentByRequest(request));
        if(accountFromDatabase.isPresent()) {
            Account accountCurrent = accountFromDatabase.get();
            String roleSystemName = getNameRoleSystem(accountCurrent);
            if(!isResourcesUrl(request)) {
                System.out.println("roleSystemName: " + roleSystemName);
                System.out.println("isActivatedAccount(accountCurrent) : " + isActivatedAccount(accountCurrent));
                System.out.println("isAccessToResourceForAccount(request, roleSystemName) : " + isAccessToResourceForAccount(request, roleSystemName));
            }
            return isAdmin(roleSystemName) || (isActivatedAccount(accountCurrent) 
                    && isAccessToResourceForAccount(request, roleSystemName));
        }
        return false;
    }

    private static boolean isActivatedAccount(Account accountCurrent) {
        return accountCurrent.isActivated();
    }

    private static String getNameRoleSystem(Account accountCurrent) {
        return accountCurrent.getRoleSystem().getName();
    }

    private static String getNameAccountCurrentByRequest(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    private static boolean isAdmin(String roleName) {
        return roleName.equals("admin");
    }
    
    private static boolean isAccessToResourceForAccount(HttpServletRequest request, String roleName) {
        String uri = request.getRequestURI();
        return uri.contains("/" + roleName + "/");
    }
    
    private static boolean isLoggedIn(HttpServletRequest request) {
        return getNameAccountCurrentByRequest(request) != null;
    }

    private void logoutAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String loginURI = getLoginUrl(request);
        SecurityUtil.logout(request);
        response.sendRedirect(loginURI);
    }
}
