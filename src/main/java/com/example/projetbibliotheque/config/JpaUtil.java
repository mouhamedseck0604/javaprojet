package com.example.projetbibliotheque.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("bibliothequePU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

