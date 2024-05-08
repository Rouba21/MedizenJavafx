package tn.esprit.models;


import java.sql.Date;

public class Commentaire {
    //attributs
    private int id, nbrlikes, nbrdislikes;
    private String nom, contenu;
    private Date datedecreation;
    private Publication publication;
    private User user;
    //constructors

    public Commentaire(int id, int nbrlikes, int nbrdislikes, String contenu, String nom, Date datedecreation, Publication publication,User user) {
        this.id = id;
        this.nbrlikes = nbrlikes;
        this.nbrdislikes = nbrdislikes;
        this.contenu = contenu;
        this.nom = nom;
        this.datedecreation = datedecreation;
        this.publication = publication;
        this.user =user;
    }

    public Commentaire(int nbrlikes, int nbrdislikes, String contenu, String nom, Date datedecreation, Publication publication,User user) {
        this.nbrlikes = nbrlikes;
        this.nbrdislikes = nbrdislikes;
        this.contenu = contenu;
        this.nom = nom;
        this.datedecreation = datedecreation;
        this.publication = publication;
        this.user =user;
    }

    public Commentaire() {

    }
    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrlikes() {
        return nbrlikes;
    }

    public void setNbrlikes(int nbrlikes) {
        this.nbrlikes = nbrlikes;
    }

    public int getNbrdislikes() {
        return nbrdislikes;
    }

    public void setNbrdislikes(int nbrdislikes) {
        this.nbrdislikes = nbrdislikes;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDatedecreation() {
        return datedecreation;
    }

    public void setDatedecreation(Date datedecreation) {
        this.datedecreation = datedecreation;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //display

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", nbrlikes=" + nbrlikes +
                ", nbrdislikes=" + nbrdislikes +
                ", nom='" + nom + '\'' +
                ", contenu='" + contenu + '\'' +
                ", datedecreation=" + datedecreation +
                ", publication=" + publication +
                ", user=" + user +
                '}';
    }


}

