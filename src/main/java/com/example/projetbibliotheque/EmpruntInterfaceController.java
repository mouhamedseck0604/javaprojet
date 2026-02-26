package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.*;
import com.example.projetbibliotheque.config.JpaUtil;
import com.example.projetbibliotheque.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class EmpruntInterfaceController {

    @FXML private TableView<Emprunt> empruntTable;
    @FXML
    private TableColumn<Emprunt, String> livreColumn;
    @FXML private TableColumn<Emprunt, String> adherentColumn;
    @FXML private TableColumn<Emprunt, String> utilisateurColumn;
    @FXML private TableColumn<Emprunt, LocalDate> dateEmpruntColumn;
    @FXML private TableColumn<Emprunt, LocalDate> dateRetourColumn;
    @FXML private TableColumn<Emprunt, String> statutColumn;

    @FXML private ComboBox<Livre> livreCombo;
    @FXML private ComboBox<Adherent> adherentCombo;
    @FXML private ComboBox<Utilisateur> utilisateurCombo;
    @FXML private DatePicker dateEmpruntPicker;
    @FXML private DatePicker dateRetourPicker;

    @FXML private Button btnAjouter, btnModifier, btnSupprimer, btnAnnuler,btnRetour;
    @FXML
    private ToggleButton toggleTheme;


    private DaoEmprunt daoEmprunt;
    private DaoLivre daoLivre;


    @FXML
    private void initialize() {
        daoEmprunt = new DaoEmpruntImpl(JpaUtil.getEntityManager());
        daoLivre = new DaoLivreImpl(JpaUtil.getEntityManager());
        DaoUtilisateur daouser = new DaoUtilisateurImpl(JpaUtil.getEntityManager());
        DaoAdherent daoAdherent = new DaoAdherentImpl();

        // Colonnes
        livreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLivre().getTitre()));
        adherentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdherent().getNom()));
        utilisateurColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUtilisateur().getNom()));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
        statutColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateRetourEffective() == null ? "En cours" : "Clôturé"));

        chargerEmprunts();

        // Désactiver bouton Ajouter si une ligne est sélectionnée
        empruntTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                btnAjouter.setDisable(true);
                remplirFormulaire(newSel);
            } else {
                btnAjouter.setDisable(false);
            }
        });


        // Charger les Utilisateur depuis la base
        List<Utilisateur> utilisateurs = daouser.listerToutes();
        utilisateurCombo.getItems().addAll(utilisateurs);
        // Charger les Adherent depuis la base
        List<Adherent> adherent = daoAdherent.listerToutes();
        adherentCombo.getItems().addAll(adherent);
        // Charger les Livres depuis la base
        List<Livre> livre =  daoLivre.listerTous();
        livreCombo.getItems().addAll(livre);

    }

    private void chargerEmprunts() {
        empruntTable.setItems(FXCollections.observableArrayList(daoEmprunt.listerTous()));
    }

    private void remplirFormulaire(Emprunt e) {
        livreCombo.setValue(e.getLivre());
        adherentCombo.setValue(e.getAdherent());
        utilisateurCombo.setValue(e.getUtilisateur());
        dateEmpruntPicker.setValue(e.getDateEmprunt());
        dateRetourPicker.setValue(e.getDateRetourPrevue());
    }

    @FXML
    private void ajouterEmprunt() {
        if (livreCombo.getValue() == null || adherentCombo.getValue() == null || utilisateurCombo.getValue() == null || dateEmpruntPicker.getValue() == null || dateRetourPicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tous les champs sont requis !");
            alert.showAndWait();
            return;
        }

        Livre livre = livreCombo.getValue();
        if (!livre.isDisponible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ce livre n'est pas disponible !");
            alert.showAndWait();
            return;
        }

        Emprunt e = new Emprunt();
        e.setLivre(livre);
        e.setAdherent(adherentCombo.getValue());
        e.setUtilisateur(utilisateurCombo.getValue());
        e.setDateEmprunt(dateEmpruntPicker.getValue());
        e.setDateRetourPrevue(dateRetourPicker.getValue());

        daoEmprunt.ajouter(e);

        livre.setDisponible(false);
        daoLivre.modifier(livre);

        chargerEmprunts();
        annulerFormulaire();
    }


    @FXML
    private void modifierEmprunt() {
        Emprunt e = empruntTable.getSelectionModel().getSelectedItem();
        if (e != null) {
            e.setLivre(livreCombo.getValue());
            e.setAdherent(adherentCombo.getValue());
            e.setUtilisateur(utilisateurCombo.getValue());
            e.setDateEmprunt(dateEmpruntPicker.getValue());
            e.setDateRetourPrevue(dateRetourPicker.getValue());

            daoEmprunt.modifier(e);
            chargerEmprunts();
            annulerFormulaire();
        }
    }

    @FXML
    private void supprimerEmprunt() {
        Emprunt e = empruntTable.getSelectionModel().getSelectedItem();
        if (e != null) {
            daoEmprunt.supprimer(e);
            chargerEmprunts();
            annulerFormulaire();
        }
    }

    @FXML
    private void annulerFormulaire() {
        livreCombo.setValue(null);
        adherentCombo.setValue(null);
        utilisateurCombo.setValue(null);
        dateEmpruntPicker.setValue(null);
        dateRetourPicker.setValue(null);
        empruntTable.getSelectionModel().clearSelection();
        btnAjouter.setDisable(false);
    }

    @FXML
    private void enregistrerRetour() {
        Emprunt e = empruntTable.getSelectionModel().getSelectedItem();
        if (e != null) {
            e.setDateRetourEffective(LocalDate.now());
            e.setPenalite(daoEmprunt.calculerPenalite(e));

            daoEmprunt.modifier(e);

            // rendre le livre disponible
            Livre livre = e.getLivre();
            livre.setDisponible(true);
            daoLivre.modifier(livre);

            chargerEmprunts();
            annulerFormulaire();
        }
    }

}