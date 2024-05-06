package GUI;

import entites.Commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.CommandeService;

import java.util.List;

public class CommandeInterfaceController {

    @FXML
    private TableView<Commande> commandeTableView;

    @FXML
    private TableColumn<Commande, Integer> idColumn;

    @FXML
    private TableColumn<Commande, Double> totalPriceColumn;

    @FXML
    private TableColumn<Commande, Integer> quantityOrderedColumn;

    @FXML
    private TableColumn<Commande, String> dateOrderedColumn;

    @FXML
    private TableColumn<Commande, String> statusColumn;

    @FXML
    private Button acceptButton;

    private final CommandeService commandeService = new CommandeService();

    public void initialize() {
        initializeTableView();
        loadCommandes();
    }

    private void initializeTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        dateOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("dateOrdered"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadCommandes() {
        List<Commande> allCommandesList = commandeService.afficherCommandes();
        ObservableList<Commande> allCommandesObservableList = FXCollections.observableList(allCommandesList);
        commandeTableView.setItems(allCommandesObservableList);
    }

    @FXML
    private void downloadPDF() {
        // Get all commandes from the TableView
        List<Commande> allCommandesList = commandeTableView.getItems();

        // Specify the file path
        String filePath = "C:/Users/21695/Downloads/commandes.pdf";

        // Generate PDF with the list of commandes
        commandeService.generateCommandesPDF(allCommandesList, filePath);
    }
    @FXML
    private void acceptCommande() {
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            selectedCommande.setStatus("Accepted");
            boolean updated = commandeService.modifierCommande(selectedCommande);
            if (updated) {
                commandeTableView.refresh(); // Refresh TableView
            }
        }
    }
}
