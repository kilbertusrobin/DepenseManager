package com.robin.demojavafx;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HeaderController {

    private Stage primaryStage;
    private VBox tableView;

    public void setPrimaryStage(Stage stage, VBox tableView) {
        this.primaryStage = stage;
        this.tableView = tableView;
    }

    @FXML
    private void switchToTableView() {
        Scene scene = new Scene(tableView);
        primaryStage.setScene(scene);
    }

    @FXML
    private void switchToGraphs() {
        System.out.println("Switching to Graphs screen (not implemented yet)");
    }
}
