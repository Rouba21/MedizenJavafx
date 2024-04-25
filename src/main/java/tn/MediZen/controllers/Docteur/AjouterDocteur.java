package tn.MediZen.controllers.Docteur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.MediZen.models.Docteur;
import tn.MediZen.services.DocteurService;

import java.io.IOException;
import java.util.regex.Pattern;

public class AjouterDocteur {
    @FXML
    private TextField NomDocteurTF;
    @FXML
    private TextField PrenomDocteurTF;
    @FXML
    private TextField NumeroTelephoneDocteur;
    @FXML
    private TextField ExperienceDocteurTF;
    @FXML
    private TextField AdresseDocteurTF;
    @FXML
    private TextField SpecialiteTF;
    @FXML
    private TextField MailDocteurTF;
    @FXML
    private Button AddDocteur;

    private final DocteurService docteurService = new DocteurService();

    @FXML
    private void AjouterDocteur() {
        String nom = NomDocteurTF.getText();
        String prenom = PrenomDocteurTF.getText();
        String mail = MailDocteurTF.getText();
        String adresse = AdresseDocteurTF.getText();
        String specialite = SpecialiteTF.getText();
        String experience = ExperienceDocteurTF.getText();

        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || adresse.isEmpty() || specialite.isEmpty() || experience.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
        } else {
            try {
                int mobile = Integer.parseInt(NumeroTelephoneDocteur.getText());
                Docteur docteur = new Docteur(nom, prenom, mail, experience, mobile, adresse, specialite);
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
    public static void Alert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

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
}
