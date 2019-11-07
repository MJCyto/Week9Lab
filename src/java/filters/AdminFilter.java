/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author mjjmk
 */
public class AdminFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        // code that is executed before the servlet
        
        HttpServletRequest hsr = (HttpServletRequest)request;
        HttpSession session = hsr.getSession();
        
        UserService us = new UserService();
        HttpServletResponse hsre = (HttpServletResponse)response;
        
        if (session.getAttribute("email") == null) {
            hsre.sendRedirect("login");
            return;
        } 
        
        try {
            User currUser = us.get((String) session.getAttribute("email"));
            if (!(currUser.getRole().getRoleID() == 1 || currUser.getRole().getRoleID() == 3)){
            hsre.sendRedirect("home");
            return;
        }
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.INFO, (String) session.getAttribute("email"), ex);
            hsre.sendRedirect("login");
            return;
        }
        
        
         // allow the user to access the servlet
         chain.doFilter(request, response);
         
         // code that is executed after the servlet
       
    }
    public void destroy() {
        
    }

    public void init(FilterConfig filterConfig) {  
        
    }
}
