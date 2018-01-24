package com.servlets;

import com.bean.Utilisateur;
import com.forms.InscriptionForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Inscription",urlPatterns = "/inscription")
public class Inscription extends HttpServlet {
    public static final String VUE = new String("/WEB-INF/inscription.jsp");
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InscriptionForm form =new InscriptionForm();
        Utilisateur utilisateur =form.inscrireUtilisateur(request);
        request.setAttribute(ATT_FORM,form);
        request.setAttribute(ATT_USER,utilisateur);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
