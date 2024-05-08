package tn.esprit.controllers.FrontController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.Etablissement;
import tn.esprit.services.etablissementservice;

public class FrontEtablissment {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchField;
    @FXML
    private Button btnMaps;

    private final etablissementservice sa = new etablissementservice();

    @FXML
    void initialize() {
        try {
            actualise();
            // Ajouter un écouteur d'événements au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue.isEmpty()) {
                        // Si le champ de recherche est vide, afficher tous les articles
                        actualise();
                    } else {
                        // Sinon, actualiser la liste des articles en fonction du terme de recherche
                        actualiseWithSearch(newValue);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualise() throws SQLException {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        List<Etablissement> etablissementList = sa.afficher();
        if (etablissementList.isEmpty()) {
            System.out.println("La liste des articles est vide.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setHgap(50);

        for (Etablissement etablissement : etablissementList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/Etabblissement/cardEtablissment.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                FrontEtablissmentCard c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(etablissement);



                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualiseWithSearch(String searchTerm) throws SQLException {
        grid.getChildren().clear();

        List<Etablissement> etablissementList = sa.rechercherOffres(searchTerm);
        if (etablissementList.isEmpty()) {
            System.out.println("Aucun offre trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 3;
        grid.setHgap(50);

        for (Etablissement etablissement : etablissementList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/Etabblissement/cardEtablissment.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                FrontEtablissmentCard c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(etablissement);



                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void showMap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/Etabblissement/map.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("OpenStreetMap");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

