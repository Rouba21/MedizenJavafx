package Medizen.Services;


import Medizen.Interfaces.IService;
import Medizen.Models.User;
import Medizen.Utils.MaConnexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class UserService implements IService<User>  {
    //att
    Connection cnx= MaConnexion.getInstance().getCnx();
    //actions

    @Override
    public void add(User user)  {
        String query = "INSERT INTO `user`(`email`, `password`,`roles`, `username`, `lastname`, `date_de_naissance`, `blocked`) VALUES (?, ?, ?, ?, ?,?, ?)";

        try {

            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, "[\""+user.getRoles()+"\"]");
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getLastname());
            statement.setDate(6, user.getDate_de_naissance());
            statement.setBoolean(7, user.isBlocked());

            statement.executeUpdate();

            System.out.println("User ajouté");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setuser(User afficherParEmail) {
    }


    @Override
    public void delete(User user) throws SQLException {
        String req = "DELETE FROM `user` WHERE `Id`=?";
        PreparedStatement ps= cnx.prepareStatement(req);
        ps.setInt(1, user.getId());
        ps.executeUpdate();
        System.out.println("Suppression réussie de l'utilisateur ");


    }


    @Override
    public void update(User user) throws SQLException {

        String req = "UPDATE `user` SET `email`=?, `password`=?, `roles`=?, `username`=?, `lastname`=?, `date_de_naissance`=?, `blocked`=? WHERE `id`=?";
        PreparedStatement statement = cnx.prepareStatement(req);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getRoles());
        statement.setString(4, user.getUsername());
        statement.setString(5, user.getLastname());
        statement.setDate(6, Date.valueOf(user.getDate_de_naissance().toLocalDate()));
        statement.setBoolean(7, user.isBlocked());
        statement.setInt(8, user.getId()); // Assuming user.getId() returns the user's ID
        statement.executeUpdate();
        System.out.println("User updated successfully");



    }

    @Override
    public List<User> Readall() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM `user`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            User u = new User();
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setUsername(rs.getString("username"));
            u.setLastname(rs.getString("lastname"));
            // Convert java.sql.Date to LocalDate
            java.sql.Date dbDate = rs.getDate("date_de_naissance");
            if (dbDate != null) {
                u.setDate_De_Naissance(dbDate.toLocalDate());
            }
            u.setBlocked(rs.getBoolean("blocked"));
            u.setRoles((rs.getString("roles"))); // Assuming roles are stored in JSON format
            users.add(u);
        }
        return users;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getOne(int id) {
        return null;
    }


    public User tryLogin(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement ps = this.cnx.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("role"),
                            rs.getString("username"),
                            rs.getString("lastname"),
                            rs.getObject("date_de_naissance", LocalDate.class),
                            rs.getBoolean("blocked")
                    );
                }
            }
        }
        return null;
    }

    public boolean tryLogin1(String username) throws SQLException {
        String sql = "SELECT username FROM user WHERE username = ?";
        try (PreparedStatement ps = this.cnx.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public User userExist(Integer id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement ps = this.cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("role"),
                            rs.getString("username"),
                            rs.getString("lastname"),
                            rs.getObject("date_de_naissance", LocalDate.class),
                            rs.getBoolean("blocked")
                    );
                }
            }
        }
        return null;
    }

    public boolean tryLogin2(String email) throws SQLException {
        String sql = "SELECT email FROM user WHERE email = ?";
        PreparedStatement ps = this.cnx.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        boolean exist = rs.next(); // Check if the result set has any rows
        if (exist) {
            User us = new User();
            us.setUsername(rs.getString("email"));
        }
        return exist;

    }

    public User tryLogin3(String numTel) throws SQLException {
        String sql = "SELECT email FROM user WHERE numTel = ?";
        try (PreparedStatement ps = this.cnx.prepareStatement(sql)) {
            ps.setString(1, numTel);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            null, // Not specified in the SQL query
                            null, // Not specified in the SQL query
                            null, // Not specified in the SQL query
                            null, // Not specified in the SQL query
                            false // Not specified in the SQL query
                    );
                }
            }
        }
        return null;
    }

    public List<User> join() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT user.id, user.email, user.role, user.username, user.lastname, user.date_de_naissance, user.blocked, codepromo.idUser FROM user INNER JOIN codePromo ON user.id = codepromo.idUser";
        try (Statement st = this.cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("lastname"),
                        rs.getObject("date_de_naissance", LocalDate.class),
                        rs.getBoolean("blocked")
                ));
            }
        }
        return users;
    }

    public User tryLoginByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement ps = this.cnx.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("role"),
                            rs.getString("username"),
                            rs.getString("lastname"),
                            rs.getObject("date_de_naissance", LocalDate.class),
                            rs.getBoolean("blocked")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }




    public void modifierMdp(User user, String mdp) throws SQLException {
        String req="UPDATE `user` SET `mdp`=? WHERE pseudo=?";
        PreparedStatement ps= cnx.prepareStatement(req);
        ps.setString(1, mdp);
        ps.setString(2, user.getEmail());
        ps.executeUpdate();
        System.out.println("Personne modifie");
    }

    public List<User> afficherParRole(String Roles) {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM `user` WHERE `role`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, Roles);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(
                        rs.getInt("ID"),
                        rs.getString("EMAIL"),
                        rs.getString("ROLE"),
                        rs.getString("USERNAME"),
                        rs.getString("LASTNAME"),
                        rs.getDate("DATE_DE_NAISSANCE").toLocalDate(),
                        rs.getBoolean("BLOCKED")
                );
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    public List<User> recherche(User user) {
        List<User> users = new ArrayList<>(); // Initialize the list of users
        String req = "SELECT * FROM `user` WHERE email=? OR password=? OR roles=? OR username=? OR lastname=? OR date_de_naissance=? OR blocked=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRoles());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getLastname());
            ps.setDate(6, Date.valueOf(user.getDate_de_naissance().toLocalDate()));
            ps.setBoolean(7, user.isBlocked());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User(
                        rs.getString("EMAIL"),
                        rs.getString("ROLES"),
                        rs.getString("PASSWORD"),
                        rs.getString("USERNAME"),
                        rs.getString("LASTNAME"),
                        rs.getDate("DATE_DE_NAISSANCE").toLocalDate(),
                        rs.getBoolean("BLOCKED")
                );
                users.add(u);
            }
            rs.close(); // Close ResultSet
            ps.close(); // Close PreparedStatement
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }



}