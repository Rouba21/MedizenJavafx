package tn.MediZen.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class BackDocteur implements Initializable {

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
                    setText("ID: " + item.getId() + ", Name: " + item.getName() + ", Surname: " + item.getSurname() + ", Status: " + item.getStatus());
                }
            }
        });
    }

    private void refreshReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsT.setItems(reservations);
    }

    @FXML
    public void ValiderReservation(ActionEvent event) {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Accepted");
            reservationService.update(selectedReservation);
            refreshReservations();
        }
    }

    @FXML
    public void RejeterReservationBTN(ActionEvent event) {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Rejected");
            reservationService.update(selectedReservation);
            refreshReservations();
        }
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
}
