package tn.esprit.services;

import tn.esprit.interfaces.IServiceMontaha;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Publication;
import tn.esprit.models.User;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentaireServiceMontaha implements IServiceMontaha<Commentaire> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Commentaire commentaire) {
        String req = "INSERT INTO commentaire (publication_id, id_user_id, nom, contenu, datedecreation, nbrlikes, nbrdislikes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, commentaire.getPublication().getId());
            preparedStatement.setInt(2, commentaire.getUser().getId());

            // Vérifier si le nom du commentaire est null ou vide
            String nom = commentaire.getNom();
            if (nom == null || nom.isEmpty()) {
                nom = "Valeur par défaut"; // Définir la valeur par défaut
            }
            preparedStatement.setString(3, nom);

            preparedStatement.setString(4, commentaire.getContenu());
            preparedStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(commentaire.getDatedecreation()));

            // Définir la valeur par défaut pour la colonne 'nbrlikes'
            preparedStatement.setInt(6, 0);

            // Définir la valeur par défaut pour la colonne 'nbrdislikes'
            preparedStatement.setInt(7, 0);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    commentaire.setId(generatedId);
                    System.out.println("Commentaire ajouté avec succès. ID généré : " + generatedId);
                }
            } else {
                System.out.println("Erreur lors de l'ajout du commentaire.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    @Override
    public void update(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire SET publication_id=?, id_user_id=?, contenu=?, datedecreation=?, nom=?, nbrlikes=?, nbrdislikes=? WHERE id=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, commentaire.getPublication().getId());
            preparedStatement.setInt(2, commentaire.getUser().getId());
            preparedStatement.setString(3, commentaire.getContenu());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(commentaire.getDatedecreation());
            preparedStatement.setString(4, formattedDate);

            preparedStatement.setString(5, commentaire.getNom());
            preparedStatement.setInt(6, commentaire.getNbrlikes());
            preparedStatement.setInt(7, commentaire.getNbrdislikes());
            preparedStatement.setInt(8, commentaire.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Le commentaire a été mis à jour avec succès !");
            } else {
                System.out.println("Aucun commentaire n'a été mis à jour !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM commentaire WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Suppression réussie du commentaire d'ID : " + id);
            } else {
                System.out.println("Aucun commentaire trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du commentaire d'ID : " + id, e);
        }
    }

    public void delete2(int id) throws SQLException {
        String sql ="delete from commentaire where publication_id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();


    }
    @Override
    public List<Commentaire> getAll() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT * FROM commentaire";
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                // Création d'un objet User avec l'ID récupéré du commentaire
                User user = new User(
                        rs.getInt("id_user_id")
                );
                // Création d'un objet Publication avec l'ID récupéré du commentaire
                Publication publication = new Publication(
                        rs.getInt("publication_id")
                );
                // Création de l'objet Commentaire avec les données du résultat de la requête
                Commentaire commentaire = new Commentaire(
                        rs.getInt("id"),
                        rs.getInt("nbrlikes"),
                        rs.getInt("nbrdislikes"),
                        rs.getString("contenu"),
                        rs.getString("nom"),
                        rs.getDate("datedecreation"),
                        publication,
                        user
                );
                commentaires.add(commentaire);
            }
        }
        return commentaires;
    }
    public List<Commentaire> getCommentsForPub(int id) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT * FROM commentaire WHERE publication_id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int commentaireId = rs.getInt("id");
                int nbrlikes = rs.getInt("nbrlikes");
                int nbrdislikes = rs.getInt("nbrdislikes");
                String contenu = rs.getString("contenu");
                String nom = rs.getString("nom");
                Date datedecreation = rs.getDate("datedecreation");

                // Création de l'objet User avec l'ID récupéré du commentaire
                User user = new User(rs.getInt("id_user_id"));

                // Création de l'objet Publication avec l'ID passé en paramètre
                Publication publication = new Publication(id);

                // Création de l'objet Commentaire avec les données du résultat de la requête
                Commentaire commentaire = new Commentaire(commentaireId, nbrlikes, nbrdislikes, contenu, nom, datedecreation, publication, user);
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commentaires pour la publication d'ID : " + id, e);
        }
        return commentaires;
    }




    @Override
    public Commentaire getOne(int id) {
        return null;
    }
}
