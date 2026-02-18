package com.example.projetbibliotheque.entities;
import jakarta.persistence.*;
//import javax.persistence.*;

@Entity
@Table(name = "livre")
public class Livre {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String isbn;

        private String titre;
        private String auteur;
        private int anneePublication;
        private int nombreExemplaires;
        private boolean disponible;

        @ManyToOne
        @JoinColumn(name = "categorie_id")
        private Categorie categorie;

        public Livre() {}


        // Getters & Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getAuteur() {
            return auteur;
        }

        public void setAuteur(String auteur) {
            this.auteur = auteur;
        }

        public int getAnneePublication() {
            return anneePublication;
        }

        public void setAnneePublication(int anneePublication) {
            this.anneePublication = anneePublication;
        }

        public int getNombreExemplaires() {
            return nombreExemplaires;
        }

        public void setNombreExemplaires(int nombreExemplaires) {
            this.nombreExemplaires = nombreExemplaires;
        }

        public boolean isDisponible() {
            return disponible;
        }

        public void setDisponible(boolean disponible) {
            this.disponible = disponible;
        }

        public Categorie getCategorie() {
            return categorie;
        }

        public void setCategorie(Categorie categorie) {
            this.categorie = categorie;
        }

    @Override
    public String toString() {
        return titre;
    }
}












