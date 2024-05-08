package tn.esprit.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Docteur;
import tn.esprit.models.Reservation;
import tn.esprit.services.DocteurService;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ModifierReservation implements Initializable {
    @FXML
    private Label welcomeid;
    @FXML
    private TextField NomTF;

    @FXML
    private TextField PrenomTF;

    @FXML
    private TextField NumeroTelephone;

    @FXML
    private TextField DescriptionDeProblemeTF;

    @FXML
    private TextField AdresseTF;

    @FXML
    private ListView<Docteur> DocteurSelectedListView;
    @FXML
    private TextArea StatusTF;

    @FXML
    private DatePicker DateReservationF;


    @FXML
    private ListView<Reservation> ListReservationsM;
    private final ReservationService rs = new ReservationService();
    private final ReservationService reservationService = new ReservationService();
    private Reservation reservation;
    private final DocteurService docteurService = new DocteurService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsM.setItems(reservations);

        List<Docteur> doctors = docteurService.getAll();
        DocteurSelectedListView.getItems().addAll(doctors);

        // Handle selection of a reservation
        ListReservationsM.setOnMouseClicked((MouseEvent event) -> {
            Reservation current = ListReservationsM.getSelectionModel().getSelectedItem();
            NomTF.setText(current.getName());
            PrenomTF.setText(current.getSurname());
            NumeroTelephone.setText(String.valueOf(current.getMobile()));
            DescriptionDeProblemeTF.setText(current.getProblemDescription());
            AdresseTF.setText(current.getAddress());
            StatusTF.setText(current.getStatus());
        });
    }


    @FXML
    public void ModifierReservation() {
        if (Saisie()) {
            LocalDate localDate = DateReservationF.getValue();
            Docteur selectedDoctor = DocteurSelectedListView.getSelectionModel().getSelectedItem();
            if (selectedDoctor != null) {
                int doctorId = selectedDoctor.getId();

                Reservation selectedReservation = ListReservationsM.getSelectionModel().getSelectedItem();
                if (selectedReservation != null) {
                    selectedReservation.setSurname(PrenomTF.getText());
                    selectedReservation.setProblemDescription(DescriptionDeProblemeTF.getText());
                    selectedReservation.setMobile(Integer.parseInt(NumeroTelephone.getText()));
                    selectedReservation.setReservationDate(localDate);
                    selectedReservation.setName(NomTF.getText());
                    selectedReservation.setAddress(AdresseTF.getText());
                    selectedReservation.setStatus(StatusTF.getText());
                    selectedReservation.setDoctor_id(doctorId);
                    System.out.println("Selected Reservation: " + selectedReservation);
                    System.out.println("Selected Doctor: " + selectedDoctor);

                    rs.update(selectedReservation);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Réservation modifiée avec succès!");
                    alert.show();
                } else {
                    Alert(Alert.AlertType.ERROR, "Données invalides", "Sélectionnez une réservation", "Veuillez sélectionner une réservation à modifier !");
                }
            } else {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Sélectionnez un docteur", "Veuillez sélectionner un docteur pour cette réservation !");
            }
        }
    }




    @FXML
    void redirectionListeReservationn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Reservation/ListReservation.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Alert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            NomTF.setText(reservation.getName());
            PrenomTF.setText(reservation.getSurname());
            NumeroTelephone.setText(String.valueOf(reservation.getMobile()));
            DescriptionDeProblemeTF.setText(reservation.getProblemDescription());
            DateReservationF.setValue(reservation.getReservationDate());
            AdresseTF.setText(reservation.getAddress());
            StatusTF.setText(reservation.getStatus());
        }
    }

    private boolean Saisie() {
        if (NomTF.getText().isEmpty() || PrenomTF.getText().isEmpty() || AdresseTF.getText().isEmpty() || NumeroTelephone.getText().isEmpty() || DescriptionDeProblemeTF.getText().isEmpty()) {
            Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Veuillez bien remplir tous les champs !");
            return false;
        } else {
            if (!Pattern.matches("\\d{8}", NumeroTelephone.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Votre Num doit etre composé de huit chiffres! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", NomTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Vérifiez le nom ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", PrenomTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Vérifiez le prenom ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z\\s]*", DescriptionDeProblemeTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Vérifiez la description du problème !");
                return false;
            }

            if (!Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", AdresseTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Vérifiez votre email ! ");
                return false;
            }
        }
        return true;
    }

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("/Reservation/Home.fxml"));
    }
    @FXML
    void revervation_btn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Reservation/AjouterReservation.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    public void docteur_btn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    public void medicament_btn(ActionEvent actionEvent) {
    }


    public void event_btn(ActionEvent actionEvent) {
    }

    public void sponseur_btn(ActionEvent actionEvent) {
    }

    public void sujet_btn(ActionEvent actionEvent) {
    }

    public void etablissement_btn(ActionEvent actionEvent) {
    }


    }
