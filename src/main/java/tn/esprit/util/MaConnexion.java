package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/rouba?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Remplacez par votre mot de passe

    // Instance unique de la classe MaConnexion (Singleton)
    private static MaConnexion instance;
    private Connection cnx;

    // Constructeur privé pour empêcher l'instanciation directe
    private MaConnexion() {
        try {
            // Chargement du pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Établissement de la connexion
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion établie avec succès à la base de données.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            throw new RuntimeException("Impossible d'établir la connexion à la base de données.", e);
        }
    }

    // Méthode statique pour obtenir l'instance unique de MaConnexion (Singleton)
    public static MaConnexion getInstance() {
        if (instance == null) {
            instance = new MaConnexion();
        }
        return instance;
    }

    // Méthode pour récupérer la connexion à la base de données
    public Connection getCnx() {
        return cnx;
    }
}
