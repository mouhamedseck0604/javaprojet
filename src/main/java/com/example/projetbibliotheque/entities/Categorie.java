package com.example.projetbibliotheque.entities;
import jakarta.persistence.*;

    @Entity
    @Table(name = "categorie")
    public class Categorie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String libelle;

        private String description;

        // Constructeur vide OBLIGATOIRE pour JPA
        public Categorie() {}

        // Getters & Setters
        public Long getId() {
            return id;
        }

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return libelle; // affichera "Roman", "Science", etc.
        }

    }












