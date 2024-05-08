package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Event;
import tn.esprit.models.Sponseur;
import tn.esprit.util.MaConnexion;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService implements IService<Event> {

    private Connection cnx;

    public EventService() {
        this.cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public int add(Event event) throws SQLException {
        String req = "INSERT INTO event (titre, date_debut, date_fin, lieu, description, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, event.getTitre());
            preparedStatement.setDate(2, new java.sql.Date(event.getDateDebut().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(event.getDateFin().getTime()));
            preparedStatement.setString(4, event.getLieu());
            preparedStatement.setString(5, event.getDescription());
            preparedStatement.setString(6, event.getImageURL());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                event.setId(rs.getInt(1));
            }
            System.out.println("Event ajouté avec succès (ID: " + event.getId() + ")");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
            throw e;
        }
        return 0;
    }

    @Override
    public List<?> getAllSponseurs() throws SQLException {
        return null;
    }

    @Override
    public void update(Event event) throws SQLException {
        String req = "UPDATE event SET titre=?, date_debut=?, date_fin=?, lieu=?, description=?, image=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, event.getTitre());
            preparedStatement.setDate(2, new java.sql.Date(event.getDateDebut().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(event.getDateFin().getTime()));
            preparedStatement.setString(4, event.getLieu());
            preparedStatement.setString(5, event.getDescription());
            preparedStatement.setString(6, event.getImageURL());
            preparedStatement.setInt(7, event.getId());

            preparedStatement.executeUpdate();
            System.out.println("Event mis à jour avec succès (ID: " + event.getId() + ")");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'événement : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM event WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Event supprimé avec succès (ID: " + id + ")");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Event> afficher() throws SQLException {
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM event";
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des événements : " + e.getMessage());
            throw e;
        }
        return events;
    }

    @Override
    public List<?> getAllEvent() throws SQLException {
        return null;
    }

    @Override
    public void addSponseurToEvent(int eventId, int sponseurId) throws SQLException {

    }

    @Override
    public void updateSponseurInEvent(int eventId, int sponseurId) throws SQLException {

    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event";
        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image")
                        // Ajoutez d'autres attributs si nécessaire
                );
                events.add(event);
            }
        }
        return events;
    }

    public void participer(Event event, int userId) throws SQLException {
        String query = "INSERT INTO event_user (event_id, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, event.getId());
            statement.setInt(2, userId);
            statement.executeUpdate();
            System.out.println("Participation enregistrée avec succès dans la base de données.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la participation : " + e.getMessage());
            throw e;
        }
    }

    public List<Sponseur> getSponseursByEvent(int eventId) throws SQLException {
        List<Sponseur> sponseurs = new ArrayList<>();
        String req = "SELECT sponseur.* FROM sponseur JOIN event_sponseur ON sponseur.id = event_sponseur.sponseur_id WHERE event_sponseur.event_id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, eventId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Sponseur sponseur = new Sponseur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getInt("numero"),
                        rs.getString("logo"),
                        rs.getString("pack")
                );
                sponseurs.add(sponseur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des sponsors pour l'événement : " + e.getMessage());
            throw e;
        }
        return sponseurs;
    }

    public void ajouterAssociationEvenementSponseur(int eventId, int sponseurId) throws SQLException {
        String query = "INSERT INTO event_sponseur (event_id, sponseur_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, sponseurId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Association événement-sponsor ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout de l'association événement-sponsor.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de l'ajout de l'association événement-sponsor : " + e.getMessage());
            throw e;
        }
    }
    public List<Event> getEventsByDateAscending() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event ORDER BY date_debut ASC"; // Trier par date de début croissante
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du tri des événements par date croissante : " + e.getMessage());
            throw e;
        }
        return events;
    }
    public List<Event> getEventsByDateDescending() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event ORDER BY date_debut DESC"; // Trier par date de début décroissante
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du tri des événements par date décroissante : " + e.getMessage());
            throw e;
        }
        return events;
    }



}
