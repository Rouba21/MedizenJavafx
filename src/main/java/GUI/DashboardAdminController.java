package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import services.MedicamentService;
import entites.Medicament;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.input.KeyEvent;

public class DashboardAdminController {
    @FXML
    private TextField searchBar;

    private final MedicamentService medicamentService = new MedicamentService();



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

    @FXML
    private void handleAddMedicament(ActionEvent event) {
        String name = nameField.getText();
        String quantity = quantityField.getText();
        String description = descriptionField.getText();
        String image = imageField.getText();
        String price = priceField.getText();
        String qrCodePath = qrCodePathField.getText();

        // Validation l'input mte3 les fieald
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

        // control sisisii 3al price
        if (parsedPrice == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Price cannot be zero.");
            return;
        }

        if (description.length() < 10) {
            showAlert(Alert.AlertType.ERROR, "Error", "Description must be at least 10 characters long.");
            return;
        }

        // 3al esm
        MedicamentService medicamentService = new MedicamentService();
        List<Medicament> existingMedicaments = medicamentService.searchMedicamentByName(name);
        if (!existingMedicaments.isEmpty()) {
            // nzido el quantity lel medicament l9dim
            Medicament existingMedicament = existingMedicaments.get(0); // medicament barka
            existingMedicament.setQuantity(existingMedicament.getQuantity() + parsedQuantity);
            boolean updated = medicamentService.modifierMedicament(existingMedicament);
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Quantity updated successfully for existing Medicament.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update quantity for existing Medicament.");
            }
        } else {
            // nzido medicament
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
            // set the file chosed
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("supprimerMedicament.fxml"));
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

            //   loaded FXML controlleur
            AfficherMedicamentsController controller = loader.getController();

            // listview init mel bd
            controller.initialize();

            // form in cneter
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
