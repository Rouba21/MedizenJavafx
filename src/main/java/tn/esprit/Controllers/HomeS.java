package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeS {
    @FXML
    private void navigateToModifierSponseur() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifierSponseur.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Sponseur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
