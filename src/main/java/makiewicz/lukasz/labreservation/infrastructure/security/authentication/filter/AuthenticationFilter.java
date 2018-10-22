/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makiewicz.lukasz.labreservation.infrastructure.security.authentication.filter;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil.getLoginUrl;
import static markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil.isAllowedUrl;
import static markiewicz.lukasz.labreservation.infrastructure.security.util.SecurityUtil.isResourcesUrl;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    
    private ResourceBundle bundle;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        bundle = ResourceBundle.getBundle("Bundle", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    @Override
    public void destroy() {
        bundle = null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if(!isResourcesUrl(request)) {
            System.out.println("isResourcesOrAllowedUrl(request) " + isResourcesOrAllowedUrl(request));
            System.out.println("isLoggedIn(request) " + isLoggedIn(request));
        }
        if(isResourcesOrAllowedUrl(request) || isLoggedIn(request)) {
            chain.doFilter(request, response);
            return;
        }
        response.sendRedirect(getLoginUrl(request));
    }

    private static boolean isResourcesOrAllowedUrl(HttpServletRequest request) {
        return isResourcesUrl(request) || isAllowedUrl(request);
    }

    private static boolean isLoggedIn(HttpServletRequest request) {
        return (request.getRemoteUser() != null);
    }
}
