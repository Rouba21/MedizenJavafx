package Medizen.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Medizen.Models.User;
import Medizen.Services.UserService;
import Medizen.Utils.Encryption;
import Medizen.Utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

    public class Profil implements Initializable {


        @javafx.fxml.FXML
        public TextField nom;
        @javafx.fxml.FXML
        public TextField prenom;
        @javafx.fxml.FXML
        public TextField email;
        @javafx.fxml.FXML
        public TextField password;
        public Button canb;
        @javafx.fxml.FXML
        private Button logout;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            User user= Session.getInstance().getUser();
            nom.setText(user.getUsername());
            prenom.setText(user.getLastname());
            email.setText(user.getEmail());
            password.setText("");
        }

        @javafx.fxml.FXML
        public void openModifierForm(ActionEvent actionEvent) {
            nom.setDisable(false);
            prenom.setDisable(false);
            email.setDisable(false);
            password.setDisable(false);



        }

        @javafx.fxml.FXML
        public void save(ActionEvent actionEvent) {
            if(   nom.getText().isEmpty()||
                    prenom.getText().isEmpty()||
                    email.getText().isEmpty()||
                    password.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Tous les champs sont requis!");
                alert.showAndWait();
            }else{

                Session.getInstance().getUser().setUsername(nom.getText());
                Session.getInstance().getUser().setLastname(prenom.getText());
                Session.getInstance().getUser().setEmail(email.getText());
                Session.getInstance().getUser().setPassword(Encryption.EncryptPassword(password.getText()));

                nom.setDisable(true);
                prenom.setDisable(true);
                email.setDisable(true);
                password.setDisable(true);
                password.setText("");
                UserService us=new UserService();
                try{
                    us.update(Session.getInstance().getUser());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("L'utilisateur a été mis à jour avec succès !");
                    alert.showAndWait();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
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

        public void cancelButtonAction(ActionEvent actionEvent) {
            Stage stage = (Stage) canb.getScene().getWindow();
            stage.close();
        }

        public void Accueil(ActionEvent actionEvent) {

            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/ListeOeuvre.fxml")));
                Stage stage = (Stage) logout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

}
