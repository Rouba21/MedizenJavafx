package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    //db
    final String URL="jdbc:mysql://localhost:3306/rouba";
    final String USR="root";
    final String PWD="";
    //att
   private Connection cnx ;
   private static MaConnexion instance ;
   //constructor
    public MaConnexion(){
        try{
        cnx= DriverManager.getConnection(URL,USR,PWD);
        System.out.println("Connexion etablie avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if(instance==null)
            instance=new MaConnexion();
        return instance;
    }
}
