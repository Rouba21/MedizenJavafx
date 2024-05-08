package tn.esprit.controllers;

import tn.esprit.models.Etablissement;
import tn.esprit.services.etablissementservice;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.List;

public class AfficherEtablissement implements Initializable {

    private etablissementservice SP= new etablissementservice();
    private List<VBox> displayedCards = new ArrayList<>();
    @FXML
    private TextField searchField;

    @FXML
    private FlowPane cardLayout2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Etablissement> etablissements = null;
        try {
            etablissements =SP.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        display(etablissements);

    }
    @FXML
    void searchetablissement() throws SQLException {
        // Récupérer le mot-clé de recherche saisi par l'utilisateur
        String keyword = searchField.getText().toLowerCase().trim();

        // Récupérer la liste complète des produits depuis le service ServiceProduit
        List<Etablissement> etablissements = SP.afficher();

        // Appliquer le filtre en fonction du mot-clé de recherche
        FilteredList<Etablissement> filteredEtablissements = new FilteredList<>(FXCollections.observableArrayList(etablissements));
        filteredEtablissements.setPredicate(etablissement -> etablissementContainsKeyword(etablissement, keyword));

        // Mettre à jour l'affichage avec les cartes filtrées
        updateDisplay(filteredEtablissements);
    }

    private void updateDisplay(List<Etablissement> etablissements) {
        // Effacer les cartes existantes
        cardLayout2.getChildren().clear();

        // Afficher les nouvelles cartes
        for (Etablissement etablissement : etablissements) {
            VBox card = createEtablissement(etablissement);
            cardLayout2.getChildren().add(card);
            displayedCards.add(card);
        }
    }

    private boolean etablissementContainsKeyword(Etablissement etablissement, String keyword) {
        String id = String.valueOf(etablissement.getId());
        return id.contains(keyword) ||
                etablissement.getName().toLowerCase().contains(keyword) ||
                etablissement.getTypee().toLowerCase().contains(keyword) ||
                etablissement.getLocation().toLowerCase().contains(keyword) ||
                etablissement.getDescription().toLowerCase().contains(keyword) ||
                String.valueOf(etablissement.getLongitude()).contains(keyword) ||
                String.valueOf(etablissement.getLatitude()).contains(keyword);
    }

    @FXML
    void AjouterEtablissement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/AddEtablissement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void RefreshPage(ActionEvent event) {
        List<Etablissement> etablissements = null;
        try {
            etablissements = SP.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cardLayout2.getChildren().clear();
        display(etablissements);

    }

   /* @FXML
    void goToReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }*/


    private void display(List<Etablissement> etablissements) {
        for (Etablissement etablissement : etablissements) {
            VBox card = createEtablissement(etablissement);
            cardLayout2.getChildren().add(card);
        }
    }


    private void openDetailsPage(Etablissement etablissement) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/Etablissementcard.fxml"));
            Parent root = fxmlLoader.load();
            CardControllerEtab controller = fxmlLoader.getController();
            if (controller != null) {
                controller.setid(etablissement.getId());
                controller.setControllerEtab(etablissement);
                controller.displayEtablissementDetails(etablissement);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("EtablissementDetails");
                stage.show();
            } else {
                System.out.println("Erreur : Contrôleur null pour la page de détails ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox createEtablissement(Etablissement etablissement) {
        VBox card = new VBox();
        card.getStyleClass().add("commande-card");
        Label id = new Label("id:" + etablissement.getId());
        Label type = new Label("Type:" + etablissement.getTypee());
        Label name = new Label("Name:" + etablissement.getName());
        Label location = new Label("Location:" + etablissement.getLocation());
        Label description = new Label("Description:" + etablissement.getDescription());
        Label latitude = new Label("Latitude:" + etablissement.getLatitude());
        Label longitude = new Label("Longitude:" + etablissement.getLongitude());

        card.getChildren().addAll(id, type, name, location, description, latitude, longitude);

        card.setOnMouseClicked(event -> openDetailsPage(etablissement));
        card.setCursor(Cursor.HAND);
        return card;
    }




}