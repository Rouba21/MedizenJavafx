package tn.MediZen.models;

public class Docteur {
    private String nom, prenom, mail,addresse, specialite, experience;
    private int id, mobile;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }



    public Docteur(String nom, String prenom, String mail, String experience, int mobile, String addresse, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.experience = experience;
        this.mobile = mobile;
        this.addresse = addresse;
        this.specialite = specialite;

    }

    @Override
    public String toString() {
        return
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", experience='" + experience + '\'' +
                ", mobile=" + mobile +
                ", addresse='" + addresse + '\'' +
                ", specialite='" + specialite + '\'' +

                '}';
    }

    public Docteur() {
    }

    public Docteur(int id, String nom, String prenom, String mail, String experience, int mobile, String addresse, String specialite) {
    }
}
