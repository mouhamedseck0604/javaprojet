package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoStatistiques;
import com.example.projetbibliotheque.Dao.DaoStatistiquesImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;

public class StatistiqueBibController {
    @FXML
    private Label lblEmpruntsMois;

    @FXML
    private Label lblRetards;

    @FXML
    private Label lblTotalAdherents;

    @FXML
    private Label lblTotalLivres;

    @FXML
    private Label lbltolallivre;

    @FXML
    private Label lbltotaladherent;

    @FXML
    private Label lbltotalemprunt;

    @FXML
    private Label lbltotalretard;


    private DaoStatistiques daoStats = new DaoStatistiquesImpl();

    @FXML
    private void initialize() {

        // Charger les statistiques au démarrage
        lbltolallivre.setText(String.valueOf(daoStats.totalLivres()));
        lbltotaladherent.setText(String.valueOf(daoStats.totalAdherents()));
        lbltotalemprunt.setText(String.valueOf(daoStats.empruntsDuMois()));
        lbltotalretard.setText(String.valueOf(daoStats.empruntsEnRetard()));
    }


}
