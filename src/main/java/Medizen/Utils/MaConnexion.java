package Medizen.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MaConnexion {

    //DB
    final String URL = "jdbc:mysql://localhost:3306/rouba";
    final String USR = "root";
    final String PWD = "";

    //att
    private Connection cnx;
    private static MaConnexion instance;

    //constructor
    //Singelton step 1
    public MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("connexion etablie avec succes");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //getter&setter

    public static MaConnexion getInstance() {
        if(instance == null)
            instance=new MaConnexion();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }


}

