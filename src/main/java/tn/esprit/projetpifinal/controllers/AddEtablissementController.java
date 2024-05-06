package tn.esprit.projetpifinal.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.projetpifinal.controllers.AfficherEtablissement;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;


import java.math.BigDecimal;
import java.sql.SQLException;

public class AddEtablissementController {

    @FXML
    private TextField IDLatitude;

    @FXML
    private TextField IDLocalisation;

    @FXML
    private TextField IDLongitude;

    @FXML
    private TextField IDdescrp;

    @FXML
    private TextField IDname;

    @FXML
    private TextField IDtype;

    etablissementservice sp = new etablissementservice();

    public AddEtablissementController() {
    }


    @FXML
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    void ajouterBtn(ActionEvent event) {
        try {

            String description = IDdescrp.getText();
            String latitudeText = IDLatitude.getText();
            String location = IDLocalisation.getText();
            String longitudeText = IDLongitude.getText();
            String name = IDname.getText();
            String typee = IDtype.getText();


            // Vérifier que les champs ne sont pas vides
            if (!name.matches("[a-zA-Z]+")) {
                showAlert("Erreur de saisie", "Le nom ne doit contenir que des lettres.");
                return;
            }
            if (description.length() < 20) {
                showAlert("Erreur de saisie", "La description doit comporter au moins 20 caractères.");
                return;
            }
            BigDecimal latitude;
            BigDecimal longitude;
            try {
                latitude = new BigDecimal(latitudeText);
                longitude = new BigDecimal(longitudeText);
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Latitude et longitude doivent être des décimaux valides.");
                return;
            }

            if (description.isEmpty() || latitudeText.isEmpty() || location.isEmpty() || longitudeText.isEmpty() || name.isEmpty() || typee.isEmpty()) {
                showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
                return;
            }
            Etablissement etablissement = new Etablissement(typee, description, location, name, latitude, longitude);

            // Call the method to insert the etablissement into the database
            etablissementservice etablissementService = new etablissementservice();
            etablissementService.ajouter(etablissement);

            // Refresh the table after adding an etablissement
            new AfficherEtablissement();
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout de l'établissement à la base de données. Veuillez réessayer plus tard ou contacter l'administrateur.");
            // Log the SQL exception for further analysis
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur inattendue s'est produite. Veuillez réessayer ou contacter l'administrateur.");
            // Log any other unexpected exceptions
            e.printStackTrace();
        }
    }


}
