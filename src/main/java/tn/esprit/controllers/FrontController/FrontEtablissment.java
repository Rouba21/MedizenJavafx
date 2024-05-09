package tn.esprit.controllers.FrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.PieChart;

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
    @FXML
    private PieChart PieChart;

    private final etablissementservice sa = new etablissementservice();

// bel chat badel hethii function bech twali ta9raelk mel base fazet ou tafichihom
    public List<Integer> countTasksByStatus(List<Etablissement> tasksList) {
        List<Integer> taskCounts = new ArrayList<>(3); // Initialize with three elements for each status

        int bizerte = 0;
        int Tunis = 0;
        int Djerba = 0;

        for (Etablissement task : tasksList) {
            String status = task.getLocation();
            if ("bizerte".equals(status)) {
                bizerte++;
            } else if ("Tunis".equals(status)) {
                Tunis++;
            } else if ("Djerba".equals(status)) {
                Djerba++;
            }
        }

        taskCounts.add(bizerte);
        taskCounts.add(Tunis);
        taskCounts.add(Djerba);

        return taskCounts;
    }
    @FXML
    void initialize() {
        try {
            actualise();
            updatePieChartData();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/cardEtablissment.fxml"));
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/cardEtablissment.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/Etablissement/map.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("OpenStreetMap");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePieChartData() {
        try {
            // Step 1: Retrieve the list of tasks
            List<Etablissement> tasksList = sa.afficher();

            // Step 2: Get the counts for tasks in different statuses
            List<Integer> taskCounts = countTasksByStatus(tasksList);
            // Calculate total tasks count
            int totalTasks = taskCounts.stream().mapToInt(Integer::intValue).sum(); // Declare and initialize totalTasks
            // Step 3: Update PieChart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Djerba", taskCounts.get(2)), // Index 2 for Todo count
                    new PieChart.Data("Tunis", taskCounts.get(1)), // Index 1 for In Progress count
                    new PieChart.Data("bizerte", taskCounts.get(0)) // Index 0 for Complete count
            );

            PieChart.setData(pieChartData);
            PieChart.setStartAngle(90);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

}

