package services;

import entites.Commande;
import utils.MyConnection;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeService {

    private final Connection myconnex = MyConnection.getInstance().getConnection();

    public int ajouterCommande(Commande commande) {
        try {
            String req = "INSERT INTO `Commande` (`totalprice`, `quantity_ordered`, `date_ordered`, `status`) VALUES (?,?,?,?)";
            PreparedStatement pstm = myconnex.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);

            // Set the values of the parameters in the prepared statement
            pstm.setDouble(1, commande.getTotalPrice());
            pstm.setInt(2, commande.getQuantityOrdered());
            pstm.setDate(3, new java.sql.Date(commande.getDateOrdered().getTime()));
            pstm.setString(4, commande.getStatus());

            // Execute the SQL query to insert the command into the database
            pstm.executeUpdate();

            // Retrieve the generated keys (if any)
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Return the generated command ID
                return generatedKeys.getInt(1);
            }

        } catch (SQLException ex) {
            // Print the stack trace for debugging purposes
            ex.printStackTrace();
        }
        // Return -1 if the insertion fails
        return -1;
    }


    public boolean modifierCommande(Commande commande) {
        try {
            String req = "UPDATE `Commande` SET `totalPrice` = ?, `quantityOrdered` = ?, `dateOrdered` = ?, `status` = ? WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setDouble(1, commande.getTotalPrice());
            pstm.setInt(2, commande.getQuantityOrdered());
            pstm.setDate(3, new java.sql.Date(commande.getDateOrdered().getTime()));
            pstm.setString(4, commande.getStatus());
            pstm.setInt(5, commande.getId());

            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean supprimerCommande(Commande commande) {
        try {
            String req = "DELETE FROM `Commande` WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setInt(1, commande.getId());
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Commande getCommandeById(int id) {
        Commande commande = null;
        try {
            String query = "SELECT * FROM `Commande` WHERE `id` = ?";
            PreparedStatement stmt = myconnex.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                commande = createCommandeFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commande;
    }

    public List<Commande> searchCommandeByStatus(String status) {
        List<Commande> commandes = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Commande` WHERE `status` LIKE ?";
            PreparedStatement ps = myconnex.prepareStatement(req);
            ps.setString(1, "%" + status + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commande commande = createCommandeFromResultSet(rs);
                commandes.add(commande);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandes;
    }

    public List<Commande> afficherCommandes() {
        List<Commande> commandes = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Commande`";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Commande commande = createCommandeFromResultSet(rs);
                commandes.add(commande);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandes;
    }

    private Commande createCommandeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double totalPrice = rs.getDouble("totalPrice");
        int quantityOrdered = rs.getInt("quantityOrdered");
        Date dateOrdered = rs.getDate("dateOrdered"); // Get Date from ResultSet
        String status = rs.getString("status");


        LocalDate localDateOrdered = Instant.ofEpochMilli(dateOrdered.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return new Commande(id, totalPrice, quantityOrdered, dateOrdered, status);
    }
}
