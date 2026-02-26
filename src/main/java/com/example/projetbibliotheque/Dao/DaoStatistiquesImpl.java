package com.example.projetbibliotheque.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class DaoStatistiquesImpl implements DaoStatistiques {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
    @Override

    public long totalAdherents() {
        EntityManager em = emf.createEntityManager();
        long count = em.createQuery("SELECT COUNT(a) FROM Adherent a", Long.class).getSingleResult();
        em.close();
        return count;
    }

    @Override
    public long totalLivres() {
        EntityManager em = emf.createEntityManager();
        long count = em.createQuery("SELECT COUNT(l) FROM Livre l", Long.class).getSingleResult();
        em.close();
        return count;
    }


    @Override
    public long empruntsDuMois() {
        EntityManager em = emf.createEntityManager();
        LocalDate debutMois = LocalDate.now().withDayOfMonth(1);
        long count = em.createQuery("SELECT COUNT(e) FROM Emprunt e WHERE e.dateEmprunt >= :debut", Long.class)
                .setParameter("debut", debutMois)
                .getSingleResult();
        em.close();
        return count;
    }
    @Override

    public long empruntsEnRetard() {
        EntityManager em = emf.createEntityManager();
        long count = em.createQuery("SELECT COUNT(e) FROM Emprunt e WHERE e.dateRetourEffective IS NULL AND e.dateRetourPrevue < CURRENT_DATE", Long.class)
                .getSingleResult();
        em.close();
        return count;
    }
}
