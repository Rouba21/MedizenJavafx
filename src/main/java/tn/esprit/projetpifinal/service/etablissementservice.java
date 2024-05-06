package tn.esprit.projetpifinal.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.utils.Connectiondb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class etablissementservice implements CRUD<Etablissement> {
    private Connection cnx;

    public etablissementservice() {
        cnx = Connectiondb.getInstance().getCnx();
    }

    @Override
    public void ajouter(Etablissement etablissement) throws SQLException {
        String query = "INSERT INTO etablissement (name, type, location, description, longitude, latitude) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, etablissement.getName());
            preparedStatement.setString(2, etablissement.getTypee());
            preparedStatement.setString(3, etablissement.getLocation());
            preparedStatement.setString(4, etablissement.getDescription());
            preparedStatement.setBigDecimal(5, etablissement.getLongitude());
            preparedStatement.setBigDecimal(6, etablissement.getLatitude());
            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void modifier(Etablissement etablissement) throws SQLException {
        String query = "UPDATE etablissement SET name=?, type=?, location=?, description=?, longitude=?, latitude=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, etablissement.getName());
            preparedStatement.setString(2, etablissement.getTypee());
            preparedStatement.setString(3, etablissement.getLocation());
            preparedStatement.setString(4, etablissement.getDescription());
            preparedStatement.setBigDecimal(5, etablissement.getLongitude());
            preparedStatement.setBigDecimal(6, etablissement.getLatitude());
            preparedStatement.setInt(7, etablissement.getId());
            preparedStatement.executeUpdate();

        }
    }

    @Override

    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM etablissement WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Etablissement> afficher() throws SQLException {
        String query = "SELECT * FROM etablissement";
        Statement ste = cnx.
                createStatement();
        ResultSet res = ste.executeQuery(query);
        List<Etablissement> list = new ArrayList<>();
            while (res.next()) {
                Etablissement etablissement = new Etablissement();
                etablissement.setId(res.getInt("id"));
                etablissement.setName(res.getString("name"));
                etablissement.setTypee(res.getString("type"));
                etablissement.setLocation(res.getString("location"));
                etablissement.setDescription(res.getString("description"));
                etablissement.setLongitude(res.getBigDecimal("latitude"));
                etablissement.setLatitude(res.getBigDecimal("longitude"));
                list.add(etablissement);
            }
        return list;
    }

    public List<Etablissement> rechercheDinamyque(String recherche) throws SQLException {
        String sql = "SELECT * FROM ETABLISSEMENTS WHERE ID_Etablissement LIKE ? OR Nom LIKE ? OR Category LIKE ? OR Prix LIKE ? OR Stock LIKE ? OR Description LIKE ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            for (int i = 1; i <= 6; i++) {
                String searchValue = "%" + recherche + "%";
                if (i == 4 || i == 1 || i == 5) { // Si c'est la colonne est un nombre (Prix ou Stock)
                    try {
                        int number = Integer.parseInt(recherche);
                        statement.setInt(i, number);
                    } catch (NumberFormatException e) {
                        // La recherche n'est pas un nombre, donc ignorez cette colonne pour la recherche
                        statement.setString(i, searchValue);
                    }
                } else {
                    statement.setString(i, searchValue);
                }
            }

            ResultSet resultSet = statement.executeQuery();
            ObservableList<Etablissement> filteredList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Etablissement etablissement = new Etablissement();
                etablissement.setId(resultSet.getInt("id"));
                etablissement.setName(resultSet.getString("name"));
                etablissement.setTypee(resultSet.getString("type"));
                etablissement.setLocation(resultSet.getString("location"));
                etablissement.setDescription(resultSet.getString("description"));
                etablissement.setLongitude(resultSet.getBigDecimal("longitude"));
                etablissement.setLatitude(resultSet.getBigDecimal("latitude"));

                filteredList.add(etablissement);
            }
            return filteredList;
        }
    }



}
