package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Deconnexion ",urlPatterns = "/deconnection")
public class Deconnexion extends HttpServlet {
    private static final String URL_REDIRECTION ="/connexion";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        try {
            session.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(URL_REDIRECTION);
    }
}
