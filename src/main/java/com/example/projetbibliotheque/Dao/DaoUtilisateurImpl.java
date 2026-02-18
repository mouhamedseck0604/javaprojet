package com.example.projetbibliotheque.Dao;

import com.example.projetbibliotheque.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DaoUtilisateurImpl implements DaoUtilisateur {
    private final EntityManager em;

    public DaoUtilisateurImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Utilisateur authentifier(String login, String password) {
        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.login = :login AND u.actif = true",
                    Utilisateur.class
            );
            query.setParameter("login", login);
            Utilisateur user = query.getSingleResult();

            // Vérification du mot de passe avec BCrypt
            if (passwordCrypte.verifyPassword(password, user.getMotDePasse())) {
                user.setDerniereConnexion(java.time.LocalDateTime.now());
                return user;
            }
            return null;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void creer(Utilisateur user) {
        em.getTransaction().begin();
        user.setMotDePasse(passwordCrypte.hashPassword(user.getMotDePasse())); // hash avant insertion
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void modifier(Utilisateur user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    @Override
    public void supprimer(Long id) {
        em.getTransaction().begin();
        Utilisateur user = em.find(Utilisateur.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
    }

    @Override
    public Utilisateur trouverParLogin(String login) {
        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.login = :login",
                    Utilisateur.class
            ).setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public List<Utilisateur> listerToutes() {
        return em.createQuery("SELECT c FROM Utilisateur c", Utilisateur.class).getResultList();
    }
}