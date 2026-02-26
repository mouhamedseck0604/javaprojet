package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoAdherent;
import com.example.projetbibliotheque.Dao.DaoAdherentImpl;
import com.example.projetbibliotheque.entities.Adherent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;
import java.util.stream.Collectors;

public class AdherentController {
    @FXML private TextField txtMatricule, txtNom, txtPrenom, txtEmail, txtTelephone, txtAdresse;
    @FXML private CheckBox chkActif;
    @FXML private TableView<Adherent> tableAdherents;
    @FXML private TableColumn<Adherent, String> colNom, colPrenom, colEmail;

    @FXML private ComboBox<String> comboTri;
    @FXML private TextField txtRecherche;

    private DaoAdherent service = new DaoAdherentImpl();
    private ObservableList<Adherent> adherents = FXCollections.observableArrayList();

    private Adherent adherentSelectionne;

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        rafraichirTable();

        // Gestion de la sélection dans la table
        tableAdherents.setOnMouseClicked((MouseEvent event) -> {
            adherentSelectionne = tableAdherents.getSelectionModel().getSelectedItem();
            if (adherentSelectionne != null) {
                remplirChamps(adherentSelectionne);
            }
        });

        // Initialiser ComboBox tri
        comboTri.setItems(FXCollections.observableArrayList("Matricule", "Nom", "Prénom", "Email"));
        comboTri.setOnAction(e -> trierListe());
    }

    @FXML
    private void ajouterAdherent() {
        if (!validerChamps()) return;

        Adherent a = new Adherent();
        a.setMatricule(txtMatricule.getText());
        a.setNom(txtNom.getText());
        a.setPrenom(txtPrenom.getText());
        a.setEmail(txtEmail.getText());
        a.setTelephone(txtTelephone.getText());
        a.setAdresse(txtAdresse.getText());
        a.setActif(chkActif.isSelected());
        a.setDateInscription(java.time.LocalDate.now());

        service.ajouterAdherent(a);
        afficherInfo("Adhérent ajouté avec succès !");
        rafraichirTable();
        viderChamps();
    }

    @FXML
    private void modifierAdherent() {
        if (adherentSelectionne == null) {
            afficherErreur("Veuillez sélectionner un adhérent à modifier.");
            return;
        }
        if (!validerChamps()) return;

        adherentSelectionne.setMatricule(txtMatricule.getText());
        adherentSelectionne.setNom(txtNom.getText());
        adherentSelectionne.setPrenom(txtPrenom.getText());
        adherentSelectionne.setEmail(txtEmail.getText());
        adherentSelectionne.setTelephone(txtTelephone.getText());
        adherentSelectionne.setAdresse(txtAdresse.getText());
        adherentSelectionne.setActif(chkActif.isSelected());

        service.modifierAdherent(adherentSelectionne);
        afficherInfo("Adhérent modifié avec succès !");
        rafraichirTable();
        viderChamps();
    }

    @FXML
    private void supprimerAdherent() {
        if (adherentSelectionne == null) {
            afficherErreur("Veuillez sélectionner un adhérent à supprimer.");
            return;
        }
        //if (!Session.getInstance().isAdmin()) {
        //    afficherErreur("Seul un administrateur peut supprimer un adhérent.");
        //    return;
        //}

        service.supprimerAdherent(adherentSelectionne.getId());
        afficherInfo("Adhérent supprimé avec succès !");
        rafraichirTable();
        viderChamps();
    }

    @FXML
    private void annuler() {
        viderChamps();
        adherentSelectionne = null;
    }

    @FXML
    private void rechercherAdherent() {
        String critere = txtRecherche.getText().toLowerCase();
        if (critere.isEmpty()) {
            rafraichirTable();
            return;
        }

        var resultats = adherents.stream()
                .filter(a -> a.getNom().toLowerCase().contains(critere)
                        || a.getPrenom().toLowerCase().contains(critere)
                        || a.getEmail().toLowerCase().contains(critere)
                        || a.getMatricule().toLowerCase().contains(critere))
                .collect(Collectors.toList());

        tableAdherents.setItems(FXCollections.observableArrayList(resultats));
    }

    private void trierListe() {
        String choix = comboTri.getValue();
        if (choix == null) return;

        Comparator<Adherent> comparator;
        switch (choix) {
            case "Matricule":
                comparator = Comparator.comparing(Adherent::getMatricule);
                break;
            case "Nom":
                comparator = Comparator.comparing(Adherent::getNom);
                break;
            case "Prénom":
                comparator = Comparator.comparing(Adherent::getPrenom);
                break;
            case "Email":
                comparator = Comparator.comparing(Adherent::getEmail);
                break;
            default:
                return;
        }
        FXCollections.sort(adherents, comparator);
        tableAdherents.setItems(adherents);
    }

    private void rafraichirTable() {
        adherents.setAll(service.listerToutes());
        tableAdherents.setItems(adherents);
    }

    private void remplirChamps(Adherent a) {
        txtMatricule.setText(a.getMatricule());
        txtNom.setText(a.getNom());
        txtPrenom.setText(a.getPrenom());
        txtEmail.setText(a.getEmail());
        txtTelephone.setText(a.getTelephone());
        txtAdresse.setText(a.getAdresse());
        chkActif.setSelected(a.isActif());
    }

    private void viderChamps() {
        txtMatricule.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtTelephone.clear();
        txtAdresse.clear();
        chkActif.setSelected(false);
    }

    private boolean validerChamps() {
        if (txtMatricule.getText().isEmpty() || txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty()) {
            afficherErreur("Matricule, Nom et Prénom sont obligatoires.");
            return false;
        }
        if (!txtEmail.getText().isEmpty() && !txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            afficherErreur("Adresse email invalide.");
            return false;
        }
        if (!txtTelephone.getText().isEmpty() && !txtTelephone.getText().matches("\\d{9,}")) {
            afficherErreur("Numéro de téléphone invalide (au moins 9 chiffres).");
            return false;
        }
        return true;
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
