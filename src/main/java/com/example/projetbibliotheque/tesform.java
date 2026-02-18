package com.example.projetbibliotheque;

import com.example.projetbibliotheque.Dao.DaoUtilisateurImpl;
import com.example.projetbibliotheque.config.JpaUtil;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class tesform extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load(), 750, 650);

        ConnexionController controller = loader.getController();

        EntityManager em = JpaUtil.getEntityManager();
        controller.setDaoUtilisateur(new DaoUtilisateurImpl(em));


        primaryStage.setTitle("Formulaire de Connexion");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
