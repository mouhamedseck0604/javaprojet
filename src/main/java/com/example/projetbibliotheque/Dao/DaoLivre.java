package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Livre;

import java.util.List;

public interface DaoLivre {
    void ajouter(Livre livre);
    void modifier(Livre livre);
    void supprimer(Long id);
    Livre rechercherParIsbn(String isbn);
    List<Livre> rechercherParTitre(String titre);
    List<Livre> rechercherParAuteur(String auteur);
    List<Livre> listerTous();

}
