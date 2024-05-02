package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Sponseur;
import tn.esprit.services.SponseurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierSponseur {

    @FXML
    private TextField NomTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField NumeroTF;

    @FXML
    private TextField PackTF;

    @FXML
    private ListView<Sponseur> liste_sponsor;

    private final SponseurService sponseurService = new SponseurService();

    private Sponseur selectedSponseur;

    public void initData(Sponseur sponseur) {
        this.selectedSponseur = sponseur;
        if (selectedSponseur != null) {
            NomTF.setText(selectedSponseur.getNom());
            emailTF.setText(selectedSponseur.getEmail());
            NumeroTF.setText(String.valueOf(selectedSponseur.getNumero()));
            PackTF.setText(selectedSponseur.getPack());
        }
    }


    @FXML
    void updateSponseur(ActionEvent event) {
        if (selectedSponseur == null) {
            showErrorAlert("Veuillez sélectionner un sponsor à mettre à jour.");
            return;
        }

        // Vérification du numéro (doit être un nombre à 8 chiffres)
        String numeroStr = NumeroTF.getText().trim();
        if (!isValidNumero(numeroStr)) {
            showErrorAlert("Le numéro doit être un nombre à 8 chiffres.");
            return;
        }

        // Vérification du nom (doit contenir uniquement des lettres)
        String nom = NomTF.getText().trim();
        if (!isValidName(nom)) {
            showErrorAlert("Le nom ne doit contenir que des lettres.");
            return;
        }

        // Vérification du pack (doit contenir uniquement des lettres)
        String pack = PackTF.getText().trim();
        if (!isValidName(pack)) {
            showErrorAlert("Le pack ne doit contenir que des lettres.");
            return;
        }

        // Vérification de l'adresse email
        String email = emailTF.getText().trim();
        if (!isValidEmail(email)) {
            showErrorAlert("Veuillez saisir une adresse email valide.");
            return;
        }



        // Mettre à jour les champs du sponsor sélectionné
        selectedSponseur.setNom(nom);
        selectedSponseur.setEmail(email);
        selectedSponseur.setNumero(Integer.parseInt(numeroStr));
        selectedSponseur.setPack(pack);

        // Confirmer la mise à jour avec une boîte de dialogue
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir mettre à jour ce sponsor ?");

        Optional<ButtonType> result = alert.showAndWait(); // Attendre la réponse de l'utilisateur

        // Vérifier la réponse de l'utilisateur
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                sponseurService.update(selectedSponseur);
                showSuccessAlert("Sponsor mis à jour avec succès !");

                // Redirection vers AfficherSponseur.fxml après la mise à jour
                redirectToAfficherSponseur();
            } catch (SQLException e) {
                showErrorAlert("Erreur lors de la mise à jour du sponsor : " + e.getMessage());
            }
        }
    }

    // Méthode pour valider un numéro à 8 chiffres
    private boolean isValidNumero(String numero) {
        return numero.matches("\\d{8}");
    }

    // Méthode pour valider un nom ou un pack (contient uniquement des lettres)
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    // Méthode pour valider une adresse email
    private boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière pour valider l'adresse email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void redirectToAfficherSponseur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSponseur.fxml"));
            Parent root = loader.load();
            AfficheController controller = loader.getController();
            controller.refreshSponseurs(); // Actualiser la liste des sponsors

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) NomTF.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showErrorAlert("Erreur lors du chargement de AfficherSponseur.fxml : " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    void revervation_btn(ActionEvent event) {

    }
    @FXML
    void sponseur_btn(ActionEvent event) {

    }

    @FXML
    void sujet_btn(ActionEvent event) {

    }
    @FXML
    void AvailableFlowersInsertImage(ActionEvent event) {

    }

    @FXML
    void Supprimer(ActionEvent event) {

    }



}
