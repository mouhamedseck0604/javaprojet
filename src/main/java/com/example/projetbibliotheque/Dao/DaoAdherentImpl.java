package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Adherent;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoAdherentImpl implements DaoAdherent{
    private final EntityManager em;

    public DaoAdherentImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public List<Adherent> listerToutes() {
        return em.createQuery("SELECT c FROM Adherent c", Adherent.class).getResultList();
    }
}
