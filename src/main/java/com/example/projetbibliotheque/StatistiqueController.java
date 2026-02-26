package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoStatistiques;
import com.example.projetbibliotheque.Dao.DaoStatistiquesImpl;
import com.example.projetbibliotheque.config.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;

public class StatistiqueController {
    @FXML
    private Label lblTotalAdherent;
    @FXML private Label lblTotalEmpruntMois;
    @FXML private Label lblTotalRetard;
    @FXML private Label lblTotalLivre;

    @FXML private ScrollPane spaceDynamique;


    private DaoStatistiques daoStats = new DaoStatistiquesImpl();

    @FXML
    private void initialize() {

        // Charger les statistiques au démarrage
        lblTotalLivre.setText(String.valueOf(daoStats.totalLivres()));
        lblTotalAdherent.setText(String.valueOf(daoStats.totalAdherents()));
        lblTotalEmpruntMois.setText(String.valueOf(daoStats.empruntsDuMois()));
        lblTotalRetard.setText(String.valueOf(daoStats.empruntsEnRetard()));
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

}
