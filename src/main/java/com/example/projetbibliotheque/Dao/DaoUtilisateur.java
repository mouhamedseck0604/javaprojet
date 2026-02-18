package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Utilisateur;

import java.util.List;

public interface DaoUtilisateur {
    Utilisateur authentifier(String login, String password);
    void creer(Utilisateur user);
    void modifier(Utilisateur user);
    void supprimer(Long id);
    Utilisateur trouverParLogin(String login);

    List<Utilisateur> listerToutes();
}
