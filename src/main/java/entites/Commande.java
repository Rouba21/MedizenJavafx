package entites;

import java.util.Date;

public class Commande {
    private int id;
    private double totalPrice;
    private int quantityOrdered;
    private Date dateOrdered;
    private String status;


    public Commande() {
    }


    public Commande(int id, double totalPrice, int quantityOrdered, Date dateOrdered, String status) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.quantityOrdered = quantityOrdered;
        this.dateOrdered = dateOrdered;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", quantityOrdered=" + quantityOrdered +
                ", dateOrdered=" + dateOrdered +
                ", status='" + status + '\'' +
                '}';
    }
}
