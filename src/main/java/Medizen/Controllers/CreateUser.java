package Medizen.Controllers;

import Medizen.Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Medizen.Models.User;
import Medizen.Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateUser implements Initializable {
    @FXML
    private Button canb;

    @FXML
    private PasswordField cpwd;

    @FXML
    private TextField email;

    @FXML
    private Label error;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField motDePasse;

    @FXML
    private TextField nom;

    @FXML
    private TextField numt;

    @FXML
    private TextField prenom;

    @FXML
    private ComboBox<?> roleInput;

    @FXML
    private Button signupButton;
    private final UserService us =new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //roleInput.getItems().addAll("Medecin", "Client");

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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/test/DisplayUser.fxml")));
            Stage stage = (Stage) roleInput.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void signup(ActionEvent actionEvent) {
        // Gather user input from text fields
        String emailText = email.getText();
        //String roleText = roleInput.getValue().toString();
        String passwordText = cpwd.getText();
        String usernameText = nom.getText(); // Assuming "nom" is used for username
        String lastnameText = prenom.getText();
        String numtel = numt.getText();
        // Assuming "prenom" is used for last name
        // Assuming date_de_naissance is not entered from UI, you may need another text field for it

        // Create a LocalDate object for date_de_naissance, assuming it's not entered from UI
        LocalDate date_de_naissance = LocalDate.now(); // Example, you need to modify this accordingly

        // Create a new User object
        User newUser = new User(emailText, "ROLE_ADMIN", passwordText, usernameText, lastnameText, date_de_naissance, false);

        // Add the user using UserService
        us.add(newUser);

        // Redirect to the login page
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/test/Login.fxml")));
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @javafx.fxml.FXML
    public void cancelButtonAction (ActionEvent actionEvent){
        Stage stage = (Stage) canb.getScene().getWindow();
        stage.close();
    }
}
