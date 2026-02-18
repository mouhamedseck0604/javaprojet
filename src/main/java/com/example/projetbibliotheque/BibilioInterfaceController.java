package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Util.Outils;
import com.example.projetbibliotheque.config.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class BibilioInterfaceController {
    @FXML
    private Button logoutButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        // Récupérer la session
        UserSession session = UserSession.getInstance();
        if (session != null) {
            welcomeLabel.setText("Bienvenue, " + session.getUtilisateur().getPrenom()+" "+session.getUtilisateur().getNom()
                    + " (" + session.getUtilisateur().getProfil() + ")");
        }

        // Action du bouton Déconnexion
        logoutButton.setOnAction(event -> {
            UserSession.clear(); // supprimer la session
            loadDashboard("FormLogin.fxml"); // retour à l'écran de login
        });
    }

    private void loadDashboard(String fxmlPath) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleBooks(ActionEvent event) {
    }

    public void handleMembers(ActionEvent event) {
    }

    public void handleLoans(ActionEvent event) {
    }

    public void handleDelays(ActionEvent event) {
    }

    public void verslivre(ActionEvent event) throws IOException {
        Outils.loadandwait(event,"gestion des livres","/com/example/projetbibliotheque/FormLivreInterface.fxml");
    }

    public void versemprunt(ActionEvent event) throws IOException {
        Outils.loadandwait(event,"gestion des livres","/com/example/projetbibliotheque/EmpruntInterface.fxml");
    }
}
