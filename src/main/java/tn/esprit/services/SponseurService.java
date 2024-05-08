package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Sponseur;
import tn.esprit.models.getData;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class SponseurService implements IService<Sponseur> {
    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public int add(Sponseur sponseur) throws SQLException {
        String query = "INSERT INTO sponseur (nom, email, numero, logo, pack) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, sponseur.getNom());
            preparedStatement.setString(2, sponseur.getEmail());
            preparedStatement.setInt(3, sponseur.getNumero());
            String uri = getData.path;
            uri = uri.replace("\\", "\\\\");
            preparedStatement.setString(4, uri);
            preparedStatement.setString(5, sponseur.getPack());

            preparedStatement.executeUpdate();

        }

        return 0;
    }

    // Méthode pour vérifier si un sponsor existe déjà avec le nom spécifié
    public boolean sponseurExists(String nom) throws SQLException {
        String query = "SELECT COUNT(*) FROM sponseur WHERE nom = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, nom);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Si le nombre de résultats est supérieur à 0, le sponsor existe
                }
            }
        }
        return false;
    }

    @Override
    public void update(Sponseur sponseur) throws SQLException {
        String req = "update sponseur set nom=?,email=?,numero=?,logo=?,pack=? where id=?";
        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setString(1, sponseur.getNom());
        preparedStatement.setString(2, sponseur.getEmail());
        preparedStatement.setInt(3, sponseur.getNumero());
        preparedStatement.setString(4, sponseur.getLogo());
        preparedStatement.setString(5, sponseur.getPack());
        preparedStatement.setInt(6, sponseur.getId());
        preparedStatement.executeUpdate();
        System.out.println("updated");

    }


    @Override
    public List<Sponseur> afficher() throws SQLException {
        List<Sponseur> sponseurs = new ArrayList<>();
        String req = "select * from sponseur";
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Sponseur sponseur = new Sponseur();
                sponseur.setId(rs.getInt("id"));
                sponseur.setNom(rs.getString("nom"));
                sponseur.setEmail(rs.getString("email"));
                sponseur.setNumero(rs.getInt("numero"));
                sponseur.setLogo(rs.getString("logo"));
                sponseur.setPack(rs.getString("pack"));
                sponseurs.add(sponseur);
            }
        }
        return sponseurs;
    }


    @Override
    public void delete(int id) throws SQLException {
        String req = "delete from sponseur where id=" + id;
        Statement statement = cnx.createStatement();
        statement.executeUpdate(req);
    }


    public List<Sponseur> getAllSponseurs() throws SQLException {
        List<Sponseur> sponsors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = MaConnexion.getInstance().getCnx(); // Obtenir la connexion à la base de données
            String query = "SELECT * FROM sponseur";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Sponseur sponsor = new Sponseur();
                sponsor.setId(resultSet.getInt("id"));
                sponsor.setNom(resultSet.getString("nom"));
                sponsor.setEmail(resultSet.getString("email"));
                sponsor.setLogo(resultSet.getString("logo"));
                sponsor.setPack(resultSet.getString("pack"));
                sponsor.setNumero(resultSet.getInt("numero"));
                sponsors.add(sponsor);
            }
        } finally {
            // Fermer les ressources JDBC (resultSet, statement, connection) ici
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            // La connexion sera fermée par le gestionnaire de connexion (MaConnexion)
        }

        return sponsors;
    }

    @Override
    public List<?> getAllEvents() throws SQLException {
        return null;
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

    @Override
    public List<Sponseur> getSponseursByEvent(int eventId) throws SQLException {
        return null;
    }

    @Override
    public void ajouterAssociationEvenementSponseur(int eventId, int sponseurId) throws SQLException {

    }
    public List<Sponseur> getSponsorsByPackGold() throws SQLException {
        List<Sponseur> sponsors = new ArrayList<>();
        String query = "SELECT * FROM sponseur WHERE pack = 'Or'";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Sponseur sponsor = new Sponseur();
                sponsor.setId(resultSet.getInt("id"));
                sponsor.setNom(resultSet.getString("nom"));
                sponsor.setEmail(resultSet.getString("email"));
                sponsor.setNumero(resultSet.getInt("numero"));
                sponsor.setLogo(resultSet.getString("logo"));
                sponsor.setPack(resultSet.getString("pack"));
                sponsors.add(sponsor);
            }
        }
        return sponsors;
    }
    public List<Sponseur> getSponsorsByPackArgent() throws SQLException {
        List<Sponseur> sponsors = new ArrayList<>();
        String query = "SELECT * FROM sponseur WHERE pack = 'Argent'";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Sponseur sponsor = new Sponseur();
                sponsor.setId(resultSet.getInt("id"));
                sponsor.setNom(resultSet.getString("nom"));
                sponsor.setEmail(resultSet.getString("email"));
                sponsor.setNumero(resultSet.getInt("numero"));
                sponsor.setLogo(resultSet.getString("logo"));
                sponsor.setPack(resultSet.getString("pack"));
                sponsors.add(sponsor);
            }
        }
        return sponsors;
    }
    public List<Sponseur> getSponsorsByPackBronze() throws SQLException {
        List<Sponseur> sponsors = new ArrayList<>();
        String query = "SELECT * FROM sponseur WHERE pack = 'Bronze'";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Sponseur sponsor = new Sponseur();
                sponsor.setId(resultSet.getInt("id"));
                sponsor.setNom(resultSet.getString("nom"));
                sponsor.setEmail(resultSet.getString("email"));
                sponsor.setNumero(resultSet.getInt("numero"));
                sponsor.setLogo(resultSet.getString("logo"));
                sponsor.setPack(resultSet.getString("pack"));
                sponsors.add(sponsor);
            }
        }
        return sponsors;
    }




}
