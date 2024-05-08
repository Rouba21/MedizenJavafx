package tn.esprit.services;
import tn.esprit.interfaces.IService;
import tn.esprit.interfaces.IServiceMontaha;
import tn.esprit.models.Like;
import tn.esprit.models.Publication;
import tn.esprit.models.User;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeService implements IServiceMontaha<Like> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Like like) {
        String req = "INSERT INTO `like`(`id_publication_id`, `user_id`) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, like.getPublicationId());
            preparedStatement.setInt(2, like.getUserId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    like.setId(id);
                    System.out.println("Like ajouté avec succès ! (ID: " + id + ")");
                } else {
                    System.out.println("Échec de récupération de l'ID du Like ajouté.");
                }
            } else {
                System.out.println("Aucun Like n'a été ajouté.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du Like", e);
        }
    }


    @Override
    public void update(Like like) {
        // Comme il n'y a probablement pas de besoin de mettre à jour l'entité Like, cette méthode peut rester vide ou être supprimée
    }
    @Override
    public void delete(int id) {
        String req = "DELETE FROM `like` WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Like supprimé avec succès !");
            } else {
                System.out.println("Aucun Like trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du Like", e);
        }
    }
    public void delete2(int id) throws SQLException {
        String sql ="delete from commentaire where id_publication_id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();


    }
    public void deleteByPublicationAndUser(int publicationId, int userId) {
        String req = "DELETE FROM `like` WHERE id_publication_id = ? AND user_id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, publicationId);
            preparedStatement.setInt(2, userId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Like supprimé avec succès !");
            } else {
                System.out.println("Aucun Like trouvé avec la publication ID : " + publicationId + " et l'ID de l'utilisateur : " + userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du Like", e);
        }
    }

    @Override
    public List<Like> getAll() {
        List<Like> likes = new ArrayList<>();
        String req = "SELECT * FROM `like`";
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                int likeId = rs.getInt("id");
                int publicationId = rs.getInt("id_publication_id");
                int userId = rs.getInt("user_id");

                // Création de l'objet User avec l'ID récupéré du like
                User user = new User(userId);

                // Création de l'objet Publication avec l'ID récupéré du like
                Publication publication = new Publication(publicationId);

                // Création de l'objet Like avec les données du résultat de la requête
                Like like = new Like(likeId, publicationId, userId);
                likes.add(like);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des Likes", e);
        }
        return likes;
    }


    public List<Like> getLikesForPub(int id) {
        List<Like> likes = new ArrayList<>();
        String req = "SELECT * FROM `like` WHERE id_publication_id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int likeId = rs.getInt("id");
                int publicationId = rs.getInt("id_publication_id");
                int userId = rs.getInt("user_id");

                Like like = new Like(likeId, publicationId, userId);
                likes.add(like);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des Likes pour la publication d'ID : " + id, e);
        }
        return likes;
    }



    @Override
    public Like getOne(int id) {
        return null;
    }
}
