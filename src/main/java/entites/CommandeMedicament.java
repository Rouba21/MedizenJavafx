package entites;

import java.util.Date;

public class CommandeMedicament {
    private int id;
    private Commande commande;
    private Medicament medicament;
    private int quantityOrdered;
    private Date dateOrdered;

    public CommandeMedicament(int id, Commande commande, Medicament medicament, int quantityOrdered, Date dateOrdered) {
        this.id = id;
        this.commande = commande;
        this.medicament = medicament;
        this.quantityOrdered = quantityOrdered;
        this.dateOrdered = dateOrdered;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
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
}
