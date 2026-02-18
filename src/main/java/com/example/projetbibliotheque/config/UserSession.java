package com.example.projetbibliotheque.config;

import com.example.projetbibliotheque.entities.Utilisateur;

public class UserSession {
    private static UserSession instance;

    private final Utilisateur utilisateur;

    private UserSession(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public static void init(Utilisateur utilisateur) {
        instance = new UserSession(utilisateur);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void clear() {
        instance = null;
    }



}
