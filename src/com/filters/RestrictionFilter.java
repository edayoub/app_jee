package com.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RestrictionFilter"/*,urlPatterns = "/*"*/)
public class RestrictionFilter implements Filter {
    public static final String ACCES_CONNEXION  = "/connexion";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request =(HttpServletRequest) req;
        HttpServletResponse  response=(HttpServletResponse) resp;
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if ( chemin.startsWith( "/inc" ) ) {
            chain.doFilter( request, response );
            return;
        }
        HttpSession session =request.getSession();
        if(session.getAttribute(ATT_SESSION_USER)==null){
            request.getRequestDispatcher(ACCES_CONNEXION).forward(request,response);
        }else {
        chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
