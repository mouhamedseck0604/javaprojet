package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoStatistiques;
import com.example.projetbibliotheque.Dao.DaoStatistiquesImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class DashboardController {
    @FXML
    private Label lblTotalAdherents;
    @FXML private Label lblEmpruntsMois;
    @FXML private Label lblTotalLivres;
    @FXML private Label lblRetards;
    @FXML
    private ToggleButton linkgAdherent;

    @FXML
    private ToggleButton linkgemprunt;

    @FXML
    private ToggleButton linkglivre;

    @FXML
    private ToggleButton linkgutilisateur;

    @FXML
    private ToggleButton linktbrd;


    private DaoStatistiques daoStats = new DaoStatistiquesImpl();

    @FXML
    private void initialize() {
        lblTotalAdherents.setText(String.valueOf(daoStats.totalAdherents()));
        lblEmpruntsMois.setText(String.valueOf(daoStats.empruntsDuMois()));
        lblTotalLivres.setText(String.valueOf(daoStats.totalLivres()));
        lblRetards.setText(String.valueOf(daoStats.empruntsEnRetard()));
    }

}
