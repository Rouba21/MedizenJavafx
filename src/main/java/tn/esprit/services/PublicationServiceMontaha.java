// PublicationService.java
package tn.esprit.services;

import tn.esprit.interfaces.IServiceMontaha;
import tn.esprit.models.Publication;
import tn.esprit.models.Topic;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PublicationServiceMontaha implements IServiceMontaha<Publication> {
    private final Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Publication publication) {
        String req = "INSERT INTO publication (topic_id, contenu, datedecreation, image) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, publication.getTopic().getId());
            preparedStatement.setString(2, publication.getContenu());
            preparedStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(publication.getDatedecreation()));
            preparedStatement.setString(4, publication.getImage());
            preparedStatement.executeUpdate();

            // Récupérer l'ID de la publication nouvellement ajoutée
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                publication.setId(rs.getInt(1));
            }

            System.out.println("Publication ajoutée avec succès !");
        } catch (SQLException e) {
            // Propager l'exception vers l'appelant
            throw new RuntimeException("Erreur lors de l'ajout de la publication", e);
        }
    }
    @Override
    public void update(Publication publication) throws SQLException {
        String req = "UPDATE publication SET contenu=?, datedecreation=?, image=?, topic_id=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, publication.getContenu());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(publication.getDatedecreation());
            preparedStatement.setString(2, formattedDate);

            preparedStatement.setString(3, publication.getImage());
            preparedStatement.setInt(4, publication.getTopic().getId()); // Assurez-vous d'avoir un getId() dans votre classe Topic
            preparedStatement.setInt(5, publication.getId());

            preparedStatement.executeUpdate();
            System.out.println("Publication mise à jour avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM publication WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Suppression réussie de la publication d'ID : " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la publication", e);
        }
    }


    @Override
    public List<Publication> getAll()throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String req = "SELECT * FROM publication";
        try (Statement statement = cnx.createStatement(); ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Topic topic = new Topic(rs.getInt("topic_id"), "", "", null);
                Publication publication = new Publication(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("contenu"),
                        rs.getDate("datedecreation"),
                        topic
                );
                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des publications", e);
        }
        return publications;
    }


    @Override
    public Publication getOne(int id) {
        // Récupérer une publication par son ID
        String req = "SELECT * FROM publication WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic(rs.getInt("topic_id"), "", "", null);
                    return new Publication(
                            rs.getInt("id"),
                            rs.getString("image"),
                            rs.getString("contenu"),
                            rs.getDate("datedecreation"),
                            topic
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la publication", e);
        }
        return null; // Retourner null si aucune publication n'est trouvée
    }

    public Publication getLastAddedPublication() throws SQLException {
        String query = "SELECT * FROM publication ORDER BY datedecreation DESC LIMIT 1";
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            if (rs.next()) {
                Topic topic = new Topic(rs.getInt("topic_id"), "", "", null);
                return new Publication(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("contenu"),
                        rs.getDate("datedecreation"),
                        topic
                );
            }
        }
        return null; // Retourner null si aucune publication n'a été trouvée
    }
}
