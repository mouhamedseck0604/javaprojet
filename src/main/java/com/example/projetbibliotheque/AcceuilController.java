package com.example.projetbibliotheque;

import com.example.projetbibliotheque.config.UserSession;
import com.example.projetbibliotheque.entities.Profil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AcceuilController {
    @FXML private Button btnUtilisateurs;
    @FXML private Button btnAdherent;
    @FXML private Button btnLivre;
    @FXML private Button btnEmprunt;
    @FXML private Button btnDeconnexion;
    @FXML private Button btntableaudebord;
    @FXML private Label nomprf1;
    @FXML private Label nomprf2;
    @FXML private Label profile;

    @FXML private ScrollPane spaceDynamique;


    @FXML
    private void initialize() {
        // Récupérer la session
        UserSession session = UserSession.getInstance();
        if (session != null) {
            nomprf1.setText("" + session.getUtilisateur().getPrenom()+" "+session.getUtilisateur().getNom()
                   );
            nomprf2.setText(nomprf1.getText());
            profile.setText(""+ session.getUtilisateur().getProfil());

            if (session.getUtilisateur().getProfil() == Profil.ADMIN){
                // Charger par défaut la vue Statistique.fxml
                chargerVue("/com/example/projetbibliotheque/Statistique.fxml");
            }else {
                chargerVue("/com/example/projetbibliotheque/StatistiqueBib.fxml");
            }

        }

    }

    /** Méthode utilitaire pour charger une vue dans le ScrollPane */
    private void chargerVue(String cheminFXML) {
        try {
            URL url = getClass().getResource(cheminFXML);
            if (url == null) {
                System.err.println("FXML introuvable : " + cheminFXML);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Node contenu = loader.load();
            spaceDynamique.setContent(contenu);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de " + cheminFXML);
            e.printStackTrace();
        }
    }

    @FXML
    void onLivres(ActionEvent event) {
        chargerVue("/com/example/projetbibliotheque/FormLivreInterface.fxml");
    }

    @FXML
    void onAdherent(ActionEvent event) {
        chargerVue("/com/example/projetbibliotheque/Adherent.fxml");
    }

    @FXML
    void onEmprunt(ActionEvent event) {
        chargerVue("/com/example/projetbibliotheque/EmpruntInterface.fxml");
    }

    @FXML
    void onUtilisateurs(ActionEvent event) {
        chargerVue("/com/example/projetbibliotheque/utilisateur.fxml");
    }
    @FXML
    void onTableauDeBord(ActionEvent event) {
        chargerVue("/com/example/projetbibliotheque/Statistique.fxml");
    }

    @FXML
    void onTableauDeBordBib(ActionEvent event) {
        chargerVue("/com/example/projetbibliotheque/StatistiqueBib.fxml");
    }

    @FXML
    public void onDeconnexion(ActionEvent event) {
        try {
            // 1. Réinitialiser la session utilisateur
            UserSession.getInstance().setUtilisateur(null);

            // 2. Charger la vue de connexion
            URL url = getClass().getResource("/com/example/projetbibliotheque/login.fxml");
            if (url == null) {
                System.err.println("FXML introuvable : /com/example/projetbibliotheque/login.fxml");
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // 3. Récupérer la fenêtre actuelle et remplacer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion");
            e.printStackTrace();
        }
    }
}
