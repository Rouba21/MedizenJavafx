package tn.esprit.services;

import tn.esprit.models.Pack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackService {

    public List<Pack> getAllPacksForSponseur(int sponseurId) throws SQLException {
        List<Pack> packs = new ArrayList<>();

        // Établir une connexion à la base de données (exemple avec JDBC)
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rouba");

        // Préparer la requête SQL pour récupérer les packs liés à un sponsor spécifique
        String query = "SELECT p.id, p.type " +
                "FROM pack p " +
                "INNER JOIN sponseur s ON s.pack = p.type " +
                "WHERE s.id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, sponseurId); // Définir le paramètre sponseurId dans la requête SQL

        // Exécuter la requête SQL
        ResultSet resultSet = statement.executeQuery();

        // Parcourir les résultats et créer des objets Pack
        while (resultSet.next()) {
            int packId = resultSet.getInt("id");
            String type = resultSet.getString("type");

            Pack pack = new Pack();
            pack.setId(packId);
            pack.setType(type);

            packs.add(pack);
        }

        // Fermer les ressources (connexion, statement, resultSet)
        resultSet.close();
        statement.close();
        connection.close();

        return packs;
    }


}
