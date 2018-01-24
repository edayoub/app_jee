package com.servlets;

import com.bean.Utilisateur;
import com.forms.ConnexionForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "Connexion" ,urlPatterns = "/connexion")
public class Connexion extends HttpServlet {
    public static final String  ATT_USER                  = "utilisateur";
    public static final String  ATT_FORM                  = "form";
    public static final String  ATT_INTERVALLE_CONNEXIONS = "intervalleConnexions";
    public static final String  ATT_SESSION_USER          = "sessionUtilisateur";
    public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
    public static final String  FORMAT_DATE               = "dd/MM/yyyy HH:mm:ss";
    public static final String  VUE                       = "/WEB-INF/connexion.jsp";
    public static final String  CHAMP_MEMOIRE             = "memoire";
    public static final int     COOKIE_MAX_AGE            = 60 * 60 * 24 * 365;  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnexionForm connexionForm =new ConnexionForm();
        Utilisateur utilisateur = connexionForm.connecterUtilisateur(request);
        HttpSession session = request.getSession();
        if(!connexionForm.getErreurs().isEmpty()){
            session.setAttribute(ATT_SESSION_USER,utilisateur);
        }else{
            session.setAttribute(ATT_SESSION_USER,null);
        }
        /* Si et seulement si la case du formulaire est cochée */
        if ( request.getParameter( CHAMP_MEMOIRE ) != null ) {
            /* Récupération de la date courante */
            Date date = new Date();
            /* Formatage de la date et conversion en texte */
            SimpleDateFormat formatter = new SimpleDateFormat( FORMAT_DATE );
            String dateDerniereConnexion = formatter.format( date );
            /* Création du cookie, et ajout à la réponse HTTP */
            setCookie( response, COOKIE_DERNIERE_CONNEXION,dateDerniereConnexion, COOKIE_MAX_AGE );
        } else {
            /* Demande de suppression du cookie du navigateur */
            setCookie( response, COOKIE_DERNIERE_CONNEXION, "", 0 );
        }

        request.setAttribute( ATT_FORM, connexionForm );
        request.setAttribute( ATT_USER, utilisateur );
        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }

    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dernnierConnexion =getCookieValue(request,COOKIE_DERNIERE_CONNEXION);
        if(dernnierConnexion!=null) {
            Date date = new Date();
            SimpleDateFormat dt = new SimpleDateFormat(FORMAT_DATE);
            try {
                long dif= date.getTime()-dt.parse(dernnierConnexion).getTime();
                Date date1 = new Date(dif);
                String intervalleConnexions = dt.format(date1);
                System.out.println(intervalleConnexions);
                request.setAttribute(ATT_INTERVALLE_CONNEXIONS, intervalleConnexions);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }

    private static String getCookieValue(HttpServletRequest request, String nom) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
