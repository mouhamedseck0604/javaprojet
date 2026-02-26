package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Adherent;

import java.util.List;

public interface DaoAdherent {
    List<Adherent> listerToutes();

    void ajouterAdherent(Adherent adherent);

    void modifierAdherent(Adherent adherent);

    void supprimerAdherent(Long id);

}
