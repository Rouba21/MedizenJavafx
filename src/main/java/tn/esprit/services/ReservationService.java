package tn.esprit.services;
import tn.esprit.interfaces.IReservation;
import tn.esprit.models.Docteur;
import tn.esprit.models.Reservation;
import tn.esprit.util.MaConnexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IReservation<Reservation> {
    private static final Connection cnx = MaConnexion.getInstance().getCnx();
    private Statement st;

    @Override
    public void add(Reservation reservation) {
        String request = "INSERT INTO reservation (surname, problem_description, mobile, reservation_date, status, name, address,doctor_id) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setString(1, reservation.getSurname());
            preparedStatement.setString(2, reservation.getProblemDescription());
            preparedStatement.setInt(3, reservation.getMobile());
            preparedStatement.setDate(4, Date.valueOf(reservation.getReservationDate()));
            preparedStatement.setString(5, reservation.getStatus());
            preparedStatement.setString(6, reservation.getName());
            preparedStatement.setString(7, reservation.getAddress());
            preparedStatement.setInt(8, reservation.getDocteur().getId());
            preparedStatement.executeUpdate();
            System.out.println("Reservation added with success!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Reservation reservation) {
        LocalDate reservationDate = reservation.getReservationDate();
        if (reservationDate == null) {
            reservationDate = LocalDate.now();
        }

        // Vérification de nullité pour éviter NullPointerException
        Docteur docteur = reservation.getDocteur();
        if (docteur != null) {
            String request = "UPDATE reservation SET surname=?, problem_description=?, mobile=?, reservation_date=?, status=?, name=?, address=?,doctor_id=? WHERE id=?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
                preparedStatement.setString(1, reservation.getSurname());
                preparedStatement.setString(2, reservation.getProblemDescription());
                preparedStatement.setInt(3, reservation.getMobile());
                preparedStatement.setDate(4, java.sql.Date.valueOf(reservationDate));
                preparedStatement.setString(5, reservation.getStatus());
                preparedStatement.setString(6, reservation.getName());
                preparedStatement.setString(7, reservation.getAddress());
                preparedStatement.setInt(8, docteur.getId());


                preparedStatement.executeUpdate();
                System.out.println("Reservation updated with success!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(Reservation reservation) {
        String request = "DELETE FROM reservation WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setInt(1, reservation.getId());
            preparedStatement.executeUpdate();
            System.out.println("Reservation deleted with success!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Reservation getOne(int id) {
        String request = "SELECT * FROM reservation WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return mapToReservation(rs);
            } else {
                System.out.println("No reservation found with ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String request = "SELECT * FROM reservation";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reservations.add(mapToReservation(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching reservations", e);
        }
        return reservations;
    }

    public int FindByDocteur(int doctorId) {
        int reservationCount = 0;
        try {
            // Define the SQL query
            String query = "SELECT COUNT(*) AS reservation_count FROM reservation WHERE doctor_Id = " + doctorId;
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery(query);

            // Extract the result
            if (resultSet.next()) {
                reservationCount = resultSet.getInt("reservation_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationCount;
    }

    private static Reservation mapToReservation(ResultSet rs) throws SQLException {
        DocteurService docteurService = new DocteurService();
        Docteur docteur = docteurService.getOne(rs.getInt("doctor_id"));
        return new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("mobile"),
                rs.getString("problem_description"),
                rs.getString("address"),
                rs.getString("status"),
                rs.getDate("reservation_date").toLocalDate(),
                rs.getInt("doctor_id")
        );
    }

}

