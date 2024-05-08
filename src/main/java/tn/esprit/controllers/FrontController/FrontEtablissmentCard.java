package tn.esprit.controllers.FrontController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;
import tn.esprit.models.Etablissement;

import java.io.IOException;

public class FrontEtablissmentCard {
    @FXML
    private ImageView img;

    @FXML
    private Label etab_Localisation;

    @FXML
    private Label etab_descrp;

    @FXML
    private Label etab_lati;

    @FXML
    private Label etab_long;

    @FXML
    private Label etab_nom;

    @FXML
    private Label etab_type;


    private Etablissement etablissement;

    public void initialize() {
        // Load the image and set it to the ImageView
        Image image = new Image("file:/resources/image/malek.jpg");
        img.setImage(image);
    }
    public void getData(Etablissement etablissement) {
        this.etablissement = etablissement;
        etab_nom.setText(etablissement.getName());
        etab_type.setText(etablissement.getTypee());
        etab_descrp.setText(String.valueOf(etablissement.getDescription()));
        etab_Localisation.setText(String.valueOf(etablissement.getLocation()));
        etab_lati.setText(String.valueOf(etablissement.getLatitude()));
        etab_long.setText(String.valueOf(etablissement.getLongitude()));


    }
    @FXML
    void Postuler(ActionEvent event) {
        try {
            // Load the Candidatures.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/offre/addCandidatureFront.fxml"));
            Parent candidaturesRoot = loader.load();

            // Create a new stage for the Candidatures scene
            Stage candidaturesStage = new Stage();
            candidaturesStage.setScene(new Scene(candidaturesRoot));
            candidaturesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void GoTo(ActionEvent actionEvent) {

        try {
            // Load the Candidatures.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/FrontDepartment.fxml"));
            Parent candidaturesRoot = loader.load();

            FrontDepartment obj = loader.getController();
            obj.setId_dep(etablissement.getId());
            // Create a new stage for the Candidatures scene
            Stage candidaturesStage = new Stage();
            candidaturesStage.setScene(new Scene(candidaturesRoot));
            candidaturesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

    }
}
