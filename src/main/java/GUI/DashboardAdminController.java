package GUI;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import services.MedicamentService;
import entites.Medicament;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DashboardAdminController {
    @FXML
    private TextField searchBar;

    private final MedicamentService medicamentService = new MedicamentService();

    @FXML
    private Button notificationButton;

    @FXML
    private VBox notificationPanel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField imageField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField qrCodePathField;

    @FXML
    private void handleAjouterMedicament(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addMedicamentForm.fxml"));
            Parent medicamentForm = loader.load();
            borderPane.setCenter(medicamentForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void toggleNotificationPanelVisibility() {
        notificationPanel.getChildren().clear(); // Clear existing notifications
// Clear existing notifications
        if (notificationPanel.isVisible()) {
            notificationPanel.setVisible(false);
        } else {
            // Get medicaments with quantity <= 2 from the database and display notification messages
            List<Medicament> lowQuantityMedicaments = medicamentService.getMedicamentsWithLowQuantity(2); // Adjust the threshold as needed
            for (Medicament medicament : lowQuantityMedicaments) {
                Label notificationLabel = new Label("Medicament \"" + medicament.getName() + "\" has a low quantity. Consider restocking.");
                notificationPanel.getChildren().add(notificationLabel);
            }
            notificationPanel.setVisible(true);
        }
    }

    @FXML
    private void handleAddMedicament(ActionEvent event) {
        String name = nameField.getText();
        String quantity = quantityField.getText();
        String description = descriptionField.getText();
        String image = imageField.getText();
        String price = priceField.getText();
        String qrCodePath = qrCodePathField.getText();

        // Validation of input fields
        if (name.isEmpty() || quantity.isEmpty() || description.isEmpty() || image.isEmpty() || price.isEmpty() || qrCodePath.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        int parsedQuantity;
        double parsedPrice;
        try {
            parsedQuantity = Integer.parseInt(quantity);
            parsedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid quantity or price format.");
            return;
        }

        // Check if price is valid
        if (parsedPrice == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Price cannot be zero.");
            return;
        }

        // Check if description length is valid
        if (description.length() < 10) {
            showAlert(Alert.AlertType.ERROR, "Error", "Description must be at least 10 characters long.");
            return;
        }

        // Check if the quantity is low
        if (parsedQuantity <= 2) {
            showAlert(Alert.AlertType.WARNING, "Low Quantity", "The quantity of the medicament is low. Consider restocking: " + name);
            toggleNotificationPanelVisibility(); // Show the notification panel
        }

        // Add the medicament
        List<Medicament> existingMedicaments = medicamentService.searchMedicamentByName(name);
        if (!existingMedicaments.isEmpty()) {
            // Update the quantity for existing medicament
            Medicament existingMedicament = existingMedicaments.get(0);
            existingMedicament.setQuantity(existingMedicament.getQuantity() + parsedQuantity);
            boolean updated = medicamentService.modifierMedicament(existingMedicament);
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Quantity updated successfully for existing Medicament.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update quantity for existing Medicament.");
            }
        } else {
            // Add a new medicament
            Medicament newMedicament = new Medicament(0, name, parsedQuantity, description, image, parsedPrice, qrCodePath);
            int id = medicamentService.ajouterMedicament(newMedicament);

            if (id != -1) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament added successfully with ID: " + id);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the medicament.");
            }
        }
    }


    @FXML
    private void handleChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
        if (selectedFile != null) {
            // Set the chosen file
            imageField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleModifierMedicament(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierMedicament.fxml"));
            Parent modifierMedicamentForm = loader.load();
            borderPane.setCenter(modifierMedicamentForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSupprimerMedicament(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/commandeInterface.fxml"));
            Parent supprimerMedicamentForm = loader.load();
            borderPane.setCenter(supprimerMedicamentForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAfficherMedicaments(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherMedicaments.fxml"));
            Parent medicamentForm = loader.load();

            // Loaded FXML controller
            AfficherMedicamentsController controller = loader.getController();

            // ListView initialization from the database
            controller.initialize();

            // Form in center
            borderPane.setCenter(medicamentForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
