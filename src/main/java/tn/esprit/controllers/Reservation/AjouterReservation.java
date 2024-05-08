package tn.esprit.controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class AjouterReservation implements Initializable {

    public Button AddReservation;
    public ComboBox doctorComboBox;
    @FXML
    private TextField AdresseTF;

    @FXML
    private DatePicker DateReservationF;

    @FXML
    private TextField DescriptionDeProblemeTF;

    @FXML
    private Label welcomeid;

    @FXML
    private TextField NomTF;

    @FXML
    private TextField NumeroTelephone;

    @FXML
    private TextField PrenomTF;

    @FXML
    private ListView<Docteur> DocteurSelectedListView;

    @FXML
    private TextArea StatusTF;

    private Reservation reservation;

    private final ReservationService rs = new ReservationService();
    private final DocteurService docteurService = new DocteurService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Docteur> doctors = docteurService.getAll();
        DocteurSelectedListView.setCellFactory(param -> new ListCell<Docteur>() {
            @Override
            protected void updateItem(Docteur doctor, boolean empty) {
                super.updateItem(doctor, empty);
                if (empty || doctor == null) {
                    setText(null);
                } else {
                    setText(doctor.getNom() + " " + doctor.getPrenom());
                }
            }
        });
        DocteurSelectedListView.getItems().addAll(doctors);
    }

    @FXML
    void AjouterReservation() {
        if (Saisie()) {
            LocalDate localDate = DateReservationF.getValue();
            StatusTF.setText("pending");
            Docteur selectedDoctor = DocteurSelectedListView.getSelectionModel().getSelectedItem();
            if (selectedDoctor != null) {
                int doctorId = selectedDoctor.getId();
                rs.add(new Reservation(
                        Integer.parseInt(NumeroTelephone.getText()),
                        NomTF.getText(),
                        PrenomTF.getText(),
                        DescriptionDeProblemeTF.getText(),
                        AdresseTF.getText(),
                        StatusTF.getText(),
                        localDate,
                        doctorId)
                );
                String notificationMessage = " Réservation a été effectuée avec les détails suivants:" + NomTF.getText() + "Prénom:" + PrenomTF.getText() + "Date réservation:" + localDate;
                SMS.sendSMS("+21626653094", notificationMessage);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Réservation insérée avec succès!");
                alert.show();
            } else {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Selectionnez un médecin", "Veuillez sélectionner un médecin pour la réservation !");
            }
        }
    }

    @FXML
    private void redirectionListeReservation(ActionEvent event) {
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

    private boolean Saisie() {
        if (NomTF.getText().isEmpty() || PrenomTF.getText().isEmpty() || AdresseTF.getText().isEmpty() || NumeroTelephone.getText().isEmpty() || DescriptionDeProblemeTF.getText().isEmpty()) {
            Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !!", "Veuillez bien remplir tous les champs !");
            return false;
        } else {
            if (!Pattern.matches("\\d{8}", NumeroTelephone.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !!", "Votre Num doit etre composé de huit chiffres! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", NomTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le nom ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", PrenomTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le prenom ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z\\s]*", DescriptionDeProblemeTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez la description du problème !");
                return false;
            }

            if (!Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", AdresseTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez votre email ! ");
                return false;
            }
        }
        return true;
    }


    public void initialize() {
    }

    public void docteur_btn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
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