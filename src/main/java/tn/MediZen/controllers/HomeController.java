package tn.MediZen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    @FXML
    private Button MoveToBack;

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
    void MoveToBack(ActionEvent event) throws IOException {
        Parent root5 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/BackOffice/BackDocteur.fxml")));
        Stage window = (Stage) MoveToBack.getScene().getWindow();
        window.setScene(new Scene(root5));
    }
    @FXML
    void event_btn(ActionEvent event) {

    }

    @FXML
    void home_btn(ActionEvent event) {

    }

    @FXML
    void naviguer(ActionEvent event) {

    }

    @FXML
    void sponseur_btn(ActionEvent event) {

    }

    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    @FXML
    void medicament_btn(ActionEvent event) {

    }

    @FXML
    void revervation_btn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Reservation/AjouterReservation.fxml"));
        welcomeid.getScene().setRoot(root);
    }


    public void docteur_btn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    public void sujet_btn(ActionEvent actionEvent) {

    }
}
