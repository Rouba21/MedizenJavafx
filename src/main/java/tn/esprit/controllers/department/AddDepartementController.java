package tn.esprit.controllers.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Departement;
import tn.esprit.models.Etablissement;
import tn.esprit.services.departementservice;

import java.sql.SQLException;

public class AddDepartementController {


    @FXML
    private TextField idetab;

    @FXML
    private TextField IDNom;

    @FXML
    private TextField IDDescription;

    @FXML
    private TextField IDChefDepartement;

    @FXML
    private TextField IDServicesOfferts;

    @FXML
    private TextField IDLocalisation;

    private departementservice departementservice = new departementservice();

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


            String nom = IDNom.getText();
            String description = IDDescription.getText();
            String chefDepartement = IDChefDepartement.getText();
            String servicesOfferts = IDServicesOfferts.getText();
            String localisation = IDLocalisation.getText();
            int idetabb = Integer.parseInt(idetab.getText());
            // Validate input fields
            if (nom.isEmpty() || description.isEmpty() || chefDepartement.isEmpty() || servicesOfferts.isEmpty() || localisation.isEmpty()) {
                showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
                return;
            }

            Departement departement = new Departement(nom, description, chefDepartement, servicesOfferts, localisation , idetabb);

            // Call the method to insert the department into the database
            departementservice.ajouter(departement);

            // Optionally, refresh the UI or perform other actions after adding the department

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du département à la base de données. Veuillez réessayer plus tard ou contacter l'administrateur.");
            // Log the SQL exception for further analysis
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur inattendue s'est produite. Veuillez réessayer ou contacter l'administrateur.");
            // Log any other unexpected exceptions
            e.printStackTrace();
        }
    }
}
