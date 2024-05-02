package tn.esprit.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

public class Sponseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //att
    private Integer id;
    private String nom;
    private String email;
    private String logo;
    private String pack;
    private Integer numero;


    //constructor
    @ManyToMany(mappedBy = "sponseurs")
    private Set<Event> events = new HashSet<>();

    public Sponseur() {
    }

    public Sponseur(Integer id, String nom, String email, Integer numero,String logo, String pack ) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numero = numero;
        this.logo = logo;
        this.pack = pack;

    }

    public Sponseur(String nom, String email,Integer numero, String logo, String pack ) {
        this.nom = nom;
        this.email = email;
        this.numero = numero;
        this.logo = logo;
        this.pack = pack;

    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Sponseur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", logo='" + logo + '\'' +
                ", pack='" + pack + '\'' +
                ", numero=" + numero +
                '}';
    }

    public void setData(String path) {
    }
}
