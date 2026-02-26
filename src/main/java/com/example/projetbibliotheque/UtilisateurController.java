package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoUtilisateur;
import com.example.projetbibliotheque.Dao.DaoUtilisateurImpl;
import com.example.projetbibliotheque.config.PasswordUtil;
import com.example.projetbibliotheque.entities.Profil;
import com.example.projetbibliotheque.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UtilisateurController {
    @FXML
    private TextField txtLogin, txtNom, txtPrenom, txtEmail;
    @FXML private PasswordField txtMotDePasse;
    @FXML private ComboBox<Profil> comboProfil;
    @FXML private CheckBox chkActif;
    @FXML private TableView<Utilisateur> tableUtilisateurs;
    @FXML private TableColumn<Utilisateur, String> colLogin, colNom, colEmail, colProfil,colprenom,colid,coletat;


    private DaoUtilisateur service;

    public void setDaoUtilisateur(DaoUtilisateurImpl service) {
        this.service = service;
    }
    private ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
    private Utilisateur utilisateurSelectionne;

    @FXML
    public void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();
        // Initialiser ton DAO ici
        service = new DaoUtilisateurImpl(em);
        colLogin.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLogin()));
        colNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colProfil.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProfil().name()));
        colprenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        colid.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getId())));
        coletat.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().isActif())));

        comboProfil.setItems(FXCollections.observableArrayList(Profil.values()));
        rafraichirTable();
    }

    @FXML
    private void ajouterUtilisateur() {
        if (txtLogin.getText().isEmpty() || txtMotDePasse.getText().isEmpty()) {
            afficherErreur("Login et mot de passe sont obligatoires.");
            return;
        }

        Utilisateur u = new Utilisateur();
        u.setLogin(txtLogin.getText());
        u.setMotDePasse(PasswordUtil.hashPassword(txtMotDePasse.getText()));
        u.setNom(txtNom.getText());
        u.setPrenom(txtPrenom.getText());
        u.setEmail(txtEmail.getText());
        u.setProfil(comboProfil.getValue());
        u.setActif(chkActif.isSelected());
        u.setDateCreation(java.time.LocalDateTime.now());

        service.creer(u);
        afficherInfo("Utilisateur ajouté avec succès !");
        rafraichirTable();
        viderChamps();
    }

    @FXML
    private void modifierUtilisateur() {
        if (utilisateurSelectionne == null) {
            afficherErreur("Veuillez sélectionner un utilisateur.");
            return;
        }

        utilisateurSelectionne.setNom(txtNom.getText());
        utilisateurSelectionne.setPrenom(txtPrenom.getText());
        utilisateurSelectionne.setEmail(txtEmail.getText());
        utilisateurSelectionne.setProfil(comboProfil.getValue());
        utilisateurSelectionne.setActif(chkActif.isSelected());

        service.modifier(utilisateurSelectionne);
        afficherInfo("Utilisateur modifié avec succès !");
        rafraichirTable();
        viderChamps();
    }

    @FXML
    private void supprimerUtilisateur() {
        if (utilisateurSelectionne == null) {
            afficherErreur("Veuillez sélectionner un utilisateur.");
            return;
        }
        service.supprimer(utilisateurSelectionne.getId());
        afficherInfo("Utilisateur supprimé avec succès !");
        rafraichirTable();
        viderChamps();
    }

    @FXML
    private void reinitialiserMotDePasse() {
        if (utilisateurSelectionne == null) {
            afficherErreur("Veuillez sélectionner un utilisateur.");
            return;
        }
        service.reinitialiserMotDePasse(utilisateurSelectionne, "nouveau123");
        afficherInfo("Mot de passe réinitialisé !");
        rafraichirTable();
    }

    private void rafraichirTable() {
        utilisateurs.setAll(service.listerToutes());
        tableUtilisateurs.setItems(utilisateurs);
    }
    @FXML

    private void viderChamps() {
        txtLogin.clear();
        txtMotDePasse.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        chkActif.setSelected(false);
        comboProfil.setValue(null);
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Validation échouée");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
