package tn.esprit.projetpifinal.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Etablissement {

    private int id;

    private String name;

    private String typee;

    private String location;

    private String description;
    private BigDecimal longitude;

    private BigDecimal latitude;

    private List<Departement> departements;



    public Etablissement(String type, String description, String location, String name, BigDecimal latitude, BigDecimal longitude) {
        this.typee= type;
        this.name = name;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Etablissement() {

    }

    public Etablissement(int id,String type, String description, String location, String name, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.typee= type;
        this.name = name;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getTypee() {
        return typee;
    }

    public void setTypee(String typee) {
        this.typee = typee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + typee + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }






}