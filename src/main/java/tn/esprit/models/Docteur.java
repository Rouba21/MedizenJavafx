package tn.esprit.models;

public class Docteur {
    private String nom, prenom, mail, addresse, specialite, experience;
    private int id, mobile, reservationId;

    public Docteur(String nom, String prenom, String mail, String addresse, String specialite, String experience, int mobile) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.addresse = addresse;
        this.specialite = specialite;
        this.experience = experience;
        this.mobile = mobile;
    }

    public Docteur() {
    }


    public Docteur(int id, String nom, String prenom, String mail, String experience, int mobile, String addresse, String specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.addresse = addresse;
        this.specialite = specialite;
        this.experience = experience;
        this.mobile = mobile;
    }

    public Docteur(int id, String nom, String prenom, String mail, String experience, int mobile, String addresse, String specialite, int reservationId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.addresse = addresse;
        this.specialite = specialite;
        this.experience = experience;
        this.mobile = mobile;
        this.reservationId = reservationId;
    }

    public Docteur(String text, String text1, String text2, String text3, String text4, String text5, String text6) {
    }


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



    @Override
    public String toString() {
        return "Docteur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", addresse='" + addresse + '\'' +
                ", specialite='" + specialite + '\'' +
                ", experience='" + experience + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
