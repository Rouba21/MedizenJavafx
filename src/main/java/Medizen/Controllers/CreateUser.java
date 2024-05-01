package Medizen.Controllers;

import Medizen.Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Medizen.Models.User;
import Medizen.Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateUser implements Initializable {
    @FXML
    private ComboBox roleInput;

    @FXML
    private TextField motDePasse;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private Label error;
    private final UserService us =new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleInput.getItems().addAll("Artiste", "Client");

    }


    // @FXML
    //  public void login(ActionEvent actionEvent) {
    //     String roleSelected = roleInput.getValue().toString();
    //  System.out.println(roleSelected);
//
//String vnom=nom.getText();
    //  }

    @FXML
    void navigate (ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/DisplayUser.fxml")));
            Stage stage = (Stage) roleInput.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
