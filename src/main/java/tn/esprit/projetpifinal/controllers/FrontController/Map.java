package tn.esprit.projetpifinal.controllers.FrontController;



import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;
import netscape.javascript.JSObject;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;

import java.sql.SQLException;
import java.util.List;

public class Map {

    @FXML
    private WebView webView;

    private final etablissementservice etablissementservice;

    public Map() {
        this.etablissementservice = new etablissementservice();
    }

    public void initialize() {
        List<Etablissement> offresList;
        try {
            offresList = etablissementservice.afficher();
            displayOffresOnMap(offresList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private void displayOffresOnMap(List<Etablissement> offresList) {
        // Convert the list of Offre objects to a JSON string
        String offresJson = convertOffresListToJson(offresList);
        System.out.println("Offres JSON: " + offresJson); // Print out the JSON data
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/html/map.html").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaBridge", new JavaBridge());

                // Pass the JSON data to the JavaScript function
                webEngine.executeScript("addMarkers(" + offresJson + ")");
                //man
                // webEngine.executeScript("addMarker(36.806650, 10.181500, 'Decorateur')");

            }
        });
    }


    // Convert the list of Offre objects to a JSON string
    private String convertOffresListToJson(List<Etablissement> offresList) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < offresList.size(); i++) {
            Etablissement offre = offresList.get(i);
            jsonBuilder.append("{")
                    .append("\"lat\":").append(offre.getLatitude()).append(",")
                    .append("\"lng\":").append(offre.getLongitude()).append(",")
                    .append("\"title\":\'").append(offre.getName()).append("\',")
                    .append("\"salary\":").append(offre.getId())
                    .append("}");
            if (i < offresList.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    public class JavaBridge {
        public void showAlert(String message) {
            System.out.println("From JavaScript: " + message);
        }
    }
}