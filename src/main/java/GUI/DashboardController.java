package GUI;

import entites.Medicament;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import services.MedicamentService;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    @FXML
    private GridPane gridPane;

    private final MedicamentService medicamentService = new MedicamentService();

    public void initialize() {
        // Fetch database
        List<Medicament> medicaments = medicamentService.afficherMedicaments();

        // Populate the GridPane with medication information
        int col = 0;
        int row = 0;
        for (Medicament medicament : medicaments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
                Node itemNode = loader.load();

                // Get the controller for item.fxml
                ItemController itemController = loader.getController();

                // Set the medication in the ItemController
                itemController.setMedicament(medicament);

                // Add it to the gridPane
                gridPane.add(itemNode, col, row);

                // Update column index and row index
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
