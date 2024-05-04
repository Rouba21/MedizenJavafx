package tn.MediZen.models;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class Reservation {

    private int id, mobile;
    private String name, surname, problemDescription, address;
    private String status = "Pending";
    private final LocalDate reservationDate;

    private Docteur docteur;
    private int doctor_id;

    public Reservation(int id, String name, String surname, int mobile, String problemDescription, String address, String status, LocalDate reservationDate, int doctor_id) {
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.reservationDate = reservationDate;
        this.status = status;
        this.doctor_id = doctor_id;
    }

    public Reservation(int mobile, String name, String surname, String problemDescription, String address, String status, LocalDate reservationDate, Docteur docteur) {
        this.mobile = mobile;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.status = status;
        this.reservationDate = reservationDate;
        this.docteur = docteur;
        this.doctor_id = docteur.getId();
    }

    public Reservation(int mobile, String name, String surname, String problemDescription, String address, String status, LocalDate reservationDate, int doctor_id) {
        this.mobile = mobile;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.status = status;
        this.reservationDate = reservationDate;
        this.docteur = new Docteur();
        this.docteur.setId(doctor_id);
    }

    public Reservation(String name, String surname, int mobile, String problemDescription, String address, String status, LocalDate reservationDate, Docteur docteur) {

        this.mobile = mobile;
        this.status = status;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.reservationDate = reservationDate;
        this.docteur = new Docteur();
        this.docteur.setId(doctor_id);
    }

    public Reservation(String name, String surname, String problemDescription, int mobile, LocalDate reservationDate, String status, String address) {
        this.mobile = mobile;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.reservationDate = reservationDate;
        this.status = status;
        this.docteur = new Docteur();
        this.docteur.setId(doctor_id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        LocalDate reservationDate1 = this.reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public Docteur getDocteur() {
        return docteur;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setDocteur(Docteur docteur) {
        this.docteur = docteur;
        this.doctor_id = docteur.getId();
    }

    public void setStatus(String status) {
        if (status.equals("pending") || status.equals("rejected") || status.equals("accepted")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status value. Status must be one of: Pending, Rejected, Accepted");
        }
    }


    @Override
    public String toString() {
        return "Name: " + name + ", Surname: " + surname + ", " +
                "Mobile: " + mobile + ", " +
                "Status: " + status + ", Address: " + address   + ", " +
                "Problem Description: " + problemDescription + ", " +
                "Reservation Date: " + reservationDate + ", " +
                "Doctor ID: " + doctor_id;
    }

    public ListCell<Reservation> call(ListView<Reservation> param) {
        return new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.toString());
                    Button editButton = new Button("Edit");
                    editButton.setOnAction(event -> {
                        // Handle edit action here
                    });
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        // Handle delete action here
                    });
                    HBox buttonsContainer = new HBox(editButton, deleteButton);
                    setGraphic(buttonsContainer);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }
}
