package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;


import entites.Medicament;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import services.MedicamentService;

import java.util.List;
import java.util.Optional;

public class AfficherMedicamentsController {

    @FXML
    private ListView<Medicament> medicamentListView;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void loadMedicaments() {
        // Fetch mel database bel les servicce mt3na
        MedicamentService medicamentService = new MedicamentService();
        List<Medicament> medicaments = medicamentService.afficherMedicaments();

        // Clear item fel listview
        medicamentListView.getItems().clear();

        // nzidp  the fetched medications lel  listvview
        medicamentListView.getItems().addAll(medicaments);
    }

    @FXML
    private ImageView imgproduit;
    @FXML
    private Label txtnom;
    @FXML
    private Label txtprix;
    @FXML
    private Label txtdesc;
    @FXML
    private Button img;

    private Medicament medicament;
    @FXML
    private ImageView qrCodeImageView;

    public void initialize() {
        // Load data
        loadMedicaments();

        // setCellFactory render el item fel list view
        medicamentListView.setCellFactory(new Callback<>() {
            @Override
            // param represntilna el listview
            public ListCell<Medicament> call(ListView<Medicament> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Medicament item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox vBox = new VBox();
                            vBox.setSpacing(5); // space between the elements

                            Label nameLabel = new Label("Name: " + item.getName());
                            nameLabel.setFont(Font.font(14));

                            Label descriptionLabel = new Label("Description: " + item.getDescription());
                            descriptionLabel.setFont(Font.font(12));
                            descriptionLabel.setWrapText(true);
                            descriptionLabel.setMaxWidth(300);

                            Label priceLabel = new Label("Price: " + item.getPrice());
                            priceLabel.setFont(Font.font(14));

                            Label quantityLabel = new Label("Quantity: " + item.getQuantity());
                            quantityLabel.setFont(Font.font(14));

                            ImageView imageView = new ImageView();
                            try {
                                String imagePath = item.getImage();
                                Image image = new Image(imagePath);
                                imageView.setImage(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                            } catch (Exception e) {
                                // handle image exception
                                e.printStackTrace();

                                Image errorImage = new Image("error.png");
                                imageView.setImage(errorImage);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                            }

                            // Generate and display QR code for the current Medicament
                            ImageView qrCodeImageView = new ImageView();
                            generateAndDisplayQRCode(item, qrCodeImageView);

                            vBox.getChildren().addAll(nameLabel, descriptionLabel, priceLabel, quantityLabel, imageView, qrCodeImageView);

                            setGraphic(vBox);
                        }
                    }
                };
            }
        });
    }



    private void generateAndDisplayQRCode(Medicament medicament, ImageView qrCodeImageView) {
        String directoryPath = "C:/Users/21695/Downloads/qrcodes/";
        String qrCodePath = directoryPath + medicament.getName() + ".png"; // Path to save the QR code image
        try {
            // Create the directory if it doesn't exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directories recursively
            }

            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(medicament.getName(), BarcodeFormat.QR_CODE, 200, 200);

            // Save QR code to file
            Path path = new File(qrCodePath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            // Display QR code in ImageView
            Image image = new Image(path.toUri().toString());
            qrCodeImageView.setImage(image); // Set QR code image to the ImageView
            qrCodeImageView.setFitWidth(100);
            qrCodeImageView.setFitHeight(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void modifyMedicament() {
        // selected medicament
        Medicament selectedMedicament = medicamentListView.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modify Medicament");
            dialog.setHeaderText("Modify " + selectedMedicament.getName());
            dialog.setContentText("Enter new name:");


            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newName -> {

                selectedMedicament.setName(newName);

                // Update fel database
                MedicamentService medicamentService = new MedicamentService();
                boolean updated = medicamentService.modifierMedicament(selectedMedicament);
                if (updated) {
                    // refrech listview
                    loadMedicaments();
                } else {
                    showAlert("Error", "Failed to modify the medicament.");
                }
            });
        } else {
            showAlert("No Medicament Selected", "Please select a medicament to modify.");
        }
    }

    @FXML
    private void deleteMedicament() {
        // selection
        Medicament selectedMedicament = medicamentListView.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            // Confirmation bel dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete " + selectedMedicament.getName());
            alert.setContentText("Are you sure you want to delete this medicament?");

            // client choice
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //confirmation bech nfs5o mel bd
                MedicamentService medicamentService = new MedicamentService();
                boolean deleted = medicamentService.supprimerMedicament(selectedMedicament);
                if (deleted) {
                    // confirmiw donc nfs5o mel list view
                    medicamentListView.getItems().remove(selectedMedicament);
                } else {
                    showAlert("Error", "Failed to delete the medicament.");
                }
            }
        } else {
            showAlert("No Medicament Selected", "Please select a medicament to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
