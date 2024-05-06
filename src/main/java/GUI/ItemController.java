package GUI;

import entites.Medicament;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.MedicamentService;
import com.stripe.exception.StripeException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

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
    @FXML
    private Button payButton;
    private Medicament medicament;
    private final MedicamentService medicamentService = new MedicamentService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Medicament> medicaments = medicamentService.afficherMedicaments();

        if (!medicaments.isEmpty()) {
            Medicament selectedMedicament = medicaments.get(0);

            setMedicament(selectedMedicament);
        } else {

            setDefaultValues();
        }
    }
    public Parent getView() {
        return imgproduit.getParent();
    }
    // Method to set the medication details to the interface
    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
        String name = "Name: " + medicament.getName();
        String price = "Price: " + medicament.getPrice();
        String desc = "Description: " + medicament.getDescription();

        txtnom.setText(name);
        txtprix.setText(price);
        txtdesc.setText(desc);

        // Load and display the image
        String imagePath =medicament.getImage();
        System.out.println("Image path: " + imagePath); // Print out image path for debugging

        File imageFile = new File(imagePath);

        Image image = new Image(imageFile.toURI().toString());
        imgproduit.setImage(image);
        imgproduit.setFitWidth(100);
        imgproduit.setFitHeight(100);

        System.err.println("Image file not found: " + imagePath); // Log the error

        imgproduit.setFitWidth(100);
        imgproduit.setFitHeight(100);
    }







    // Method to set default values to the interface elements
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

    // Method to handle the "Buy Now" button action
    @FXML
    public void handleBuyButtonAction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderMedicament.fxml"));
            Parent root = loader.load();

            OrderMedicamentController controller = loader.getController();

            controller.setMedicament(medicament);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Order Medicament");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open order window.");
        }
    }

    @FXML
    public void handlePayButtonAction() {
        try {
            float medicationPrice = Float.parseFloat(txtprix.getText().split(": ")[1]); // Extract price from label
            String currency = "usd";
            String userEmail = "example@example.com";

            // Process payment using PaymentProcessor
            boolean paymentResult = PaymentProcessor.processPayment(medicationPrice, currency, userEmail);

            // Check payment result
            if (paymentResult) {
                showAlert("Success", "Payment successful! Your order has been placed.");
            } else {
                showAlert("Error", "Payment failed. Please try again later.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while processing the payment.");
        }
    }


    // Method to display an alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

