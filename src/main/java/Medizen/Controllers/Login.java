package Medizen.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Medizen.Models.User;
import Medizen.Services.UserService;
import Medizen.Utils.Encryption;
import Medizen.Utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Login {
    @javafx.fxml.FXML
    public Button logb;
    @javafx.fxml.FXML
    public Button canb;
    @javafx.fxml.FXML
    public TextField logt;
    @javafx.fxml.FXML
    public Label loginmsg;
    @javafx.fxml.FXML
    public Label forgottenpwd;
    @javafx.fxml.FXML
    public Button Create_account;
    @javafx.fxml.FXML
    public CheckBox spwd;
    public ImageView a1;
    @javafx.fxml.FXML
    public Hyperlink lfp;
    @javafx.fxml.FXML
    public AnchorPane ap;
    @javafx.fxml.FXML
    private Label error;

    private UserService us = new UserService();
    @javafx.fxml.FXML
    private PasswordField pwdt;
    @javafx.fxml.FXML
    private TextField passwordText;

    @javafx.fxml.FXML
    public void loginButtonAction(ActionEvent actionEvent) throws SQLException {

        String nom = logt.getText();
        String mp = pwdt.getText();
        System.out.println(nom + " " + mp);
        // Ensure nom is initialized
        // Validate login
        if (nom.isEmpty()||mp.isEmpty()) {
            forgottenpwd.setText("* veuillez entrer un nom et un mot de passe");
        } else if(us.tryLogin1(nom)==false){
            forgottenpwd.setText("Login inexistant");


        }

        else {
            User user =  us.tryLogin(nom);
            System.out.println(Encryption.checkPassword( mp,user.getPassword()));
            if(user != null && Encryption.checkPassword( mp,user.getPassword())) {
                forgottenpwd.setText("Connecté avec succès");
                Session.start_session(user);
                if(user.getRoles().equals("Admin")) {
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/DisplayUser.fxml")));
                        Stage stage = (Stage) logb.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else {


                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/Profil.fxml")));
                        Stage stage = (Stage) logb.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }

            }else{
                forgottenpwd.setText("Mot de passe incorrect");
            }
        }




    }

    @javafx.fxml.FXML
    public void cancelButtonAction (ActionEvent actionEvent){
        Stage stage = (Stage) canb.getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void Createaccount(MouseEvent mouseEvent) {
    }

    @javafx.fxml.FXML
    public void showpwd(ActionEvent actionEvent) {
        if(spwd.isSelected()){
            passwordText.setText(pwdt.getText());
            passwordText.setLayoutX(pwdt.getLayoutX());
            passwordText.setLayoutY(pwdt.getLayoutY());
            passwordText.setVisible(true);
            pwdt.setVisible(false);
        }else {
            pwdt.setText(passwordText.getText());
            pwdt.setLayoutX(passwordText.getLayoutX());
            pwdt.setLayoutY(passwordText.getLayoutY());
            passwordText.setLayoutY(1000);
            pwdt.setVisible(true);
            passwordText.setVisible(false);
        }

    }

    @Deprecated
    public void resetlb(MouseEvent mouseEvent) {

    }

    @javafx.fxml.FXML
    public void Create_accountAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/CreateAccount.fxml")));
            Stage stage = (Stage) logb.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void reset(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/at_one/choix.fxml")));
            Stage stage = (Stage) logb.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}