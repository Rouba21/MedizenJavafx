package tn.esprit.models;

import java.util.Date;
import java.util.List;

public class Topic {
    //attributs
    private int id ;
    private String titre ,contenu;
    private Date datedecreation ;
    private List<Publication> publications;

    // Constructeurs, getters et setters existants

    // Méthode pour ajouter une publication à la liste
    public void addPublication(Publication publication) {
        publications.add(publication);
    }

    //constructors

    public Topic(String titre, String contenu, Date datedecreation) {
        this.titre = titre;
        this.contenu = contenu;
        this.datedecreation = datedecreation;
    }

    public Topic(int id, String titre, String contenu, Date datedecreation) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.datedecreation = datedecreation;
    }

    public Topic() {

    }
    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatedecreation() {
        return datedecreation;
    }

    public void setDatedecreation(Date datedecreation) {
        this.datedecreation = datedecreation;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", datedecreation=" + datedecreation +
                '}';
    }


    public void setPublications(List<Publication> publications) {
    }
}
