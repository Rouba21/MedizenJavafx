package tn.MediZen.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;

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
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsT.setItems(reservations);
        ListReservationsT.setOnMouseClicked((MouseEvent event) -> {
            Reservation current = ListReservationsT.getSelectionModel().getSelectedItem();
            NomTF.setText(current.getName());
            PrenomTF.setText(current.getSurname());
            NumeroTelephone.setText(String.valueOf(current.getMobile()));
            DescriptionDeProblemeTF.setText(current.getProblemDescription());
            AdresseTF.setText(current.getAddress());
            StatusTF.setText(current.getStatus());

        });
    }

    public void ValiderReservation() {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Accepted");
            reservationService.update(selectedReservation);
            ListReservationsT.refresh();
        }
    }

    public void RejeterReservationBTN() {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Rejected");
            reservationService.update(selectedReservation);
            ListReservationsT.refresh();
        }
    }


    public void go_userback(ActionEvent actionEvent) {
    }

    public void go_blogback(ActionEvent actionEvent) {
    }

    public void go_employeback(ActionEvent actionEvent) {
    }

    public void go_reservationback(ActionEvent actionEvent) {
    }

    public void go_offreback(ActionEvent actionEvent) {
    }


}
