package tn.MediZen.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListeReservation {
    private ObservableList<Reservation> allReservations = FXCollections.observableArrayList();
    private final ReservationService reservationService = new ReservationService();
    static final int ITEMS_PER_PAGE = 2;

    @FXML
    private Button AcceptedStatus;

    @FXML
    private Button AllStatus;

    @FXML
    private Button PendingStatus;

    @FXML
    private Button RejectedStatus;


    @FXML
    public AnchorPane logout;

    @FXML
    public Pagination paginationReservation;

    @FXML
    private Label welcomeid;

    @FXML
    private ListView<Reservation> ListReservationsT;

    @FXML
    private Button ModifierButton;

    @FXML
    private Button PDFExporter;

    @FXML
    public void initialize() {
        loadReservations();
        paginationReservation.setPageFactory(this::createPage);
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
                    Label doctorIdLabel = new Label("Docteur séléctionner: " + item.getDoctor_id());

                    VBox reservationInfoLayout = new VBox(nameLabel, surnameLabel, mobileLabel,
                            statusLabel, addressLabel, problemDescriptionLabel,
                            reservationDateLabel, doctorIdLabel);
                    reservationInfoLayout.setSpacing(5);

                    Button editButton = new Button("Modifier");
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

                    Button deleteButton = new Button("Supprimer");
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

    private Node createPage(Integer pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allReservations.size());
        ListReservationsT.setItems(FXCollections.observableArrayList(allReservations.subList(fromIndex, toIndex)));
        return ListReservationsT;
    }

    private void loadReservations() {
        allReservations = FXCollections.observableArrayList(reservationService.getAll());
        filterAndLoadReservations("All");
    }

    private void filterAndLoadReservations(String selectedStatus) {
        List<Reservation> filteredReservations;
        if (selectedStatus.equalsIgnoreCase("All")) {
            filteredReservations = allReservations;
        } else {
            filteredReservations = allReservations.stream()
                    .filter(reservation -> reservation.getStatus().equalsIgnoreCase(selectedStatus))
                    .collect(Collectors.toList());
        }
        populateListView(filteredReservations);
    }

    private void populateListView(List<Reservation> reservations) {
        int fromIndex = paginationReservation.getCurrentPageIndex() * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, reservations.size());
        ListReservationsT.setItems(FXCollections.observableArrayList(reservations.subList(fromIndex, toIndex)));
    }

    @FXML
    public void AllStatusFilter(ActionEvent actionEvent) {
        filterAndLoadReservations("All");
    }

    @FXML
    public void AcceptedStatusFilter(ActionEvent actionEvent) {
        filterAndLoadReservations("accepted");
    }

    @FXML
    public void RejectedStatusFilter(ActionEvent actionEvent) {
        filterAndLoadReservations("rejected");
    }

    @FXML
    public void PendingStatusFilter(ActionEvent actionEvent) {
        filterAndLoadReservations("pending");
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
        // Not implemented in the provided code snippet
    }

    public void event_btn(ActionEvent actionEvent) {
        // Not implemented in the provided code snippet
    }

    public void sponseur_btn(ActionEvent actionEvent) {
        // Not implemented in the provided code snippet
    }

    public void sujet_btn(ActionEvent actionEvent) {
        // Not implemented in the provided code snippet
    }

    public void etablissement_btn(ActionEvent actionEvent) {
        // Not implemented in the provided code snippet
    }

    public void medicament_btn(ActionEvent actionEvent) {

    }
    @FXML
    public void redirectToModifierReservation(ActionEvent actionEvent) throws IOException {
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
    }

