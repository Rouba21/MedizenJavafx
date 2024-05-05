package tn.MediZen.controllers;

import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import tn.MediZen.models.Reservation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class CalendarController implements Initializable {

    @FXML
    private CalendarView calendarView;
    @FXML
    private ChoiceBox<String> statusFilterChoiceBox;


    public void setReservationsPublic(List<Reservation> Reservations) {
        setReservations(Reservations);
    }


    @FXML
    private void initialize() {
        statusFilterChoiceBox.getItems().addAll("All", "Pending", "Accepted", "Rejected");

        statusFilterChoiceBox.setValue("All");
    }
    // Private method to set Reservations
    private void setReservations(List<Reservation> Reservations) {
        for (Reservation Reservation : Reservations) {
            Entry<?> entry = new Entry<>(Reservation.getName());
            entry.setInterval(Reservation.getReservationDate());
            calendarView.getCalendarSources().get(0).getCalendars().get(0).addEntry(entry);
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
