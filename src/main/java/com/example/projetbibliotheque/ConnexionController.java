package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoUtilisateur;
import com.example.projetbibliotheque.Dao.DaoUtilisateurImpl;
import com.example.projetbibliotheque.Dao.passwordCrypte;
import com.example.projetbibliotheque.config.UserSession;
import com.example.projetbibliotheque.entities.Profil;
import com.example.projetbibliotheque.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionController {
    @FXML
    private Button btnconnect;

    @FXML
    private TextField txtlogin;

    @FXML
    private PasswordField txtpassword;
    @FXML private Label lblMessage;


    private DaoUtilisateur userdao;

    public void setDaoUtilisateur(DaoUtilisateurImpl service) {
        this.userdao = service;
    }

    @FXML
    private void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();


        // Initialiser ton DAO ici
        userdao = new DaoUtilisateurImpl(em);
    }

    @FXML
    private void checkconnection() {
        String login = txtlogin.getText();
        String password = txtpassword.getText();

        Utilisateur user = userdao.authentifier(login, password);

        if (user != null) {

            // Redirection selon profil
            if (user.getProfil() == Profil.ADMIN) {
                UserSession.init(user);
                loadDashboard("tableauDeBordAdministrateur.fxml");
            } else if (user.getProfil() == Profil.BIBLIOTHECAIRE) {
                UserSession.init(user);
                loadDashboard("BibilioInterface.fxml");
            }
        } else {
            lblMessage.setText("Login ou mot de passe incorrect !");
        }
    }

    private void loadDashboard(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) txtlogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
