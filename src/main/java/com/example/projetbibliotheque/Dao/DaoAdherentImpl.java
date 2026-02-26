package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Adherent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DaoAdherentImpl implements DaoAdherent{

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");

    @Override
    public void ajouterAdherent(Adherent adherent) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(adherent);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void modifierAdherent(Adherent adherent) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(adherent);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void supprimerAdherent(Long id) {
        EntityManager em = emf.createEntityManager();
        Adherent adherent = em.find(Adherent.class, id);
        if (adherent != null) {
            em.getTransaction().begin();
            em.remove(adherent);
            em.getTransaction().commit();
        }
        em.close();
    }

    @Override
    public List<Adherent> listerToutes() {
        EntityManager em = emf.createEntityManager();
        List<Adherent> adherents = em.createQuery("SELECT a FROM Adherent a", Adherent.class).getResultList();
        em.close();
        return adherents;
    }
}
