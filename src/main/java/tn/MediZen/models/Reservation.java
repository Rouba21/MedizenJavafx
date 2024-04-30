package tn.MediZen.models;

import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {

    private int id, mobile;
    private String name, surname, problemDescription, address;
    private String status = "Pending";

    private LocalDate reservationDate;

    public Reservation(int id, String name, String surname, int mobile, String problemDescription, String address, String status, LocalDate reservationDate) {
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.reservationDate = reservationDate;
        this.status = status;
    }
    public Reservation() {

    }
    public Reservation( String name, String surname, int mobile, String problemDescription, String address, String status, LocalDate reservationDate) {

        this.mobile = mobile;
        this.status = status;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.reservationDate = reservationDate;

    }

    public Reservation(String name, String surname, String problemDescription, int mobile, LocalDate reservationDate , String status,String address) {
        this.mobile = mobile;
        this.name = name;
        this.surname = surname;
        this.problemDescription = problemDescription;
        this.address = address;
        this.reservationDate = reservationDate;
        this.status = status;
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


    public void setStatus(String status) {
        if (status.equals("Pending") || status.equals("Rejected") || status.equals("Accepted")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status value. Status must be one of: Pending, Rejected, Accepted");
        }
    }

    @Override
    public String toString() {
        return "Reservation:" +
                ", mobile:" + mobile +
                ", name:" + name + '\'' +
                ", surname:" + surname + '\'' +
                ", problemDescription:" + problemDescription + '\'' +
                ", address:" + address + '\'' +
                ", status:" + status + '\'' +
                ", reservationDate:" + reservationDate ;
    }
}
