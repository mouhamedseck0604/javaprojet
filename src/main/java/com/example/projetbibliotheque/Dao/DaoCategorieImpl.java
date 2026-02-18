package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Categorie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DaoCategorieImpl implements DaoCategorie {
    private final EntityManager em;

    public DaoCategorieImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void ajouter(Categorie categorie) {
        em.getTransaction().begin();
        em.persist(categorie);
        em.getTransaction().commit();
    }

    @Override
    public void modifier(Categorie categorie) {
        em.getTransaction().begin();
        em.merge(categorie);
        em.getTransaction().commit();
    }

    @Override
    public void supprimer(Long id) {
        em.getTransaction().begin();
        Categorie cat = em.find(Categorie.class, id);
        if (cat != null) em.remove(cat);
        em.getTransaction().commit();
    }

    @Override
    public Categorie rechercherParLibelle(String libelle) {
        TypedQuery<Categorie> query = em.createQuery(
                "SELECT c FROM Categorie c WHERE c.libelle = :libelle", Categorie.class);
        query.setParameter("libelle", libelle);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Categorie> listerToutes() {
        return em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
    }
}