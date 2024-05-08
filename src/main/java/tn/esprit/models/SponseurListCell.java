package tn.esprit.models;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.Controllers.*;
import tn.esprit.services.SponseurService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;

public class SponseurListCell extends ListCell<Sponseur> {


    private final ImageView imageView = new ImageView();
    private final Text nomText = new Text();
    private final Text emailText = new Text();
    private final Text numeroText = new Text();
    private final Text packText = new Text();

    private static final int IMAGE_WIDTH = 30; // Nouvelle largeur de l'image
    private static final int IMAGE_HEIGHT = 30; // Nouvelle hauteur de l'image

    private final Button editButton = createStyledIconButton("/img/1.png", "-fx-background-color: #fff;", 40, 40);
    private final Button deleteButton = createStyledIconButton("/img/delete.jpg", "-fx-background-color: #fff;", 40, 40);
    private final Button detailButton = createStyledIconButton("/img/detail.png", "-fx-background-color: #fff;", 40, 40);

    public SponseurListCell() {


        editButton.setOnAction(event -> {
            Sponseur sponseur = getItem();
            if (sponseur != null) {
                navigateToModifierSponseur(sponseur);
            }
        });

        deleteButton.setOnAction(event -> {
            Sponseur sponseur = getItem();
            if (sponseur != null) {
                deleteSponseur(sponseur);
            }
        });

        detailButton.setOnAction(event -> {
            Sponseur sponseur = getItem();
            if (sponseur != null) {
                afficherDetailsSponseur(sponseur);
            }
        });
    }

    @Override
    protected void updateItem(Sponseur sponseur, boolean empty) {
        super.updateItem(sponseur, empty);

        if (empty || sponseur == null) {
            setGraphic(null);
        } else {
            // Charger l'image du logo du sponsor
            String logoPath = sponseur.getLogo();
            if (logoPath != null && !logoPath.isEmpty()) {
                Image image = new Image("file:" + logoPath);
                imageView.setImage(image);
                imageView.setFitWidth(IMAGE_WIDTH); // Définir la largeur de l'image
                imageView.setFitHeight(IMAGE_HEIGHT); // Définir la hauteur de l'image
            }

            // Afficher les données dans un HBox
            nomText.setText("Nom: " + sponseur.getNom());
            emailText.setText("Email: " + sponseur.getEmail());
            numeroText.setText("Numéro: " + sponseur.getNumero());
            packText.setText("Pack: " + sponseur.getPack());

            // Ajouter les éléments à un HBox
            HBox content = new HBox(10, imageView, nomText, emailText, numeroText, packText, editButton, deleteButton, detailButton);
            content.setPadding(new Insets(5));

            // Appliquer les styles CSS aux éléments de la liste
            content.getStyleClass().add("list-cell");
            nomText.getStyleClass().add("text");
            emailText.getStyleClass().add("text");
            numeroText.getStyleClass().add("text");
            packText.getStyleClass().add("text");
            editButton.getStyleClass().add("button");
            deleteButton.getStyleClass().add("button");
            detailButton.getStyleClass().add("button");

            setGraphic(content);
        }
    }

    private Button createStyledIconButton(String iconFilename, String style, double width, double height) {
        Button button = new Button();
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setStyle("-fx-font-size: 12px; " + style);

        // Chargement de l'icône depuis le fichier
        InputStream inputStream = getClass().getResourceAsStream(iconFilename);
        if (inputStream != null) {
            Image iconImage = new Image(inputStream);
            ImageView iconView = new ImageView(iconImage);
            iconView.setFitWidth(20); // Taille de l'icône
            iconView.setFitHeight(20);

            button.setGraphic(iconView); // Définir l'icône comme graphique du bouton
        } else {
            System.err.println("Erreur : Impossible de charger l'image " + iconFilename);
        }

        return button;
    }

    private void navigateToModifierSponseur(Sponseur sponseur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSponseur.fxml"));
            Parent root = loader.load();

            // Passer le sponseur à l'interface ModifierSponseurController
            ModifierSponseur controller = loader.getController();
            controller.initData(sponseur);

            // Créer une nouvelle scène et configurer la fenêtre
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Sponseur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSponseur(Sponseur sponseur) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce sponseur ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Utiliser le service pour supprimer le sponseur
                SponseurService sponseurService = new SponseurService();
                sponseurService.delete(sponseur.getId());

                // Remove the item from the modifiable list

            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'erreur
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSponseur.fxml"));
            Parent root = loader.load();
            AfficheController controller = loader.getController();
            controller.refreshSponseurs(); // Actualiser la liste des sponsors

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) nomText.getScene().getWindow();
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



    private void afficherDetailsSponseur(Sponseur sponseur) {
        // Implémentez votre logique pour afficher les détails du sponsor
        // Par exemple, afficher les détails dans une nouvelle interface utilisateur
        // ou une boîte de dialogue
        // Ici, vous pouvez utiliser une nouvelle vue (FXML) pour afficher les détails du sponsor.
        // Chargez la vue et affichez-la avec les données du sponsor.

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailSponseur.fxml"));
            Parent root = loader.load();

            // Passer le sponseur à l'interface DetailSponseurController
            DetailSponseurController controller = loader.getController();
            controller.initData(sponseur);

            // Créer une nouvelle scène et configurer la fenêtre
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Détails du Sponseur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
