package com.bean;

public class Utilisateur {
    private String email;
    private String motdepasse;
    private String nom;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motdepasse;
    }

    public void setMotDePasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
