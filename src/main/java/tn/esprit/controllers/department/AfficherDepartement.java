package tn.esprit.controllers.department;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Departement;

import tn.esprit.services.departementservice;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherDepartement implements Initializable {

    private departementservice departementService = new departementservice();

    @FXML
    private TextField searchField;

    @FXML
    private FlowPane cardLayout2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshDisplay();
    }

    @FXML
    void searchDepartement() throws SQLException {
        String keyword = searchField.getText().toLowerCase().trim();
        List<Departement> departements = departementService.afficher();
        FilteredList<Departement> filteredDepartements = new FilteredList<>(FXCollections.observableArrayList(departements));
        filteredDepartements.setPredicate(departement -> departementContainsKeyword(departement, keyword));
        //updateDisplay(filteredDepartements);
    }

    private boolean departementContainsKeyword(Departement departement, String keyword) {
        return departement.getNom().toLowerCase().contains(keyword) ||
                departement.getDescription().toLowerCase().contains(keyword) ||
                departement.getChef_departement().toLowerCase().contains(keyword) ||
                departement.getService_offerts().toLowerCase().contains(keyword) ||
                departement.getLocalisation().toLowerCase().contains(keyword);
    }

    @FXML
    void ajouterDepartement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/AddDepartement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void refreshPage(ActionEvent event) {
        refreshDisplay();
    }

    private void refreshDisplay() {
        try {
            List<Departement> departements = departementService.afficher();
            cardLayout2.getChildren().clear();
            display(departements);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void display(List<Departement> departements) {
        for (Departement departement : departements) {
            VBox card = createDepartementCard(departement);
            cardLayout2.getChildren().add(card);
        }
    }

    private VBox createDepartementCard(Departement departement) {
        VBox card = new VBox();
        card.getStyleClass().add("commande-card");
        Label nom = new Label("Nom: " + departement.getNom());
        Label description = new Label("Description: " + departement.getDescription());
        Label chefDepartement = new Label("Chef Département: " + departement.getChef_departement());
        Label servicesOfferts = new Label("Services Offerts: " + departement.getService_offerts());
        Label localisation = new Label("Localisation: " + departement.getLocalisation());

        card.getChildren().addAll(nom, description, chefDepartement, servicesOfferts, localisation);

        card.setCursor(Cursor.HAND);
        return card;
    }
}
