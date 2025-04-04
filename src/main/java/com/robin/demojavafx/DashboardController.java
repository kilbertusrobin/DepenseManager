package com.robin.demojavafx;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {

    private Stage primaryStage;
    private VBox dashboardView;

    public void setPrimaryStage(Stage stage, VBox dashboardView) {
        this.primaryStage = stage;
        this.dashboardView = dashboardView;
    }

    @FXML
    private void initialize() {
        // On peut initialiser des composants ici si nécessaire.
    }
}
