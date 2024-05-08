package tn.MediZen.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BackDocteur implements Initializable {

    @FXML
    private Button redirectToCalendar;
    @FXML
    private TextField AdresseTF;

    @FXML
    private DatePicker DateReservationF;

    @FXML
    private TextField DescriptionDeProblemeTF;

    @FXML
    private TextField NomTF;

    @FXML
    private TextField NumeroTelephone;

    @FXML
    private TextField PrenomTF;

    @FXML
    private TextArea StatusTF;

    @FXML
    private Button RejeterReservationBTN;

    @FXML
    private ListView<Reservation> ListReservationsT;

    @FXML
    private Button ValiderReservationBTN;

    private final ReservationService reservationService = new ReservationService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshReservations();
        ListReservationsT.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    Label nomLabel = new Label("Nom: " + item.getName());
                    Label prenomLabel = new Label("Prenom: " + item.getSurname());
                    Label mailLabel = new Label("Description de problème: " + item.getProblemDescription());
                    Label adresseLabel = new Label("Adresse: " + item.getAddress());
                    Label specialiteLabel = new Label("Mobile: " + item.getMobile());
                    Label experienceLabel = new Label("Date de réservation: " + item.getReservationDate());
                    Label mobileLabel = new Label("Status: " + item.getStatus());
                    VBox docteurInfoLayout = new VBox(nomLabel, prenomLabel, mailLabel,
                            adresseLabel, specialiteLabel, experienceLabel, mobileLabel);
                    docteurInfoLayout.setSpacing(5);
                    Separator separator = new Separator();
                    separator.setStyle("-fx-background-color: #87CEEB;");
                    VBox reservationLayout = new VBox(docteurInfoLayout, separator);
                    reservationLayout.setSpacing(10);
                    setGraphic(reservationLayout);
                }
            }
        });
    }

    @FXML
    public void gotoCalendar(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Calendar.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) scene.getRoot()).getScene().getWindow();
            //stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void ValiderReservation(javafx.event.ActionEvent actionEvent) {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Accepted");
            reservationService.update(selectedReservation);
            refreshReservations();
        }
    }
    @FXML
    public void RejeterReservationBTN(javafx.event.ActionEvent actionEvent) {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Rejected");
            reservationService.update(selectedReservation);
            refreshReservations();
        }
    }

    private void refreshReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsT.setItems(reservations);
    }

    public void home_btn(ActionEvent actionEvent) {
    }

    public void event_btn(ActionEvent actionEvent) {
    }

    public void sponseur_btn(ActionEvent actionEvent) {
    }

    public void sujet_btn(ActionEvent actionEvent) {
    }

    public void etablissement_btn(ActionEvent actionEvent) {
    }

    public void revervation_btn(ActionEvent actionEvent) {
    }

    public void medicament_btn(ActionEvent actionEvent) {
    }

    public void docteur_btn(ActionEvent actionEvent) {
    }

    public void docteur_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void medicament_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void revervation_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void etablissement_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void sujet_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void sponseur_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void home_btn(javafx.event.ActionEvent actionEvent) {
    }

    public void event_btn(javafx.event.ActionEvent actionEvent) {
    }


    public Button getRejeterReservationBTN() {
        return RejeterReservationBTN;
    }

    public void setRejeterReservationBTN(Button rejeterReservationBTN) {
        RejeterReservationBTN = rejeterReservationBTN;
    }

    public Button getValiderReservationBTN() {
        return ValiderReservationBTN;
    }

    public void setValiderReservationBTN(Button validerReservationBTN) {
        ValiderReservationBTN = validerReservationBTN;
    }
}



