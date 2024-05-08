package tn.esprit.controllers.Docteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.services.ReservationService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class BackDocteur implements Initializable {
    @FXML
    public Button gotoCalendar;
    @FXML
    private Button gotoStats;
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
            // Load the FXML file for the calendar view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Docteur/Calendar.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            CalendarController controller = loader.getController();

            LocalDate dateFocus = LocalDate.now(); // Or any other date you want to focus on
            List<Reservation> reservations = reservationService.getAll(); // Or fetchAppointmentsForWeek depending on your requirements

            // Pass appointments to the controller
            controller.setReservations(reservations);

            // Create a new stage for the calendar view
            Stage stage = new Stage();
            stage.setTitle("Calendar View");
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void gotoStats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Docteur/DocteurStats.fxml"));
            Parent root = loader.load();
            Stage window = new Stage();
            window.setScene(new Scene(root));
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void refreshReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsT.setItems(reservations);
    }

    @FXML
    public void ValiderReservation(javafx.event.ActionEvent actionEvent) {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Accepted");
            // Update the status of the selected reservation to "Accepted"
            reservationService.update(selectedReservation);
            // Refresh the list of reservations after updating
            refreshReservations();
        }
    }

    @FXML
    public void RejeterReservationBTN(javafx.event.ActionEvent actionEvent) {
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            selectedReservation.setStatus("Rejected");
            // Update the status of the selected reservation to "Rejected"
            reservationService.update(selectedReservation);
            // Refresh the list of reservations after updating
            refreshReservations();
        }
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

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("/Reservation/Home.fxml"));
    }

    public void event_btn(javafx.event.ActionEvent actionEvent) {
    }

}



