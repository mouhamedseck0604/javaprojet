package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Livre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class DaoLivreImpl implements DaoLivre {
    private EntityManager em;

    public DaoLivreImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void ajouter(Livre livre) {
        Livre existant = rechercherParIsbn(livre.getIsbn());
        if (existant != null) {
            throw new IllegalArgumentException("Un livre avec cet ISBN existe déjà !");
        }
        em.getTransaction().begin();
        em.persist(livre);
        em.getTransaction().commit();
    }

    @Override
    public void modifier(Livre livre) {
        em.getTransaction().begin();
        em.merge(livre);
        em.getTransaction().commit();
    }

    @Override
    public void supprimer(Long id) {
        em.getTransaction().begin();
        Livre livre = em.find(Livre.class, id);
        if (livre != null) {
            em.remove(livre);
        }
        em.getTransaction().commit();
    }

    @Override
    public Livre rechercherParIsbn(String isbn) {
        try {
            TypedQuery<Livre> query = em.createQuery(
                    "SELECT l FROM Livre l WHERE l.isbn = :isbn", Livre.class);
            query.setParameter("isbn", isbn);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Livre> rechercherParTitre(String titre) {
        return em.createQuery(
                        "SELECT l FROM Livre l WHERE l.titre LIKE :titre", Livre.class)
                .setParameter("titre", "%" + titre + "%")
                .getResultList();
    }

    @Override
    public List<Livre> rechercherParAuteur(String auteur) {
        return em.createQuery(
                        "SELECT l FROM Livre l WHERE l.auteur LIKE :auteur", Livre.class)
                .setParameter("auteur", "%" + auteur + "%")
                .getResultList();
    }



    @Override
    public List<Livre> listerTous() {
        return em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
    }
}
