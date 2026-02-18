package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Emprunt;

import java.util.List;

public interface DaoEmprunt {
    void ajouter(Emprunt emprunt);
    void modifier(Emprunt emprunt);
    void supprimer(Emprunt emprunt);
    Emprunt trouverParId(Long id);
    List<Emprunt> listerTous();

    double calculerPenalite(Emprunt e);
}
