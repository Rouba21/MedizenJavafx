package tn.esprit.controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
    private BorderPane borderPanetf;

    @FXML
    private Label welcomeid;

    @FXML
    private AnchorPane main_forum;

    @FXML
    void AjouterSponseur(ActionEvent event) {

    }

    @FXML
    void MoveToBack(ActionEvent event) throws IOException {
        Parent root5 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Docteur/BackDocteur.fxml")));
        Stage window = (Stage) MoveToBack.getScene().getWindow();
        window.setScene(new Scene(root5));
    }


    @FXML
    private void revervation_btn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/AjouterReservation.fxml"));
            AnchorPane ajouterReservationPane = loader.load();

            // Get the controller
            AjouterReservation controller = loader.getController();

            // Initialize the controller
            controller.initialize();

            // Replace the content of the main_forum AnchorPane with the loaded form
            main_forum.getChildren().setAll(ajouterReservationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("/Reservation/Home.fxml"));
    }

    public void docteur_btn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    public void sujet_btn(ActionEvent actionEvent) {

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
    void event_btn(ActionEvent event) {

    }


}
