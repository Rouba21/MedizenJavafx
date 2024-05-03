package tn.MediZen.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;
import tn.MediZen.models.Docteur;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    private ListView<?> DocteurSelectedListView;
    @FXML
    private TextArea StatusTF;

    @FXML
    private DatePicker DateReservationF;


    @FXML
    private ListView<Reservation> ListReservationsM;
    private final ReservationService rs = new ReservationService();
    private final ReservationService reservationService = new ReservationService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAll());
        ListReservationsM.setItems(reservations);
        ListReservationsM.setOnMouseClicked((MouseEvent event)->{

            Reservation current = ListReservationsM.getSelectionModel().getSelectedItem();
            current.getId();
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
        if (Saisie() == true) {
            LocalDate localDate = DateReservationF.getValue();
            Reservation current = ListReservationsM.getSelectionModel().getSelectedItem();
            Reservation p = new Reservation(Integer.parseInt(NumeroTelephone.getText()),NomTF.getText(), PrenomTF.getText(), DescriptionDeProblemeTF.getText(), AdresseTF.getText(), StatusTF.getText(), localDate,(Docteur) DocteurSelectedListView.getSelectionModel().getSelectedItem()
            );
            p.setId(current.getId());
            p.setSurname(PrenomTF.getText());
            p.setProblemDescription(DescriptionDeProblemeTF.getText());
            p.setMobile(Integer.parseInt(NumeroTelephone.getText()));
            p.setReservationDate(localDate);
            p.setName(NomTF.getText());
            p.setAddress(AdresseTF.getText());
            rs.update(p);
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
            if (!Pattern.matches("[A-Za-z]*", DescriptionDeProblemeTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le descriiption du probléme ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", AdresseTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez l'adresse ! ");
                return false;
            }


        }
        return true;

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

    @FXML
    void revervation_btn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Reservation/AjouterReservation.fxml"));
        welcomeid.getScene().setRoot(root);
    }


    public void medicament_btn(ActionEvent actionEvent) {
    }

    public void docteur_btn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }
}