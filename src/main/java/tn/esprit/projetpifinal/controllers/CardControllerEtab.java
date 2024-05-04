package tn.esprit.projetpifinal.controllers;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;


import java.io.IOException;
import java.sql.SQLException;


public class CardControllerEtab {

    @FXML
    private Label IDLocalisation;

    @FXML
    private Label IDType;

    @FXML
    private Label IDdescrp;

    @FXML
    private Label IDlati;

    @FXML
    private Label IDlong;

    @FXML
    private Label IDnom;

    private etablissementservice SP = new etablissementservice();
    private Etablissement etablissements;

    @FXML
    void supprimerEtablissement(ActionEvent event) throws SQLException {
        SP.supprimer(etablissements.getId());
        Stage stage = (Stage) IDnom.getScene().getWindow(); // Assuming IDnom is a label in your FXML
        stage.close();
    }

    public void displayEtablissementDetails(Etablissement etablissement) {

        IDnom.setText("Nom: " + etablissement.getName());
        IDType.setText("Type: " + etablissement.getTypee());
        IDLocalisation.setText("Localisation: " + etablissement.getLocation());
        IDdescrp.setText("Description: " + etablissement.getDescription());
        IDlati.setText("Latitude: " + etablissement.getLatitude());
        IDlong.setText("Longitude: " + etablissement.getLongitude());
    }
    @FXML
    void modifierEtablissement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEtablissement.fxml"));
            Parent root = loader.load();
            ModifierEtablissement editTicketController = loader.getController();
            editTicketController.setData(etablissements);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit ");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue de modification : " + e.getMessage());
        }

    }
    // Vous pouvez ajouter d'autres méthodes ou logique selon vos besoins
}