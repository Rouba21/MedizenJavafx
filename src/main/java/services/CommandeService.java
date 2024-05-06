package services;

import entites.Commande;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
public class CommandeService {
    private final Connection myconnex = MyConnection.getInstance().getConnection();

    public List<Commande> afficherCommandes() {
        List<Commande> commandes = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Commande`";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double totalPrice = rs.getDouble("totalprice");
                int quantityOrdered = rs.getInt("quantity_ordered");
                Date dateOrdered = rs.getDate("date_ordered");
                String status = rs.getString("status");
                Commande commande = new Commande(id, totalPrice, quantityOrdered, dateOrdered, status);
                commandes.add(commande);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandes;
    }

    public boolean modifierCommande(Commande commande) {
        try {
            String req = "UPDATE `Commande` SET `status` = ? WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setString(1, commande.getStatus());
            pstm.setInt(2, commande.getId());
            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
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
                int commandeId = rs.getInt("id");
                double totalPrice = rs.getDouble("totalprice");
                int quantityOrdered = rs.getInt("quantity_ordered");
                Date dateOrdered = rs.getDate("date_ordered");
                String status = rs.getString("status");
                commande = new Commande(commandeId, totalPrice, quantityOrdered, dateOrdered, status);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commande;
    }

    public int ajouterCommande(Commande commande) {
        try {
            String req = "INSERT INTO `Commande` (`totalprice`, `quantity_ordered`, `date_ordered`, `status`) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = myconnex.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pstm.setDouble(1, commande.getTotalPrice());
            pstm.setInt(2, commande.getQuantityOrdered());
            pstm.setDate(3, new java.sql.Date(commande.getDateOrdered().getTime()));
            pstm.setString(4, commande.getStatus());

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
    public void generateCommandesPDF(List<Commande> commandes, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("List of Commandes");

                int yOffset = 650;
                for (Commande commande : commandes) {
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("ID: " + commande.getId());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Total Price: " + commande.getTotalPrice());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Quantity Ordered: " + commande.getQuantityOrdered());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Date Ordered: " + commande.getDateOrdered());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Status: " + commande.getStatus());
                    contentStream.newLineAtOffset(0, -20);
                    yOffset -= 120;
                }

                contentStream.endText();
            }

            document.save(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
