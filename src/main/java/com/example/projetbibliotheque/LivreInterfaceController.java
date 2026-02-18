package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoCategorie;
import com.example.projetbibliotheque.Dao.DaoCategorieImpl;
import com.example.projetbibliotheque.Dao.DaoLivre;
import com.example.projetbibliotheque.Dao.DaoLivreImpl;
import com.example.projetbibliotheque.config.JpaUtil;
import com.example.projetbibliotheque.entities.Categorie;
import com.example.projetbibliotheque.entities.Livre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class LivreInterfaceController {
    @FXML
    private ComboBox<Categorie> categorieCombo;
    @FXML
    private TextField txtisbn, txttitre, txtauteur, txtannpub, txtnbrexamp, searchField;

    @FXML
    private CheckBox disponibleCheck;

    @FXML
    private Button btnsave, btnupdate, btndelete, btnreach,btnannul;

    @FXML
    private ComboBox<String> critereCombo;

    @FXML
    private TableView<Livre> livreTable;

    @FXML
    private TableColumn<Livre, String> isbnColumn, titreColumn, auteurColumn, categorieColumn;

    @FXML
    private TableColumn<Livre, Boolean> disponibleColumn;

    private DaoLivre daoLivre; // ton DAO pour gérer les livres


    @FXML
    private void initialize() {
        // Initialiser le DAO (avec EntityManager par ex.)
        daoLivre = new DaoLivreImpl(JpaUtil.getEntityManager());
        DaoCategorie daoCategorie = new DaoCategorieImpl(JpaUtil.getEntityManager());

        // Remplir la ComboBox avec les critères
        critereCombo.getItems().addAll("Titre", "Auteur", "ISBN","All");
        critereCombo.setValue("Titre"); // valeur par défaut

        // Charger les catégories depuis la base
        List<Categorie> categories = daoCategorie.listerToutes();
        categorieCombo.getItems().addAll(categories);



        // Initialiser les colonnes de la TableView
        isbnColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIsbn()));
        titreColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitre()));
        auteurColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAuteur()));
        categorieColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCategorie() != null ? data.getValue().getCategorie().getLibelle() : ""
                )
        );
        disponibleColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleBooleanProperty(data.getValue().isDisponible()));

        // Charger tous les livres au démarrage
        chargerLivres();

        livreTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtisbn.setText(newSel.getIsbn());
                txttitre.setText(newSel.getTitre());
                txtauteur.setText(newSel.getAuteur());
                txtannpub.setText(String.valueOf(newSel.getAnneePublication()));
                txtnbrexamp.setText(String.valueOf(newSel.getNombreExemplaires()));
                disponibleCheck.setSelected(newSel.isDisponible());

                // Pour la catégorie : sélectionner dans le ComboBox
                categorieCombo.setValue(newSel.getCategorie());
                // Désactiver le bouton Ajouter
                btnsave.setDisable(true);

            }else {
                // Réactiver le bouton Ajouter si aucune sélection
                btnsave.setDisable(false);
            }

        });
    }

    private void chargerLivres() {
        List<Livre> livres = daoLivre.listerTous();
        ObservableList<Livre> observableLivres = FXCollections.observableArrayList(livres);
        livreTable.setItems(observableLivres);
    }

    @FXML
    private void ajouterLivre() {
        // Vérification des champs obligatoires
        if (txtisbn.getText().isBlank() ||
                txttitre.getText().isBlank() ||
                txtauteur.getText().isBlank() ||
                txtannpub.getText().isBlank() ||
                txtnbrexamp.getText().isBlank() ||
                categorieCombo.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs requis");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont requis !");
            alert.showAndWait();
            return; // on arrête la méthode ici
        }

        try {
            Livre livre = new Livre();
            livre.setIsbn(txtisbn.getText());
            livre.setTitre(txttitre.getText());
            livre.setAuteur(txtauteur.getText());
            livre.setCategorie(categorieCombo.getValue());
            livre.setAnneePublication(Integer.parseInt(txtannpub.getText()));
            livre.setNombreExemplaires(Integer.parseInt(txtnbrexamp.getText()));
            livre.setDisponible(disponibleCheck.isSelected());

            daoLivre.ajouter(livre);
            chargerLivres();
            annulerLivre();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Livre ajouté avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des nombres valides pour l'année et le nombre d'exemplaires.");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifierLivre() {
        Livre livre = livreTable.getSelectionModel().getSelectedItem();
        if (livre != null) {
            livre.setTitre(txttitre.getText());
            livre.setAuteur(txtauteur.getText());
            livre.setCategorie(categorieCombo.getValue());
            livre.setAnneePublication(Integer.parseInt(txtannpub.getText()));
            livre.setNombreExemplaires(Integer.parseInt(txtnbrexamp.getText()));
            livre.setDisponible(disponibleCheck.isSelected());

            daoLivre.modifier(livre);
            chargerLivres();
            livreTable.refresh();
        }
    }

    @FXML
    private void supprimerLivre() {
        Livre livre = livreTable.getSelectionModel().getSelectedItem();
        if (livre != null) {
            daoLivre.supprimer(livre.getId());
            chargerLivres();
        }
    }

    @FXML
    private void rechercherLivre() {
        String motCle = searchField.getText();
        String critere = critereCombo.getValue();

        if (motCle == null || motCle.isBlank() || critere == null) {
            return; // ne rien faire si champ vide
        }


        List<Livre> resultats = new ArrayList<>();

        switch (critere) {
            case "Titre" -> resultats = daoLivre.rechercherParTitre(motCle);
            case "Auteur" -> resultats = daoLivre.rechercherParAuteur(motCle);
            case "ISBN" -> {
                Livre livre = daoLivre.rechercherParIsbn(motCle);
                if (livre != null) resultats.add(livre);
            }
            case "All"-> resultats = daoLivre.listerTous();
        }

        livreTable.setItems(FXCollections.observableArrayList(resultats));
    }

    @FXML
    private void annulerLivre() {
        // Vider les champs texte
        txtisbn.clear();
        txttitre.clear();
        txtauteur.clear();
        txtannpub.clear();
        txtnbrexamp.clear();

        // Réinitialiser le ComboBox
        categorieCombo.setValue(null);

        // Réinitialiser la CheckBox
        disponibleCheck.setSelected(false);

        // Annuler la sélection dans la TableView
        livreTable.getSelectionModel().clearSelection();
    }
}