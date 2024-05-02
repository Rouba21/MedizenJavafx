package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.esprit.models.Pack;
import tn.esprit.models.Sponseur;
import tn.esprit.models.getData;
import tn.esprit.services.PackService;
import tn.esprit.services.SponseurService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SponseurController {

    @FXML
    private TextField LogoTF;

    @FXML
    private TextField NomTF;

    @FXML
    private TextField NumeroTF;

    @FXML
    private TextField PackTF;

    @FXML
    private TextField emailTF;

    @FXML
    private AnchorPane main_form;

    @FXML
    private ImageView availableFlowers_imageView;

    private Image image;

    @FXML
    private ComboBox<Pack> packComboBox;

    private final PackService packService = new PackService();

    private final SponseurService sponseurService=new SponseurService();





    @FXML
    private void initialize() {
        loadPacksForSponseur(2); // Exemple : passer l'ID du sponsor ici
    }

    private void loadPacksForSponseur(int sponseurId) {
        try {
            List<Pack> packs = packService.getAllPacksForSponseur(sponseurId);

            // Créer une liste observable à partir de la liste de packs
            ObservableList<Pack> observablePacks = FXCollections.observableArrayList(packs);

            // Mettre à jour la ComboBox avec les packs
            packComboBox.setItems(observablePacks);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur lors du chargement des packs liés à un sponsor spécifique
            showAlert("Erreur", "Erreur lors du chargement des packs liés au sponsor : " + e.getMessage());
        }
    }



    @FXML
    void AjouterSponseur(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (NomTF.getText().isEmpty() ||
                emailTF.getText().isEmpty() ||
                NumeroTF.getText().isEmpty() ||
                PackTF.getText().isEmpty() ||
                getData.path == null || getData.path.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        // Valider le champ NomTF (lettres et espaces seulement)
        if (!isValidName(NomTF.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir uniquement des lettres dans le champ Nom.");
            alert.showAndWait();
            return;
        }

        // Valider le champ PackTF (lettres et espaces seulement)
        if (!isValidName(PackTF.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir uniquement des lettres dans le champ Pack.");
            alert.showAndWait();
            return;
        }

        // Valider le champ emailTF (adresse email valide)
        if (!isValidEmail(emailTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse email valide.");
            alert.showAndWait();
            return;
        }

        // Valider le champ NumeroTF (8 chiffres)
        if (!isValidPhoneNumber(NumeroTF.getText().trim())) {
            showAlert("Erreur", "Veuillez saisir un numéro de téléphone valide (8 chiffres).");
            return;
        }

        // Ajouter le sponseur après validation
        try {
            sponseurService.add(new Sponseur(
                    NomTF.getText().trim(),
                    emailTF.getText(),
                    Integer.parseInt(NumeroTF.getText()),
                    getData.path,
                    PackTF.getText().trim()
            ));

            // Afficher une confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sponseur ajouté");
            alert.setHeaderText(null);
            alert.setContentText("Le sponseur a été ajouté avec succès !");
            alert.showAndWait();




            NomTF.clear();
            emailTF.clear();
            NumeroTF.clear();
            PackTF.clear();
            getData.path = null;

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du sponseur : " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }



    @FXML
    void naviguer(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/AfficherSponseur.fxml"));
            NomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isValidName(String input) {
        return Pattern.matches("[a-zA-Z ]+", input);
    }


    private boolean isValidEmail(String input) {
        return Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", input);
    }


    private boolean isValidNumber(String input) {
        return Pattern.matches("[0-9]+", input);
    }


    @FXML
    void event_btn(ActionEvent event) {
        try {
            // Navigation vers une autre vue (AfficherSponseur.fxml)
            // Assurez-vous d'ajuster le chemin d'accès si nécessaire
            Parent root = FXMLLoader.load(getClass().getResource("/Event.fxml"));
            NomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void home_btn(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            NomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void sponseur_btn(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/Sponseur.fxml"));
            NomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void AvailableFlowersInsertImage(){
        FileChooser open =new FileChooser();
        open.setTitle("open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg" ,"*png"));
        File file=open.showOpenDialog(main_form.getScene().getWindow());
        if(file !=null){
            getData.path =file.getAbsolutePath();
            image = new Image(file.toURI().toString(),123,117,false,true);
            availableFlowers_imageView.setImage(image);
        }
    }

    @FXML
    void medicament_btn(ActionEvent event) {

    }



    @FXML
    void revervation_btn(ActionEvent event) {

    }



    @FXML
    void sujet_btn(ActionEvent event) {

    }

    @FXML
    private Label noww;

    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    private boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber != null && phoneNumber.matches("[0-9]{8}");
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}