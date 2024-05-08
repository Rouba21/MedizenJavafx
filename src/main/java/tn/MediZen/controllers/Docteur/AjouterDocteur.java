package tn.MediZen.controllers.Docteur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.MediZen.models.Docteur;
import tn.MediZen.services.DocteurService;

import java.io.IOException;
import java.util.regex.Pattern;

public class AjouterDocteur {
    private final DocteurService docteurService = new DocteurService();
    @FXML
    private Button AddDocteur;
    @FXML
    private TextField AdresseDocteurTF;
    @FXML
    private TextField ExperienceDocteurTF;
    @FXML
    private Button ListeDocteur;
    @FXML
    private TextField MailDocteurTF;
    @FXML
    private TextField NomDocteurTF;
    @FXML
    private TextField NumeroTelephoneDocteur;
    @FXML
    private TextField PrenomDocteurTF;
    @FXML
    private TextField SpecialiteTF;
    @FXML
    private Label welcomeid;
    @FXML
    private Button logoutt;
    private final DocteurService rs = new DocteurService();

    public static void Alert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void docteur_btn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    @FXML
    void revervation_btn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Reservation/AjouterReservation.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    @FXML
    private void AjouterDocteur() {
        if (Saisi()) {
            rs.add(new Docteur(
                    NomDocteurTF.getText(),
                    PrenomDocteurTF.getText(),
                    MailDocteurTF.getText(),
                    AdresseDocteurTF.getText(),
                    SpecialiteTF.getText(),
                    ExperienceDocteurTF.getText(),
                    Integer.parseInt(NumeroTelephoneDocteur.getText()))
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Docteur inséré avec succès!");
            alert.show();
        } else {
            Alert(Alert.AlertType.ERROR, "Données invalides", "Selectionnez un médecin", "Veuillez sélectionner un médecin pour la réservation !");
        }
    }


    private void clearFields() {
        NomDocteurTF.clear();
        PrenomDocteurTF.clear();
        MailDocteurTF.clear();
        AdresseDocteurTF.clear();
        SpecialiteTF.clear();
        ExperienceDocteurTF.clear();
        NumeroTelephoneDocteur.clear();
    }

    @FXML
    void gotolistdocteur(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Docteur/ListDocteur.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean Saisi() {
        String errorMessage = "";

        if (NomDocteurTF.getText().isEmpty() || PrenomDocteurTF.getText().isEmpty() || NumeroTelephoneDocteur.getText().isEmpty() || ExperienceDocteurTF.getText().isEmpty() || AdresseDocteurTF.getText().isEmpty() || SpecialiteTF.getText().isEmpty()) {
            errorMessage += "Veuillez bien remplir tous les champs !\n";
        } else {
            if (!Pattern.matches("\\d{8}", NumeroTelephoneDocteur.getText())) {
                errorMessage += "Votre numéro de téléphone doit être composé de huit chiffres !\n";
            }

            if (!Pattern.matches("[A-Za-zÀ-ÿ\\s]+", NomDocteurTF.getText())) {
                errorMessage += "Vérifiez le nom !\n";
            }

            if (!Pattern.matches("[A-Za-zÀ-ÿ\\s]+", PrenomDocteurTF.getText())) {
                errorMessage += "Vérifiez le prénom !\n";
            }

            if (!Pattern.matches("[A-Za-z0-9\\s,.-]+", ExperienceDocteurTF.getText())) {
                errorMessage += "Vérifiez l'expérience du docteur !\n";
            }

            if (!Pattern.matches("\\d+\\s[A-Za-zÀ-ÿ,\\s]+", AdresseDocteurTF.getText())) {
                errorMessage += "Vérifiez l'adresse !\n";
            }

            if (!Pattern.matches("[A-Za-zÀ-ÿ\\s]+", SpecialiteTF.getText())) {
                errorMessage += "Vérifiez la spécialité !\n";
            }

            if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", MailDocteurTF.getText())) {
                errorMessage += "Vérifiez l'adresse e-mail !\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            Alert(Alert.AlertType.ERROR, "Données invalides", "Vérifiez les champs suivants :", errorMessage);
            return false;
        }

        return true;
    }


    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    @FXML
    void event_btn(ActionEvent event) {

    }


    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("Home.fxml"));
    }

    @FXML
    void medicament_btn(ActionEvent event) {

    }

    @FXML
    void sponseur_btn(ActionEvent event) {

    }

    @FXML
    void sujet_btn(ActionEvent event) {

    }

}
