package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button AjouterSponseur;

    @FXML
    private AnchorPane logout;

    @FXML
    private Button logoutt;

    @FXML
    private AnchorPane main_forum;

    @FXML
    private Label welcomeid;

    @FXML
    void AjouterSponseur(ActionEvent event) {

    }

    @FXML
    void event_btn(ActionEvent event) {

        try {
            // Navigation vers une autre vue (AfficherSponseur.fxml)
            // Assurez-vous d'ajuster le chemin d'accès si nécessaire
            Parent root = FXMLLoader.load(getClass().getResource("/Event.fxml"));
            welcomeid.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void home_btn(ActionEvent event) {

    }

    @FXML
    void naviguer(ActionEvent event) {

    }

    @FXML
    void sponseur_btn(ActionEvent event) {

        try {
            // Navigation vers une autre vue (AfficherSponseur.fxml)
            // Assurez-vous d'ajuster le chemin d'accès si nécessaire
            Parent root = FXMLLoader.load(getClass().getResource("/Sponseur.fxml"));
            welcomeid.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    @FXML
    void medicament_btn(ActionEvent event) {

    }

    @FXML
    void revervation_btn(ActionEvent event) {

    }


    @FXML
    void sujet_btn(ActionEvent event) {

    }
}
