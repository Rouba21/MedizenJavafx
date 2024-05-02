package tn.esprit.models;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Event {
    // Attributs
    private int id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private String lieu;
    private String description;
    private String imageURL;

    @ManyToMany
    @JoinTable(
            name = "event_sponseur",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "sponseur_id")
    )

    private Set<Sponseur> sponseurs = new HashSet<>();

    // Constructeur(s)
    public Event(int id, String titre, LocalDate dateDebut, LocalDate dateFin, String lieu, String description, String imageURL) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = convertToDate(dateDebut); // Conversion LocalDate vers Date
        this.dateFin = convertToDate(dateFin); // Conversion LocalDate vers Date
        this.lieu = lieu;
        this.description = description;
        this.imageURL = imageURL;
    }

    // Méthode pour convertir LocalDate en Date
    private Date convertToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public Event(String titre, java.sql.Date dateDebut, java.sql.Date dateFin, String lieu, String description, String imageURL) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.description = description;
        this.imageURL = imageURL;
    }


    // Getters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // Additional methods (optional)
    public String getFormattedDateDebut() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(dateDebut);
    }

    public String getFormattedDateFin() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(dateFin);
    }

    public Set<Sponseur> getSponseurs() {
        return sponseurs;
    }

    public void setSponseurs(Set<Sponseur> sponseurs) {
        this.sponseurs = sponseurs;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}