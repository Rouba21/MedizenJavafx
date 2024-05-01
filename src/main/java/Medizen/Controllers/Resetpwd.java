package Medizen.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Medizen.Models.User;
import Medizen.Services.UserService;
import Medizen.Utils.Encryption;

import java.util.Objects;

    public class Resetpwd {

        @FXML
        private TextField cpwd;

        @FXML
        private TextField pwd;

        @FXML
        private Button pwdb;

        @FXML
        private Label pwdl;
        private User user;
        UserService us=new UserService();

        @FXML
        void confirmer(ActionEvent event) {
            if (!pwd.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$") || pwd.getText().length() < 8) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Le mot de passe doit contenir au moins une majuscule, une minuscule et un chiffre et au moins 8 caractères");
                alert.showAndWait();
            }else if(pwd.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setContentText("Le champ du mot de passe ne peut pas être vide");
                alert.showAndWait();
            }else if(!pwd.getText().equals(cpwd.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setContentText("Le mot de passe ne correspond pas");
                alert.showAndWait();
            }else {
                String encryptedPassword = Encryption.EncryptPassword(pwd.getText());
                user.setPassword(encryptedPassword);
                try {
                    us.update(user);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Le mot de passe a été changé avec succès");
                    alert.showAndWait();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/Login.fxml")));
                    Stage stage = (Stage) pwd.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        void setUser(String email) {
            this.user=us.tryLoginByEmail(email);

        }


}
