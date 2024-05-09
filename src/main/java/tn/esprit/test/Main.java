package tn.esprit.test;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {


      // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/tn/esprit/Etablissement/AfficherEtablissement.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/tn/esprit/Etablissement/AfficherDepartement.fxml"));
       //  FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/tn/esprit/Etablissement/FrontEtablissment.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Etablissement");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();



    }
}