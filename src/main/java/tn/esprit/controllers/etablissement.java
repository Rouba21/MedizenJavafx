/*package tn.esprit.projetpifinal.controllers;

import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.sql.SQLException;

public class etablissement {

    etablissementservice ps = new etablissementservice();

    @FXML
    private Button btnclear;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnselect;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<Etablissement, String> colDescrp;

    @FXML
    private TableColumn<Etablissement, BigDecimal> colLati;

    @FXML
    private TableColumn<Etablissement, String> colLocation;

    @FXML
    private TableColumn<Etablissement, BigDecimal> colLongi;

    @FXML
    private TableColumn<Etablissement, String> colName;

    @FXML
    private TableColumn<Etablissement, String> colType;

    @FXML
    private TextArea descrp;

    @FXML
    private TextField lati;

    @FXML
    private TextField localisation;

    @FXML
    private TextField longi;

    @FXML
    private TextField nom;

    @FXML
    private TableView<Etablissement> table;

    @FXML
    private TextField type;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    @FXML
    void initialize() {
        colDescrp.setCellValueFactory(new PropertyValueFactory<>("description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typee"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colLati.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        colLongi.setCellValueFactory(new PropertyValueFactory<>("Longitude"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        afficheretablissement(); //refrech el tableview
    }

    public void afficheretablissement() {
        try {
            table.getItems().setAll(ps.selectAll()); // Accessing etablissementservice methods via 'ps' instance
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des Etablissement!");
        }
    }

    @FXML
    void clearField(ActionEvent event) {
        descrp.clear();
        lati.clear();
        localisation.clear();
        longi.clear();
        nom.clear();
        type.clear();
    }

    @FXML
    void createEtablissement(ActionEvent event) {
        try {
            // Retrieve values from UI controls
            String description = descrp.getText();
            String latitudeText = lati.getText();
            String location = localisation.getText();
            String longitudeText = longi.getText();
            String name = nom.getText();
            String typee = type.getText();

            // Vérifier que le nom contient uniquement des lettres
            if (!name.matches("[a-zA-Z]+")) {
                showAlert("Erreur de saisie", "Le nom ne doit contenir que des lettres.");
                return;
            }

            // Vérifier que la description comporte au moins 20 caractères
            if (description.length() < 20) {
                showAlert("Erreur de saisie", "La description doit comporter au moins 20 caractères.");
                return;
            }

            // Vérifier que latitude et longitude sont des décimaux valides
            BigDecimal latitude;
            BigDecimal longitude;
            try {
                latitude = new BigDecimal(latitudeText);
                longitude = new BigDecimal(longitudeText);
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Latitude et longitude doivent être des décimaux valides.");
                return;
            }

            // Vérifier que tous les champs sont remplis
            if (description.isEmpty() || latitudeText.isEmpty() || location.isEmpty() || longitudeText.isEmpty() || name.isEmpty() || typee.isEmpty()) {
                showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
                return;
            }

            // Create a new etablissement object
            Etablissement etablissement = new Etablissement(typee, description, location, name, latitude, longitude);

            // Call the method to insert the etablissement into the database
            etablissementservice etablissementService = new etablissementservice();
            etablissementService.insertOne(etablissement);

            // Refresh the table after adding an etablissement
            afficheretablissement();
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


    @FXML
    void deleteEtablissement(ActionEvent event) {
        Etablissement selectedEtablissement = table.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            try {
                // Get the ID of the selected offre
                int offreId = selectedEtablissement.getId();

                // Call the deleteOne function from the service to delete the Offre from the database
                etablissementservice etablissementservice = new etablissementservice();
                etablissementservice.deleteOne(selectedEtablissement.getId());

                // Refresh the table view after deleting
                afficheretablissement();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression de l'offre!");
            }
        } else {
            showAlert("Aucune sélection", "Aucune offre sélectionnée");
        }
        // Implement deletion logic here
    }

    @FXML
    void selectEtablissement(ActionEvent event) {
        Etablissement selectedEtablissement = table.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            // Set the values of UI controls with the selected Etablissement's properties
            descrp.setText(selectedEtablissement.getDescription());
            localisation.setText(selectedEtablissement.getLocation());
            nom.setText(selectedEtablissement.getName());
            lati.setText(String.valueOf(selectedEtablissement.getLatitude()));
            longi.setText(String.valueOf(selectedEtablissement.getLongitude()));
            type.setText(selectedEtablissement.getTypee());
        } else {
            showAlert("Aucune sélection", "Aucun etablissement sélectionnée");
        }
        // Implement selection logic here
    }

    @FXML
    void updateEtablissement(ActionEvent event) {
        Etablissement selectedEtablissement = table.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            try {
                // Retrieve values from UI controls
                String description = descrp.getText();
                String latitudeText = lati.getText();
                String location = localisation.getText();
                String longitudeText = longi.getText();
                String name = nom.getText();
                String typee = type.getText();


                // Update the selected etablissement object with the modified data
                selectedEtablissement.setDescription(descrp.getText());
                selectedEtablissement.setLatitude(new BigDecimal(lati.getText()));
                selectedEtablissement.setLocation((localisation.getText()));
                selectedEtablissement.setLongitude(new BigDecimal(longi.getText()));
                selectedEtablissement.setName(nom.getText());
                selectedEtablissement.setTypee(type.getText());

                // Call the updateOne function from the service to update the database
                etablissementservice etablissementservice = new etablissementservice();
                etablissementservice.updateOne(selectedEtablissement);

                // Refresh the table view after updating
                afficheretablissement();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour de l'etablissement!");
            }
        } else {
            showAlert("Aucune sélection", "Aucune etablissement sélectionnée");
        }
        // Implement update logic here
    }
}
*/