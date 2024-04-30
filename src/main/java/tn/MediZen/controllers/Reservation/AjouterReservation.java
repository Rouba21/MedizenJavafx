package tn.MediZen.controllers.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.MediZen.models.Reservation;
import tn.MediZen.services.ReservationService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


public class AjouterReservation implements Initializable {

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
    private TextArea StatusTF;

    private final ReservationService rs = new ReservationService();

    @FXML
    void AjouterReservation(ActionEvent event) {
        if (Saisie() == true) {
            LocalDate localDate = DateReservationF.getValue();
            StatusTF.setText("pending");
            rs.add(new Reservation(
                    NomTF.getText(),
                    PrenomTF.getText(),
                    DescriptionDeProblemeTF.getText(),
                    Integer.parseInt(NumeroTelephone.getText()),
                    localDate,
                    StatusTF.getText(),
                    AdresseTF.getText())
             );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Réservation insérée avec succès!");
            alert.show();
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
            if (!Pattern.matches("[A-Za-z]*", DescriptionDeProblemeTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez le descriiption du probléme ! ");
                return false;
            }

            if (!Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", AdresseTF.getText())) {
                Alert(Alert.AlertType.ERROR, "Données invalides", "Verifier ", "Vérifiez votre email ! ");
                return false;
            }


        }
        return true;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
