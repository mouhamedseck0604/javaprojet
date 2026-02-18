module com.example.projetbibliotheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jbcrypt;


    opens com.example.projetbibliotheque to javafx.fxml,javafx.base;
    opens com.example.projetbibliotheque.entities ;
    exports com.example.projetbibliotheque;

    exports com.example.projetbibliotheque.Dao;

    // Exporter les entités pour qu'elles soient visibles
    exports com.example.projetbibliotheque.entities;





}