package tn.MediZen.services;

import tn.MediZen.interfaces.IDocteur;
import tn.MediZen.models.Docteur;
import tn.MediZen.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocteurService implements IDocteur<Docteur> {
    private final Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Docteur docteur) {
        String request = "INSERT INTO docteur (nom, prenom, mail, experience, mobile, addresse, specialite) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setString(1, docteur.getNom());
            preparedStatement.setString(2, docteur.getPrenom());
            preparedStatement.setString(3, docteur.getMail());
            preparedStatement.setString(4, docteur.getExperience());
            preparedStatement.setInt(5, docteur.getMobile());
            preparedStatement.setString(6, docteur.getAddresse());
            preparedStatement.setString(7, docteur.getSpecialite());

            preparedStatement.executeUpdate();
            System.out.println("Docteur added with success!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Docteur docteur) {
        String request = "UPDATE docteur SET nom=?, prenom=?, mail=?, experience=?, mobile=?, addresse=?, specialite=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setString(1, docteur.getNom());
            preparedStatement.setString(2, docteur.getPrenom());
            preparedStatement.setString(3, docteur.getMail());
            preparedStatement.setString(4, docteur.getExperience());
            preparedStatement.setInt(5, docteur.getMobile());
            preparedStatement.setString(6, docteur.getAddresse());
            preparedStatement.setString(7, docteur.getSpecialite());
            preparedStatement.setInt(8, docteur.getId());
            preparedStatement.executeUpdate();
            System.out.println("Docteur updated with success!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Docteur docteur) {
        String request = "DELETE FROM docteur WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setInt(1, docteur.getId());
            preparedStatement.executeUpdate();
            System.out.println("Docteur deleted with success!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Docteur getOne(int id) {
        String request = "SELECT * FROM docteur WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Docteur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mail"),
                        rs.getString("experience"),
                        rs.getInt("mobile"),
                        rs.getString("addresse"),
                        rs.getString("specialite")
                );
            } else {
                System.out.println("No docteur found with ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Docteur> getAll() {
        List<Docteur> docteurs = new ArrayList<>();
        String request = "SELECT * FROM docteur";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                docteurs.add(mapToDocteur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching docteurs", e);
        }
        return docteurs;
    }



    private Docteur mapToDocteur(ResultSet rs) throws SQLException {
        return new Docteur(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("mail"),
                rs.getString("experience"),
                rs.getInt("mobile"),
                rs.getString("addresse"),
                rs.getString("specialite")
        );
    }
}