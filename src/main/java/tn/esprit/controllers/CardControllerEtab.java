package tn.esprit.controllers;
import tn.esprit.models.Etablissement;
import tn.esprit.services.etablissementservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


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

    int id ;

    public void setid(int id) {
        this.id = id;
    }


    @FXML
    void supprimerEtablissement(ActionEvent event) throws SQLException {
        SP.supprimer(id);

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
            System.out.println(etablissements);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/Etabblissement/UpdateEtablissement.fxml"));
            Parent root = loader.load();
            ModifierEtablissement editTicketController = loader.getController();
            editTicketController.setData(etablissements);
            editTicketController.setid(id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit ");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue de modification : " + e.getMessage());
        }

    }

    public void setControllerEtab(Etablissement etablissement) {
        this.etablissements=etablissement;
    }


    // Vous pouvez ajouter d'autres méthodes ou logique selon vos besoins
}