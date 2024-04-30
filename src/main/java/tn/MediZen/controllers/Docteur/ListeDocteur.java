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
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.MediZen.models.Docteur;
import tn.MediZen.services.DocteurService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListeDocteur implements Initializable {



    @FXML
    private Label welcomeid;

    @FXML
    private Button ModifierDocteur;

    @FXML
    private Button SupprimerDocteur;
    @FXML
    private ListView<Docteur> ListDocteurTT;

    private  DocteurService ds = new DocteurService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDocteurs();
    }
    private void loadDocteurs() {
        ObservableList<Docteur> docteurs = FXCollections.observableArrayList(ds.getAll());
        ListDocteurTT.setItems(docteurs);
    }


    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce docteur ?");
        alert.setContentText("Cette action est irréversible.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOK;
    }



    public void SupprimerDocteur(javafx.event.ActionEvent actionEvent) {
        Docteur docteurToDelete = ListDocteurTT.getSelectionModel().getSelectedItem();

        if (docteurToDelete != null) {
            boolean confirmed = showConfirmationDialog();
            if (confirmed) {
                ds.delete(docteurToDelete);
                loadDocteurs();
            }
        }
    }

    public void redirectToModiferDocteur(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root3 = FXMLLoader.load(getClass().getResource("/Docteur/ModifierDocteur.fxml"));
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(new Scene(root3));
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