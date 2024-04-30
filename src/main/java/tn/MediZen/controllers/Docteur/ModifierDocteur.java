package tn.MediZen.controllers.Docteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.MediZen.models.Docteur;
import tn.MediZen.services.DocteurService;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ModifierDocteur implements Initializable {

    @FXML
    private Label welcomeid;
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
    private ListView<Docteur> ListDocteursM;

    private final DocteurService docteurService = new DocteurService();


    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Docteur> docteurs = FXCollections.observableArrayList(docteurService.getAll());
        ListDocteursM.setItems(docteurs);
        ListDocteursM.setOnMouseClicked((MouseEvent event) -> {
            Docteur current = ListDocteursM.getSelectionModel().getSelectedItem();
            if (current != null) {
                NomDocteurTF.setText(current.getNom());
                PrenomDocteurTF.setText(current.getPrenom());
                NumeroTelephoneDocteur.setText(String.valueOf(current.getMobile()));
                ExperienceDocteurTF.setText(current.getExperience());
                AdresseDocteurTF.setText(current.getAddresse());
                SpecialiteTF.setText(current.getSpecialite());
            }
        });
    }

    public void redictionListeDocteurs(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Docteur/ListDocteur.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ModifierDocteur() {
        if (Saisi()) {
            Docteur current = ListDocteursM.getSelectionModel().getSelectedItem();
            Docteur docteur = new Docteur();
            docteur.setId(current.getId());
            docteur.setNom(NomDocteurTF.getText());
            docteur.setPrenom(PrenomDocteurTF.getText());
            docteur.setMobile(Integer.parseInt(NumeroTelephoneDocteur.getText()));
            docteur.setMail(MailDocteurTF.getText());
            docteur.setExperience(ExperienceDocteurTF.getText());
            docteur.setExperience(SpecialiteTF.getText());
            docteurService.update(docteur);
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

    public void sujet_btn(ActionEvent actionEvent) {
    }

    public void etablissement_btn(ActionEvent actionEvent) {
    }

    public void revervation_btn(ActionEvent actionEvent) {
    }

    public void medicament_btn(ActionEvent actionEvent) {
    }

    public void docteur_btn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    public void home_btn(ActionEvent actionEvent) {
    }

    public void event_btn(ActionEvent actionEvent) {
    }

    public void sponseur_btn(ActionEvent actionEvent) {
    }
}
