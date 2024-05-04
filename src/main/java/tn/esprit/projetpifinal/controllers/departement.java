/*package tn.esprit.projetpifinal.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.projetpifinal.models.Departement;
import tn.esprit.projetpifinal.service.departementservice;

import java.math.BigDecimal;
import java.sql.SQLException;

public class departement {

    private departementservice departementservice = new departementservice();

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
    private TableColumn<Departement, String> colName;

    @FXML
    private TableColumn<Departement, String> colDescrp;

    @FXML
    private TableColumn<Departement, String> colChef;

    @FXML
    private TableColumn<Departement, String> colServices;

    @FXML
    private TableColumn<Departement, String> colLocation;

    @FXML
    private TextArea descrp;

    @FXML
    private TextField chef_departement;

    @FXML
    private TextField localisation;

    @FXML
    private TextField nom;

    @FXML
    private TextField services_offerts;

    @FXML
    private TableView<Departement> table;

    @FXML
    void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescrp.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        colServices.setCellValueFactory(new PropertyValueFactory<>("service_offerts"));
        colChef.setCellValueFactory(new PropertyValueFactory<>("chef_departement"));

        afficherDepartements();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void afficherDepartements() {
        try {
            table.getItems().setAll(departementservice.selectAll());
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des départements !");
        }
    }

    @FXML
    void clearField(ActionEvent event) {
        nom.clear();
        descrp.clear();
        chef_departement.clear();
        services_offerts.clear();
        localisation.clear();
    }

    @FXML
    void createDepartement(ActionEvent event) {
        try {
            String nomDepartement = nom.getText();
            String descriptionDepartement = descrp.getText();
            String chefDepartementText = chef_departement.getText();
            String serviceOffertsText = services_offerts.getText();
            String localisationText = localisation.getText();

            // Vérification des saisies
            if (!nomDepartement.matches("[a-zA-Z]+")) {
                showAlert("Erreur de saisie", "Le nom du département ne doit contenir que des lettres.");
                return;
            }

            // Autres vérifications de saisie...

            Departement departement = new Departement(nomDepartement, descriptionDepartement, chefDepartementText, serviceOffertsText, localisationText);

            departementservice.insertOne(departement);

            afficherDepartements();
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du département à la base de données.");
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur inattendue s'est produite. Veuillez réessayer ou contacter l'administrateur.");
            e.printStackTrace();
        }
    }

    @FXML
    void deleteDepartement(ActionEvent event) {
        Departement selectedDepartement = table.getSelectionModel().getSelectedItem();
        if (selectedDepartement != null) {
            try {
                departementservice.deleteOne(selectedDepartement.getId());
                afficherDepartements();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression du département !");
            }
        } else {
            showAlert("Aucune sélection", "Aucun département sélectionné");
        }
    }

    @FXML
    void selectDepartement(ActionEvent event) {
       Departement selectedDepartement = table.getSelectionModel().getSelectedItem();
        if (selectedDepartement != null) {
            nom.setText(selectedDepartement.getNom());
            descrp.setText(selectedDepartement.getDescription());
            chef_departement.setText(selectedDepartement.getChef_departement());
            services_offerts.setText(selectedDepartement.getService_offerts());
            localisation.setText(selectedDepartement.getLocalisation());
        } else {
            showAlert("Aucune sélection", "Aucun département sélectionné");
        }
    }

    @FXML
    void updateDepartement(ActionEvent event) {
        Departement selectedDepartement = table.getSelectionModel().getSelectedItem();
        if (selectedDepartement != null) {
            try {
                String nomDepartement = nom.getText();
                String descriptionDepartement = descrp.getText();
                String chefDepartementText = chef_departement.getText();
                String serviceOffertsText = services_offerts.getText();
                String localisationText = localisation.getText();

                // Vérification des saisies...

                selectedDepartement.setNom((nom.getText()));
                selectedDepartement.setDescription((descrp.getText()));
                selectedDepartement.setChef_departement((chef_departement.getText()));
                selectedDepartement.setService_offerts((services_offerts.getText()));
                selectedDepartement.setLocalisation((localisation.getText()));

                departementservice.updateOne(selectedDepartement);

                afficherDepartements();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour du département !");
            }
        } else {
            showAlert("Aucune sélection", "Aucun département sélectionné");
        }
    }
}
*/