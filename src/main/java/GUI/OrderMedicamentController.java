package GUI;

import entites.Commande;
import entites.Medicament;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.CommandeService;

import java.util.Date;

public class OrderMedicamentController {

    @FXML
    private ImageView imgproduit;
    @FXML
    private Label txtnom;
    @FXML
    private Label txtprix;
    @FXML
    private Label txtdesc;
    @FXML
    private Button buyButton;

    private Medicament medicament;

    public void initialize() {
        // Set the information of the selected medication
        displayMedicamentInfo();
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    private void displayMedicamentInfo() {
        // Display the information of the selected medication
        if (medicament != null) {
            txtnom.setText(medicament.getName());
            txtprix.setText(String.valueOf(medicament.getPrice()));
            txtdesc.setText(medicament.getDescription());

            // Load and display the image
            try {
                String imagePath = "file:///C:/Users/21695/Downloads/" + medicament.getImage();
                Image image = new Image(imagePath);
                imgproduit.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle image loading exception
            }
        }
    }

    @FXML
    private void orderMedicament() {

        if (medicament != null) {
            // ncreatiw comande lel medicament ordered
            Commande newCommande = new Commande(0, medicament.getPrice(), 1, new Date(), "Pending");

            // nzidaha lel database
            CommandeService commandeService = new CommandeService();
            int commandeId = commandeService.ajouterCommande(newCommande);

            if (commandeId != -1) {

                showAlert("Order Success", "Medication ordered successfully. Command ID: " + commandeId);
            } else {
                //  error message
                showAlert("Order Error", "Failed to order the medication. Please try again.");
            }
        } else {
            showAlert("Medication Error", "No medication selected for ordering.");
        }
    }

    private void showAlert(String title, String content) {
        // alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
