package tn.esprit.projetpifinal;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;
import tn.esprit.projetpifinal.utils.Connectiondb;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/AfficherEtablissement.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Etablissement");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
       launch();



    }
}
