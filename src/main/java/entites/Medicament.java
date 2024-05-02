package entites;

public class Medicament {
    private int id;
    private String name;
    private int quantity;
    private String description;
    private String image;
    private double price;
    private String qrCodePath;

    public Medicament(int id, String name, int quantity, String description, String image, double price, String qrCodePath) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.price = price;
        this.qrCodePath = qrCodePath;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    // toString method
    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", qrCodePath='" + qrCodePath + '\'' +
                '}';
    }
}
