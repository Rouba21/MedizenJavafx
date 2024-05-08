
package tn.esprit.controllers.Docteur;

import com.jfoenix.controls.JFXPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.controllers.Docteur.animations.Animations;
import tn.esprit.models.Docteur;
import tn.esprit.services.DocteurService;
import tn.esprit.services.ReservationService;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsDocteur implements Initializable {

    private final DocteurService docteurService = new DocteurService();
    @FXML
    private AnchorPane rootStatistics;

    @FXML
    private AnchorPane rootDate;

    @FXML
    private PieChart pieChart;

    @FXML
    private Text title;

    @FXML
    private HBox hbox;

    @FXML
    private HBox hboxImage;

    @FXML
    private ImageView emptyImage;
    private final Docteur docteur = new Docteur();
    private final DatePicker datepicker = new DatePicker();
    private final ReservationService rs = new ReservationService();


    private final DatePickerSkin datePickerSkin = new DatePickerSkin(datepicker);

    private final Node popupContent = datePickerSkin.getPopupContent();

    private final JFXPopup popup = new JFXPopup();

    private final ObservableList<PieChart.Data> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setGraphics();
        //setPopup();
        //setAnimations();
        //setNodeStartupConfiguration();
    }


    private void setAnimations() {
        Animations.fadeInUp(hboxImage);
        Animations.fadeInUp(title);
    }


    private void setNodeStartupConfiguration() {
        popupContent.getStyleClass().addAll("jfx-date-picker");
        hboxImage.setVisible(true);
        hbox.setVisible(false);
        pieChart.setLegendVisible(false);
    }


    private void setPopup() {
        popup.setPopupContent(rootDate);

        pieChart.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.SECONDARY)) {
                popupContent.setVisible(true);
                popup.show(pieChart);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });

        hboxImage.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.SECONDARY)) {
                popupContent.setVisible(true);
                popup.show(hboxImage);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });

        emptyImage.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.SECONDARY)) {
                popupContent.setVisible(true);
                popup.show(emptyImage);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });

        rootStatistics.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.SECONDARY)) {
                popupContent.setVisible(true);
                popup.show(rootStatistics);
                popup.setAnchorX(ev.getScreenX());
                popup.setAnchorY(ev.getScreenY());
            }
        });
    }

    private void setGraphics() {
        pieChart.getData().clear();
        ObservableList<Docteur> allDocteurs = FXCollections.observableArrayList(docteurService.getAll());

        for (Docteur doctor : allDocteurs) {
            try {
                int reservationCount = rs.FindByDocteur(doctor.getId());
                PieChart.Data doctorData = new PieChart.Data(doctor.getNom() + "'s reservations", reservationCount);
                System.out.println(reservationCount);
                pieChart.getData().add(doctorData);

                Animations.hover(doctorData.getNode(), 50, 1.5);
            } catch (Exception e) {
                System.err.println("Error fetching reservation count for doctor: " + doctor.getNom());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("/Reservation/Home.fxml"));
    }

    public void event_btn(ActionEvent actionEvent) {
    }

    public void sponseur_btn(ActionEvent actionEvent) {
    }

    public void sujet_btn(ActionEvent actionEvent) {
    }

    public void etablissement_btn(ActionEvent actionEvent) {
    }

    public void revervation_btn(ActionEvent actionEvent) {
    }

    public void medicament_btn(ActionEvent actionEvent) {
    }

    public void docteur_btn(ActionEvent actionEvent) {
    }
}







