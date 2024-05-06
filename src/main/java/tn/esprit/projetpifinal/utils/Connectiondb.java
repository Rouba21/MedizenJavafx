package tn.esprit.projetpifinal.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectiondb {
    private static Connectiondb instance ;

    private final String URL="jdbc:mysql://127.0.0.1:3306/rouba";
    private final String USERNAME ="root";
    private final String PASSWORD ="";
    private Connection cnx ;

    private Connectiondb (){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("  " +
                    "Connected ... ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" ___ Connection Failed ___");
        }
    }


    public static Connectiondb getInstance() {
        if(instance == null)
            instance = new Connectiondb();

        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
