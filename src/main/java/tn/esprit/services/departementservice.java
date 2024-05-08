package tn.esprit.services;

import tn.esprit.interfaces.CRUD;
import tn.esprit.models.Departement;
import tn.esprit.util.Connectiondb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class departementservice implements CRUD<Departement> {
    private Connection cnx;

    public departementservice() {
        cnx = Connectiondb.getInstance().getCnx();
    }

    @Override
    public void ajouter(Departement departement) throws SQLException {
        String query = "INSERT INTO departement (nom, description, chef_departement, service_offerts, localisation,etablissement_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, departement.getNom());
            preparedStatement.setString(2, departement.getDescription());
            preparedStatement.setString(3, departement.getChef_departement());
            preparedStatement.setString(4, departement.getService_offerts());
            preparedStatement.setString(5, departement.getLocalisation());
            preparedStatement.setInt(6, departement.getEtablissement().getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Departement departement) throws SQLException {
        String query = "UPDATE departement SET nom=?, description=?, chef_departement=?, service_offerts=?, localisation=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, departement.getNom());
            preparedStatement.setString(2, departement.getDescription());
            preparedStatement.setString(3, departement.getChef_departement());
            preparedStatement.setString(4, departement.getService_offerts());
            preparedStatement.setString(5, departement.getLocalisation());
            preparedStatement.setInt(6, departement.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM departement WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Departement> afficher() throws SQLException {
        List<Departement> departements = new ArrayList<>();
        String query = "SELECT * FROM departement";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Departement departement = new Departement(
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("chef_departement"),
                        resultSet.getString("services_offerts"),
                        resultSet.getString("localisation")

                );
                departement.setId(resultSet.getInt("id"));
                departements.add(departement);
            }
        }
        return departements;
    }
    public List<Departement> afficherById(int id ) throws SQLException {
        List<Departement> departements = new ArrayList<>();
        String query = "SELECT * FROM departement WHERE etablissement_id = ?";


            try (PreparedStatement statement = cnx.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();


            while (rs.next()) {
                Departement departement = new Departement(
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("chef_departement"),
                        rs.getString("services_offerts"),
                        rs.getString("localisation")

                );
                departement.setId(rs.getInt("id"));
                departements.add(departement);
            }
        }
        return departements;
    }
}
