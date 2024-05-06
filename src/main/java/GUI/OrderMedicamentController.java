package GUI;

import entites.Commande;
import entites.Medicament;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.CommandeService;
import services.MedicamentService;
import GUI.TwilioSMS;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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

    private final MedicamentService medicamentService = new MedicamentService();

    public void initialize(URL url, ResourceBundle rb) {
        List<Medicament> medicaments = medicamentService.afficherMedicaments();

        if (!medicaments.isEmpty()) {
            Medicament selectedMedicament = medicaments.get(0);

            setMedicament(selectedMedicament);
        } else {

            setDefaultValues();
        }
    }
    private void setDefaultValues() {
        txtnom.setText("Name: N/A");
        txtprix.setText("Price: N/A");
        txtdesc.setText("Description: N/A");

        // Set a default image or hide the image view
        // Here, I'm setting a default image
        Image defaultImage = new Image("file:///C:/Users/21695/Downloads/.png");
        imgproduit.setImage(defaultImage);
        imgproduit.setFitWidth(100);
        imgproduit.setFitHeight(100);
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    private void displayMedicamentInfo() {
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
            // Create a new order for the ordered medication
            Commande newCommande = new Commande(0, medicament.getPrice(), 1, new Date(), "Pending");
            medicament.setQuantity(medicament.getQuantity() - 1);
            MedicamentService medicamentService = new MedicamentService();
            medicamentService.modifierMedicament(medicament);
            // Add the order to the database
            CommandeService commandeService = new CommandeService();
            int commandeId = commandeService.ajouterCommande(newCommande);

            if (commandeId != -1) {
                showAlert("Order Success", "Medication ordered successfully. Command ID: " + commandeId);
            } else {
                showAlert("Order Error", "Failed to order the medication. Please try again.");
            }
        } else {
            showAlert("Medication Error", "No medication selected for ordering.");
        }
        if (medicament.getQuantity() <= 2) {
            String notificationMessage = "The quantity of the medicament '" + medicament.getName() + "' is low. Description: " + medicament.getDescription();
            TwilioSMS.sendSMS("+21695055670", notificationMessage);
        }

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
