package tn.esprit.projetpifinal.models;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Departement {

    private int id;

    private String nom;

    private String description;

    private String chef_departement;

    private String service_offerts;
    private String localisation;

    private Etablissement etablissement;

    public Departement(String nom, String description, String chefDepartement, String servicesOfferts, String localisation) {
        this.nom= nom;
        this.description = description;
        this.chef_departement =chefDepartement;
        this.service_offerts = servicesOfferts;
        this.localisation = localisation;

    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Departement(String nom, String description, String chef_departement, String service_offerts, String localisation, Etablissement etablissement) {
        this.nom= nom;
        this.description = description;
        this.chef_departement =chef_departement;
        this.service_offerts = service_offerts;
        this.localisation = localisation;
        this.etablissement = etablissement;

    }

    public Departement() {

    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getChef_departement() {
        return chef_departement;
    }

    public String getService_offerts() {
        return service_offerts;
    }

    public String getLocalisation() {
        return localisation;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChef_departement(String chef_departement) {
        this.chef_departement = chef_departement;
    }

    public void setService_offerts(String service_offerts) {
        this.service_offerts = service_offerts;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    // toString() method
    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", chef_departement='" + chef_departement + '\'' +
                ", service_offerts='" + service_offerts + '\'' +
                ", localisation='" + localisation + '\'' +
                '}';
    }
}

