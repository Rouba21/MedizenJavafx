package tn.MediZen.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.MediZen.models.Reservation;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private CalendarView calendarView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setReservations(List<Reservation> reservations) {
        Calendar calendar = new Calendar("Reservations");
        for (Reservation reservation : reservations) {
            Entry<String> entry = new Entry<>(reservation.getName());
            entry.changeStartDate(reservation.getReservationDate());
            entry.changeEndDate(reservation.getReservationDate()); // Assuming reservation is for one day
            calendar.addEntry(entry);
        }

        // Create a CalendarSource and add the Calendar to it
        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendar);

        // Clear existing calendars and add the new calendar source
        calendarView.getCalendarSources().clear();
        calendarView.getCalendarSources().add(calendarSource);
    }


    @FXML
    private void loadCalendarView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
            Parent root = loader.load();

            CalendarController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Calendar View");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
