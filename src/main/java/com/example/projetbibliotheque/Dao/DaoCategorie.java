package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Categorie;

import java.util.List;

public interface DaoCategorie {
    void ajouter(Categorie categorie);
    void modifier(Categorie categorie);
    void supprimer(Long id);
    Categorie rechercherParLibelle(String libelle);
    List<Categorie> listerToutes();

}
