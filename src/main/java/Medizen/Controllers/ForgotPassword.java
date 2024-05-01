package Medizen.Controllers;

import Medizen.Services.UserService;
import com.sun.mail.iap.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Medizen.Utils.Generator;
import Medizen.Utils.MailSender;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgotPassword implements Initializable {

    public Button sms;
    @FXML
    private Button codeb;

    @FXML
    private Label codel;

    @FXML
    private TextField codet;

    @FXML
    private TextField emailt;
    int userIsOnAction=1;
    String code;
    private UserService us=new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeb.setText("Submit Your Email");
        codet.setDisable(true);
    }

    @FXML
    public void sendcode(ActionEvent actionEvent) throws SQLException {
        if(userIsOnAction==1){
            if(us.tryLogin2(emailt.getText())==false){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Erreur");
                alert.setContentText("Email doesn't exist");
                alert.showAndWait();
            }else{
                code=Generator.generateCode(6);
                MailSender.SendEmail(emailt.getText(), "Réinitialisez le mot de passe", "noussa "+code);
                codeb.setText("Envoyez Code");
                codet.setDisable(false);
                emailt.setDisable(true);
                userIsOnAction=2;

            }


        }else{

            if(!codet.getText().equals(code)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Errer");
                alert.setContentText("Code est incorrect");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succés");
                alert.setHeaderText("Succés");
                alert.setContentText("Code est correct");
                alert.showAndWait();
                codet.setDisable(true);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/at_one/Resetpwd.fxml"));
                    Parent root = loader.load();
                    Resetpwd resetController = loader.getController();
                    resetController.setUser(emailt.getText());
                    Stage stage = (Stage) emailt.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }



        }

    }



}