package services;

import entites.Medicament;
import javafx.scene.image.Image;
import utils.MyConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.io.File;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javafx.scene.image.ImageView;

import java.nio.file.Files;
import java.io.ByteArrayOutputStream;

public class MedicamentService implements IMedicamentService {
    private final Connection myconnex = MyConnection.getInstance().getConnection();

    @Override
    public int ajouterMedicament(Medicament medicament) {
        try {
            String req = "INSERT INTO `Medicament` (`name`, `quantity`, `description`, `image`, `price`, `qr_code_path`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstm = myconnex.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, medicament.getName());
            pstm.setInt(2, medicament.getQuantity());
            pstm.setString(3, medicament.getDescription());
            pstm.setString(4, medicament.getImage());
            pstm.setDouble(5, medicament.getPrice());

            // Generate QR code and save it
            String qrCodePath = generateQRCode(medicament);
            pstm.setString(6, qrCodePath);

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

    private String generateQRCode(Medicament medicament) {
        String qrCodeFileName = medicament.getName() + ".png"; // QR code file name
        String qrCodePath = "C:/Users/21695/Downloads/" + qrCodeFileName; // Path to the saved QR code image

        try {
            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(medicament.getName(), BarcodeFormat.QR_CODE, 200, 200);

            // Save QR code to file
            Path path = new File(qrCodePath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return qrCodePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean modifierMedicament(Medicament medicament) {
        try {
            String req = "UPDATE `Medicament` SET `name` = ?, `quantity` = ?, `description` = ?, `image` = ?, `price` = ?, `qr_code_path` = ? WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setString(1, medicament.getName());
            pstm.setInt(2, medicament.getQuantity());
            pstm.setString(3, medicament.getDescription());
            pstm.setString(4, medicament.getImage());
            pstm.setDouble(5, medicament.getPrice());
            pstm.setString(6, medicament.getQrCodePath());
            pstm.setInt(7, medicament.getId());

            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Medicament> getMedicamentsWithLowQuantity(int threshold) {
        List<Medicament> medicamentsWithLowQuantity = new ArrayList<>();
        // Retrieve medicaments from the database or any other source
        List<Medicament> allMedicaments = afficherMedicaments(); // Assuming you have a method to get all medicaments
        // Check each medicament's quantity
        for (Medicament medicament : allMedicaments) {
            if (medicament.getQuantity() <= threshold) {
                medicamentsWithLowQuantity.add(medicament);
            }
        }
        return medicamentsWithLowQuantity;
    }

    @Override
    public boolean supprimerMedicament(Medicament medicament) {
        try {
            String req = "DELETE FROM `Medicament` WHERE `id` = ?";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            pstm.setInt(1, medicament.getId());
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Medicament getMedicamentById(int id) {
        Medicament medicament = null;
        try {
            String query = "SELECT * FROM `Medicament` WHERE `id` = ?";
            PreparedStatement stmt = myconnex.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                String qrCodePath = rs.getString("qr_code_path");
                medicament = new Medicament(id, name, quantity, description, image, price, qrCodePath);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return medicament;
    }

    @Override
    public List<Medicament> searchMedicamentByName(String name) {
        List<Medicament> medicaments = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Medicament` WHERE `name` LIKE ?";
            PreparedStatement ps = myconnex.prepareStatement(req);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                String qrCodePath = rs.getString("qr_code_path");
                Medicament medicament = new Medicament(id, name, quantity, description, image, price, qrCodePath);
                medicaments.add(medicament);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return medicaments;
    }

    @Override
    public List<Medicament> afficherMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Medicament`";
            PreparedStatement pstm = myconnex.prepareStatement(req);
            // Execute the SQL query to retrieve medications from the database
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                String qrCodePath = rs.getString("qr_code_path");
                Medicament medicament = new Medicament(id, name, quantity, description, image, price, qrCodePath);

                medicaments.add(medicament);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return medicaments;
    }
}
