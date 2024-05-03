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
            try {

                Docteur docteur = new Docteur();
                docteurService.add(docteur);
                clearFields();
                showAlert("Docteur ajouté avec succès !");
            } catch (NumberFormatException e) {
                showAlert("Veuillez saisir un numéro de téléphone valide.");
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        NomDocteurTF.clear();
        PrenomDocteurTF.clear();
        MailDocteurTF.clear();
        NumeroTelephoneDocteur.clear();
        AdresseDocteurTF.clear();
        SpecialiteTF.clear();
        ExperienceDocteurTF.clear();
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

        if (NomDocteurTF.getText().isEmpty() || PrenomDocteurTF.getText().isEmpty() || NumeroTelephoneDocteur.getText().isEmpty() || ExperienceDocteurTF.getText().isEmpty() || AdresseDocteurTF.getText().isEmpty() || SpecialiteTF.getText().isEmpty()) {
            Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !!", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("\\d{8}", NumeroTelephoneDocteur.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier !", "Votre Num doit etre composé de huit chiffres! ");
                return false;
            }

            if (!Pattern.matches("[A-Za-z]*", NomDocteurTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le nom ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", PrenomDocteurTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le prenom ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", ExperienceDocteurTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le descriiption du probléme ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z0-9\\s,.-]+", AdresseDocteurTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez l'adresse ! ");
                return false;
            }
            if (!Pattern.matches("[A-Za-z]*", SpecialiteTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez l'adresse ! ");
                return false;
            }


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
    void home_btn(ActionEvent event) {

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
