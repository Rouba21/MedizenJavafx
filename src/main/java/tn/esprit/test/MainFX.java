package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));

            // Créer une scène
            Scene scene = new Scene(root);

            // Charger le fichier CSS
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            // Configurer la scène et afficher la fenêtre
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ajouter Sponseur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Afficher l'erreur dans la console
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
