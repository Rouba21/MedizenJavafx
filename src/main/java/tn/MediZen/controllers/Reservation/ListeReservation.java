package tn.MediZen.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;

import java.io.IOException;
import java.util.Optional;

public class ListeReservation {

    @FXML
    private Label welcomeid;
    @FXML
    private ListView<Reservation> ListReservationsT;
    @FXML
    private Button ModifierButton;
    private final ReservationService reservationService = new ReservationService();

    @FXML
    public void initialize() {
        loadReservations();
    }

    private void loadReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsT.setItems(reservations);
    }

    @FXML
    public void SupprimerReservation() {
        Reservation reservationToDelete = ListReservationsT.getSelectionModel().getSelectedItem();

        if (reservationToDelete != null) {
            boolean confirmed = showConfirmationDialog();
            if (confirmed) {
                reservationService.delete(reservationToDelete);
                loadReservations();
            }
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réservation ?");
        alert.setContentText("Cette action est irréversible.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOK;
    }

    @FXML
    private void redirectToModiferReservation() throws IOException {
        Parent root3 = FXMLLoader.load(getClass().getResource("/Reservation/ModifierReservation.fxml"));
        Stage window = (Stage) ModifierButton.getScene().getWindow();
        window.setScene(new Scene(root3));

    }

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("Home.fxml"));
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

    public void event_btn(ActionEvent actionEvent) {
    }

    public void sponseur_btn(ActionEvent actionEvent) {
    }

    public void sujet_btn(ActionEvent actionEvent) {
    }

    public void etablissement_btn(ActionEvent actionEvent) {
    }

    public void medicament_btn(ActionEvent actionEvent) {
    }

}




