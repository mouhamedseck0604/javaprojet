package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Emprunt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class DaoEmpruntImpl implements DaoEmprunt {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");



    private EntityManager em;
    public DaoEmpruntImpl(EntityManager em){this.em = em;}
    @Override
    public void ajouter(Emprunt emprunt) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(emprunt);
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public void modifier(Emprunt emprunt) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(emprunt);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void supprimer(Emprunt emprunt) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Emprunt e = em.find(Emprunt.class, emprunt.getId());
            if (e != null) {
                em.remove(e);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Emprunt trouverParId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Emprunt.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> listerTous() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery("SELECT e FROM Emprunt e", Emprunt.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public double calculerPenalite(Emprunt e) {
        if (e.getDateRetourEffective() == null) return 0.0;
        long joursRetard = ChronoUnit.DAYS.between(e.getDateRetourPrevue(), e.getDateRetourEffective());
        return joursRetard > 0 ? joursRetard * 100.0 : 0.0; // exemple : 100 FCFA par jour
    }
}