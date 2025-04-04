module com.robin.demojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    // Ajout des modules requis pour JDBC et SQLite
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.robin.demojavafx to javafx.fxml;
    exports com.robin.demojavafx;
    // Export du package de la base de données
    exports com.robin.demojavafx.db;
}