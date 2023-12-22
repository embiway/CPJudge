package com.judge;

import com.embi.User;
import com.judge.server.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    AuthManager authManager;

    public AuthManager getAuthManager() {
        return authManager;
    }

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        String path = servletRequest.getRequestURI();
        if (path.contains("/signup/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = (String) servletRequest.getHeader("Authorization");
        User user = new User();
        user.setToken(token);

        try {
            boolean isUserAuthenticated = authManager.authenticateUser(user);
            if (!isUserAuthenticated) throw new Exception("Token expired!!");
        } catch (Exception e) {
            user.setUserName((String) servletRequest.getHeader("userName"));
            user.setPassword(((String) servletRequest.getHeader("password")).toCharArray());
            try {
                String newToken = authManager.login(user);
                servletResponse.addHeader("Authorization", newToken);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception ex) {
                throw new RuntimeException("Authentication Failed. Please sign up or login!!!!!");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
