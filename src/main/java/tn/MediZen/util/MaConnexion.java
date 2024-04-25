package tn.MediZen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    final String URL="jdbc:mysql://localhost:3306/medizentest";
    final  String USR="root";
    final  String PWD="";

    //att
    private Connection cnx;
    static MaConnexion instance;
    //cons

    public MaConnexion() throws SQLException {
        try {
            cnx = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("Connexion établie!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if(instance == null) {
            try {
                instance = new MaConnexion();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
