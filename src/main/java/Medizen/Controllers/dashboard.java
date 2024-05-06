package Medizen.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import Medizen.Models.User;
import Medizen.Utils.Encryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Medizen.Services.UserService;
import Medizen.Models.User;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

public class dashboard {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    //BORDER TOP
    @FXML
    private Button menu;
    @FXML
    private Button menuClose;
    @FXML
    private Button logo;
    @FXML
    private MenuItem deconnecterButton;
    //BORDER LEFT
    @FXML
    private Button usersButton;
    @FXML
    private AnchorPane slider;
    //BORDER CENTER
    //ANCHOR PANE USERS
    @FXML
    private AnchorPane usersAnchorPane;
    @FXML
    private TextField rechercheDashboard;
    @FXML
    private TextField pseudoDashboard, cinDashboard, nomDashboard, prenomDashboard, ageDashboard, numtelDashboard, emailDashboard;
    @FXML
    private ToggleGroup roles;
    @FXML
    private RadioButton roleAdminSignup, rolepatientSignup;
    @FXML
    private Label roleSignupError;
    @FXML
    private AnchorPane ajouterAnchorPane;
    @FXML
    private TextField pseudoAjout, cinAjout, nomAjouter, prenomAjout, ageAjout, numtelAjout, emailAjout, mdpAjout;
    @FXML
    private PasswordField confirmMdpAjout;
    @FXML
    private Label pseudoError, cinError, nomError, prenomError, ageError, numtelError, emailError, mdpError, confirmMdpError, mdpLabel, confirmMdpLabel;
    @FXML
    private Button annulerButton, ajoutAdminButton;
    @FXML
    private AnchorPane anchorPaneModifierMdp, statsAnchor;
    @FXML
    private PasswordField ancienText, nouveauMdp, confirmNouveauMdp;
    @FXML
    private Label ancienError, nouveauMdpError, confirmNouveauMdpError;
    @FXML
    private Button confirmerNouveauMdp;
    UserService utilisateurService = new UserService();

    private List<User> getlist() throws SQLException {
        return utilisateurService.Readall();
    }

    private Parent parent;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button back;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scrol;
    private List<User> u = new ArrayList<>();

    private List<User> getData() throws SQLException {
        return getlist();
    }

    UserService userService = new UserService();
    private static User loggedInuser;

    public static void setLoggedInuser(User user) {
        loggedInuser = user;
    }

    @FXML
    void initialize() throws SQLException {
        statsAnchor.setVisible(false);
        logo.setOnAction(event -> {
            usersAnchorPane.setVisible(false);
            ajouterAnchorPane.setVisible(false);
            anchorPaneModifierMdp.setVisible(false);
        });
        deconnecterButton.setOnAction(event -> {
            try {
                loggedInuser = null;
                Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                ageAjout.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        updateScroll();
        updateChart();
    }

    private void updateScroll() {
    }



    @FXML
    private void usersButtonOnClick(ActionEvent event) {
        usersAnchorPane.setVisible(true);
        ajouterAnchorPane.setVisible(false);
        anchorPaneModifierMdp.setVisible(false);
        statsAnchor.setVisible(false);
    }

    @FXML
    private void ajouterButtonOnClick(ActionEvent event) {
        usersAnchorPane.setVisible(false);
        ajouterAnchorPane.setVisible(true);
    }

    public boolean getErrors1() {
        // Your existing implementation...
        return false;
    }

    @FXML
    private void ajoutAdminButtonOnClick(ActionEvent event) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (!getErrors1()) {
            User newuser = new User(
                    emailAjout.getText(),
                    roleAdminSignup.isSelected() ? "Admin" : "admin",
                    Encryption.EncryptPassword(mdpAjout.getText()),
                    nomAjouter.getText(),
                    prenomAjout.getText(),
                    LocalDate.now(), // Assuming date_de_naissance is set to the current date
                    false // Assuming blocked is set to false by default for a new user
            );

            userService.add(newuser);
            JOptionPane.showMessageDialog(null, "Admin ajouté avec succès!");
        }
    }


    @FXML
    private void profileButtonOnClick(ActionEvent event) {
        usersAnchorPane.setVisible(true);
        ajouterAnchorPane.setVisible(false);
        anchorPaneModifierMdp.setVisible(false);
        statsAnchor.setVisible(false);
    }



    @FXML
    private void modifierMdpOnClick(ActionEvent event) {
        ajouterAnchorPane.setVisible(false);
        usersAnchorPane.setVisible(false);
        anchorPaneModifierMdp.setVisible(true);
    }

    private boolean getErrorsMdp() throws NoSuchAlgorithmException {
        String thisuserMdp = loggedInuser.getPassword();
        String mdpSaisi = Encryption.EncryptPassword(ancienText.getText());
        if(!thisuserMdp.equals(mdpSaisi)){
            ancienError.setTextFill(Color.RED);
            ancienError.setText("Ancien Mot de passe invalide");
            return true;
        }
        if(nouveauMdp.getText().isBlank()|| nouveauMdp.getText().length() < 8 || nouveauMdp.getText().matches("[^a-zA-Z0-9]")){
            nouveauMdpError.setTextFill(Color.RED);
            nouveauMdpError.setText("Le mot de passe est invalide");
            return true;
        }
        if(confirmNouveauMdp.getText().isBlank()){
            confirmNouveauMdpError.setTextFill(Color.RED);
            confirmNouveauMdpError.setText("La confirmation du mot de passe est invalide");
            return true;
        }
        if(!Objects.equals(confirmNouveauMdp.getText(), nouveauMdp.getText())){
            confirmNouveauMdpError.setTextFill(Color.RED);
            confirmNouveauMdpError.setText("Le mot de passe doit etre le meme");
            return true;
        }
        return false;
    }

    @FXML
    private void confirmerNouveauMdpOnClick(ActionEvent event) throws NoSuchAlgorithmException {
        if (!getErrorsMdp()){
            try {
                userService.modifierMdp(loggedInuser, Encryption.EncryptPassword(nouveauMdp.getText()));
                JOptionPane.showMessageDialog(null,"Mot de Passe modifié avec succès !");
                anchorPaneModifierMdp.setVisible(false);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void pieChart() throws SQLException {
        // Your existing implementation...
    }

    private void updateChart() throws SQLException {
        // Your existing implementation...
    }

    @FXML
    private void showStats() {
        usersAnchorPane.setVisible(false);
        ajouterAnchorPane.setVisible(false);
        anchorPaneModifierMdp.setVisible(false);
        statsAnchor.setVisible(true);
    }

    public void deconnecterButtonOnClick(ActionEvent actionEvent) {
    }
}
