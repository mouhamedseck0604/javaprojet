package com.example.projetbibliotheque.app;
import com.example.projetbibliotheque.config.JpaUtil;
import jakarta.persistence.EntityManager;
public class TestHibernate {



        public static void main(String[] args) {

            System.out.println("Démarrage...");

            EntityManager em = JpaUtil.getEntityManager();

            System.out.println("Connexion OK, tables créées !");

            em.close();
        }
    }






