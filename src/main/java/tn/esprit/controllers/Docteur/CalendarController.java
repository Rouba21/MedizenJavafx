package tn.esprit.controllers.Docteur;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import tn.esprit.models.Reservation;

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
            entry.changeEndDate(reservation.getReservationDate());
            calendar.addEntry(entry);
        }
        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().clear();
        calendarView.getCalendarSources().add(calendarSource);
    }

}
