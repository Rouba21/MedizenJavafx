package Medizen.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Medizen.Models.User;
import Medizen.Services.UserService;
import Medizen.Utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DisplayUser {
    public Button canb;
    public Button logout;
    @FXML
    private TableColumn<User, Integer> cnum;

    @FXML
    private TableColumn<User, Integer> cid;
    @FXML
    public TableColumn<User, String>cmp;
    @FXML
    private TableColumn<User, String> cnom;
    @FXML
    private TableColumn<User, String> cprenom;
    @FXML
    private TableColumn<User, String> cemail;
    @FXML
    private TableColumn<User, String> crole;

    @FXML
    private TableView<User> tableview;


    private final UserService us =new UserService();
    @FXML
    private Button deleteButton;
    private User selectUser;
    @FXML
    public void initialize()  {


        try{
            cnom.getStyleClass().add("custom-label");

            tableview.getStylesheets().add("/com/example/at_one/style1.css");
            List<User> users = us.Readall();
            ObservableList<User> observableList= FXCollections.observableList (users);
            tableview.setItems(observableList);
            cid.setCellValueFactory(new PropertyValueFactory<>("id"));
            cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            cemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            crole.setCellValueFactory(new PropertyValueFactory<>("role"));
            cmp.setCellValueFactory(new PropertyValueFactory<>("motDePasse"));
            cnum.setCellValueFactory(new PropertyValueFactory<>("numTel"));
            tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    deleteButton.setDisable(false);
                    selectUser=(User)newValue;
                    // Enable the delete button when an item is selected
                } else {
                    deleteButton.setDisable(true); // Disable the delete button if no item is selected
                }
            });
        }
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    public void deleteUser(ActionEvent actionEvent) {
        try {
            // Assuming `selectUser` is already a User object properly initialized elsewhere in your code.
            if (selectUser != null) {
                us.delete(selectUser);
                List<User> users = us.Readall();
                ObservableList<User> observableList = FXCollections.observableList(users);
                tableview.setItems(observableList);
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("No user selected for deletion.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void GoToCodePormo(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/CreateUser.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) this.tableview.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelButtonAction(ActionEvent actionEvent) {

        Stage stage = (Stage) canb.getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void logoutAction(ActionEvent actionEvent) {
        Session.ClearSession();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/Login.fxml")));
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}