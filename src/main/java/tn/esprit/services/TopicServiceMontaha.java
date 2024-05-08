package tn.esprit.services;

import tn.esprit.interfaces.IServiceMontaha;
import tn.esprit.models.Publication;
import tn.esprit.models.Topic;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TopicServiceMontaha implements IServiceMontaha<Topic> {
    // Attribut
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Topic topic) {
        String req = "INSERT INTO `topic` (`titre`, `contenu`, `datedecreation`) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, topic.getTitre());
            preparedStatement.setString(2, topic.getContenu());

            // Formater la date dans le format approprié (par exemple 'yyyy-MM-dd')
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(topic.getDatedecreation());
            preparedStatement.setString(3, formattedDate);

            preparedStatement.executeUpdate();
            System.out.println("Topic ajouté avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Topic topic) throws SQLException {
        String req = "UPDATE topic SET titre=?, contenu=?, datedecreation=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, topic.getTitre());
            preparedStatement.setString(2, topic.getContenu());

            // Formater la date dans le format approprié (par exemple 'yyyy-MM-dd')
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(topic.getDatedecreation());
            preparedStatement.setString(3, formattedDate);

            preparedStatement.setInt(4, topic.getId());

            preparedStatement.executeUpdate();
            System.out.println("Topic mis à jour avec succès !");
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM topic WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Suppression réussie du topic d'ID : " + id);
        }
    }

    @Override
    public List<Topic> getAll()throws SQLException {
        List<Topic> topics = new ArrayList<>();
        String req = "SELECT * FROM topic";
        try (Statement statement = cnx.createStatement(); ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Topic topic = new Topic(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("contenu"),
                        rs.getDate("datedecreation")
                );
                // Charger les publications associées à ce sujet
                List<Publication> publications = getPublicationsForTopic(topic.getId());
                topic.setPublications(publications);
                topics.add(topic);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des sujets", e);
        }
        return topics;
    }

    // Méthode pour charger les publications associées à un sujet donné
    public List<Publication> getPublicationsForTopic(int topicId)throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String req = "SELECT * FROM publication WHERE topic_id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, topicId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Publication publication = new Publication(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("contenu"),
                        rs.getDate("datedecreation"),
                        null // Vous pouvez initialiser le sujet plus tard si nécessaire
                );
                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des publications pour le sujet d'ID : " + topicId, e);
        }
        return publications;
    }

    @Override
    public Topic getOne(int id) {
        // Non implémenté pour le moment
        return null;
    }


}
