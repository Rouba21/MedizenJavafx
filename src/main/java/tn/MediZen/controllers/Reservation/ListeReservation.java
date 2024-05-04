package tn.MediZen.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;

import java.io.IOException;
import java.util.Optional;

public class ListeReservation {

    public AnchorPane logout;
    @FXML
    private Label welcomeid;
    @FXML
    private ListView<Reservation> ListReservationsT;
    @FXML
    private Button ModifierButton;
    private final ReservationService reservationService = new ReservationService();

    @FXML
    public void initialize() {
        ListReservationsT.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label("Name: " + item.getName());
                    Label surnameLabel = new Label("Surname: " + item.getSurname());
                    Label mobileLabel = new Label("Mobile: " + item.getMobile());
                    Label statusLabel = new Label("Status: " + item.getStatus());
                    Label addressLabel = new Label("Address: " + item.getAddress());
                    Label problemDescriptionLabel = new Label("Problem Description: " + item.getProblemDescription());
                    Label reservationDateLabel = new Label("Reservation Date: " + item.getReservationDate());
                    Label doctorIdLabel = new Label("Doctor ID: " + item.getDoctor_id());

                    VBox reservationInfoLayout = new VBox(nameLabel, surnameLabel, mobileLabel,
                            statusLabel, addressLabel, problemDescriptionLabel,
                            reservationDateLabel, doctorIdLabel);
                    reservationInfoLayout.setSpacing(5);

                    Button editButton = new Button("Edit");
                    editButton.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ModifierReservation.fxml"));
                            Parent root3 = loader.load();
                            ModifierReservation controller = loader.getController();
                            controller.setReservation(item);
                            Stage window = (Stage) ModifierButton.getScene().getWindow();
                            window.setScene(new Scene(root3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        boolean confirmed = showConfirmationDialog();
                        if (confirmed) {
                            reservationService.delete(item);
                            loadReservations();
                        }
                    });

                    HBox buttonsLayout = new HBox(editButton, deleteButton);
                    buttonsLayout.setSpacing(10);

                    VBox layout = new VBox(reservationInfoLayout, buttonsLayout);
                    layout.setSpacing(10);

                    setGraphic(layout);
                }
            }
        });

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
        Reservation selectedReservation = ListReservationsT.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ModifierReservation.fxml"));
            Parent root3 = loader.load();
            ModifierReservation controller = loader.getController();
            controller.setReservation(selectedReservation);
            Stage window = (Stage) ModifierButton.getScene().getWindow();
            window.setScene(new Scene(root3));
        } else {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à modifier.");
            alert.showAndWait();
        }
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

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("Home.fxml"));
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