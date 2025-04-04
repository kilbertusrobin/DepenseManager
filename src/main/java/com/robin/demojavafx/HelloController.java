package com.robin.demojavafx;

import com.robin.demojavafx.db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HelloController {
    @FXML private TableView<Line> tableView;
    @FXML private TableColumn<Line, String> periodColumn;
    @FXML private TableColumn<Line, String> totalColumn;
    @FXML private TableColumn<Line, String> housingColumn;
    @FXML private TableColumn<Line, String> foodColumn;
    @FXML private TableColumn<Line, String> outingColumn;
    @FXML private TableColumn<Line, String> transportColumn;
    @FXML private TableColumn<Line, String> travelColumn;
    @FXML private TableColumn<Line, String> taxesColumn;
    @FXML private TableColumn<Line, String> otherColumn;

    private ObservableList<Line> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Vérifier que la connexion à la base de données fonctionne
        if (!Database.isOK()) {
            showErrorDialog("Erreur de base de données", "Impossible de se connecter à la base de données.");
            return;
        }

        // Link TableView columns to Line properties
        periodColumn.setCellValueFactory(new PropertyValueFactory<>("period"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        housingColumn.setCellValueFactory(new PropertyValueFactory<>("housing"));
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("food"));
        outingColumn.setCellValueFactory(new PropertyValueFactory<>("outing"));
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));
        travelColumn.setCellValueFactory(new PropertyValueFactory<>("travel"));
        taxesColumn.setCellValueFactory(new PropertyValueFactory<>("taxes"));
        otherColumn.setCellValueFactory(new PropertyValueFactory<>("other"));

        // Set data
        tableView.setItems(data);

        loadDataFromDatabase();
    }

    @FXML
    public void openAddLineDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addLineDialog.fxml"));
            GridPane gridPane = loader.load();

            AddLineDialogController controller = loader.getController();

            // Create Dialog
            Dialog<Line> dialog = new Dialog<>();
            dialog.setTitle("Ajouter une ligne");
            dialog.setHeaderText("Remplissez les champs ci-dessous:");
            dialog.initModality(Modality.APPLICATION_MODAL);

            // Add Buttons
            ButtonType submitButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(submitButton, cancelButton);

            dialog.getDialogPane().setContent(gridPane);

            // Handle submission
            dialog.setResultConverter(button -> {
                if (button == submitButton) {
                    return controller.getData();
                }
                return null;
            });

            // Show dialog
            Optional<Line> result = dialog.showAndWait();
            result.ifPresent(line -> {
                // Ajouter à la liste (tableview)
                data.add(line);

                // Ajouter à la base de données
                ExpenseDAO.insertExpense(line.toExpense());
            });

        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Erreur", "Impossible d'ouvrir la boîte de dialogue: " + e.getMessage());
        }
    }

    @FXML
    protected void onAdd(ActionEvent event) {
        System.out.println("Bouton appuyé");
        openAddLineDialog();
        event.consume();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDataFromDatabase() {
        List<Expense> expenses = ExpenseDAO.getAllExpenses();
        for (Expense expense : expenses) {
            Line line = new Line(
                expense.getDate(),
                calculateTotal(expense),
                (float) expense.getHousing(),
                (float) expense.getFood(),
                (float) expense.getGoingOut(),
                (float) expense.getTransportation(),
                (float) expense.getTravel(),
                (float) expense.getTax(),
                (float) expense.getOther()
            );
            data.add(line);
        }
    }

    private float calculateTotal(Expense expense) {
        return (float) (expense.getHousing() + expense.getFood() + expense.getGoingOut() +
                expense.getTransportation() + expense.getTravel() + expense.getTax() +
                expense.getOther());
    }
}