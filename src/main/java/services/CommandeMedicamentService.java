package services;

import entites.Commande;
import entites.CommandeMedicament;
import entites.Medicament;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeMedicamentService implements ICommandeMedicamentService {
    private final Connection myconnex = MyConnection.getInstance().getConnection();

    @Override
    public int ajouterCommandeMedicament(CommandeMedicament commandeMedicament) {
        try {
            String req = "INSERT INTO `CommandeMedicament` (`commande_id`, `medicament_id`, `quantityOrdered`, `dateOrdered`) VALUES (?,?,?,?)";
            PreparedStatement pstm = myconnex.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, commandeMedicament.getCommande().getId());
            pstm.setInt(2, commandeMedicament.getMedicament().getId());
            pstm.setInt(3, commandeMedicament.getQuantityOrdered());
            pstm.setDate(4, new java.sql.Date(commandeMedicament.getDateOrdered().getTime()));

            pstm.executeUpdate();

            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean modifierCommandeMedicament(CommandeMedicament commandeMedicament) {
        try {
            String req = "UPDATE `CommandeMedicament` SET `quantityOrdered` = ? WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setInt(1, commandeMedicament.getQuantityOrdered());
            pstm.setInt(2, commandeMedicament.getId());

            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean supprimerCommandeMedicament(CommandeMedicament commandeMedicament) {
        try {
            String req = "DELETE FROM `CommandeMedicament` WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setInt(1, commandeMedicament.getId());
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CommandeMedicament> getCommandeMedicamentByCommande(Commande commande) {
        List<CommandeMedicament> commandeMedicaments = new ArrayList<>();
        try {
            String query = "SELECT * FROM `CommandeMedicament` WHERE `commande_id` = ?";
            PreparedStatement stmt = myconnex.prepareStatement(query);
            stmt.setInt(1, commande.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CommandeMedicament commandeMedicament = createCommandeMedicamentFromResultSet(rs);
                commandeMedicaments.add(commandeMedicament);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandeMedicaments;
    }

    @Override
    public List<CommandeMedicament> getCommandeMedicamentByMedicament(Medicament medicament) {
        List<CommandeMedicament> commandeMedicaments = new ArrayList<>();
        try {
            String query = "SELECT * FROM `CommandeMedicament` WHERE `medicament_id` = ?";
            PreparedStatement stmt = myconnex.prepareStatement(query);
            stmt.setInt(1, medicament.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CommandeMedicament commandeMedicament = createCommandeMedicamentFromResultSet(rs);
                commandeMedicaments.add(commandeMedicament);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandeMedicaments;
    }

    private CommandeMedicament createCommandeMedicamentFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int commandeId = rs.getInt("commande_id");
        int medicamentId = rs.getInt("medicament_id");
        int quantityOrdered = rs.getInt("quantityOrdered");
        Date dateOrdered = rs.getDate("dateOrdered");

        CommandeService commandeService = new CommandeService();
        Commande commande = commandeService.getCommandeById(commandeId);

        MedicamentService medicamentService = new MedicamentService();
        Medicament medicament = medicamentService.getMedicamentById(medicamentId);

        return new CommandeMedicament(id, commande, medicament, quantityOrdered, dateOrdered);
    }
}
