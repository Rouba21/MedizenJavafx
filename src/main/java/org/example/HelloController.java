package org.example;


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
import Medizen.Utils.Encryption;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    public TextField cpwd;
    public Button login;
    public Button sb;
    public TextField numt;
    public Button canb;
    @FXML
    private TextField motDePasse;
    @FXML
    private ComboBox roleInput;
    @FXML
    private Label error;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    private UserService us=new UserService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleInput.getItems().addAll("Artiste", "Client");

    }


    @FXML
    public void signup(ActionEvent actionEvent) throws SQLException {


        String vnom = nom.getText();
        String vprenom = prenom.getText();
        String vemail = email.getText();
        String vmotdepass = motDePasse.getText();
        String vpwd = cpwd.getText();
        String vnum=numt.getText();

        User user = us.tryLogin(vnom);

        if (vnom.isEmpty() || vprenom.isEmpty() || vemail.isEmpty() || vmotdepass.isEmpty() || roleInput.getValue() == null|| Integer.parseInt(vnum)==0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont requis");
            alert.showAndWait();

        } else {
            if (us != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur   " + vnom + " existe déja");
                alert.showAndWait();
            } else {
                boolean testEmailExist = us.tryLogin2(vemail);
                if (testEmailExist) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Email  " + vemail + "  existe déjà essayez un autre ");
                    alert.showAndWait();
                } else if (vemail.indexOf('@') == -1 || vemail.indexOf('.') == -1 || vemail.indexOf('.') < vemail.indexOf('@') || vemail.indexOf('.') == vemail.length() || vemail.indexOf('@') == vemail.length() || vemail.indexOf('@') == 0 || vemail.indexOf('.') == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("l'email est invalide");
                    alert.showAndWait();
                } else if (!vmotdepass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$") || vmotdepass.length() < 8) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Le mot de passe doit contenir au moins une majuscule, une minuscule et un chiffre et doit avoir au moins 8 caractères");
                    alert.showAndWait();
                } else if (motDePasse.getText().equals(vpwd) == false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Les mots de passe ne sont pas identiques");
                    alert.showAndWait();
                }
                else if(!((vnum+"").matches("[0-9]+"))){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("numéro invalide");
                    alert.showAndWait();
                }
                else {
                    String encryptedPassword = Encryption.EncryptPassword(vmotdepass);

                    user = new User(
                            Integer.parseInt(vnum), // Assuming this is the ID, and your form provides it, which is unusual
                            vemail,
                            roleInput.getValue().toString(),
                            vnom, // Assuming `vnom` is the `username`
                            vprenom, // Assuming `vprenom` is the `lastName`
                            LocalDate.now(), // If you need the current date as `date_de_naissance` or get it from a picker
                            false // Assuming new users are not blocked by default
                    );
                    us.add(user);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("inscription a été effectué avec succès. - Traduction anglaise");
                    alert.setHeaderText(null);
                    alert.setContentText(" inscription réussie");
                    alert.showAndWait();
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/Login.fxml")));
                        Stage stage = (Stage) roleInput.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    public void navigate(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/Login.fxml")));
            Stage stage = (Stage) roleInput.getScene().getWindow();
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
}
