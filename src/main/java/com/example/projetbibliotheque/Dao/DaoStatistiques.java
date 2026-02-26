package com.example.projetbibliotheque.Dao;

public interface DaoStatistiques {
    long totalAdherents();

    long totalLivres();

    long empruntsDuMois();

    long empruntsEnRetard();
}
