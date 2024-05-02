
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
    String url="jdbc:mysql://localhost:3306/pidev";
    String login="root";
    String password="";
    Connection myconnex;
    static MyConnection moncnx;
    public MyConnection() {
        try{
            //établir une connexion bd
            myconnex= DriverManager.getConnection(url,login,password);
            System.out.println("reussi!!");

        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static MyConnection getInstance(){
        if(moncnx==null)
            moncnx= new MyConnection();
        return moncnx;
    }
    public Connection getConnection(){
        return myconnex;
    }

}
