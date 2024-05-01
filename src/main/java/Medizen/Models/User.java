package Medizen.Models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class User {
    private int id;
    private String email;
    private String roles;
    private String password;
    private String username;
    private String lastname;
    private LocalDate date_de_naissance;
    private boolean blocked;


    public User(int id, String email, String roles, String username, String lastname, LocalDate date_de_naissance, boolean blocked) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.username = username;
        this.lastname = lastname;
        this.date_de_naissance = date_de_naissance;
        this.blocked = blocked;

    }

    public User(String email, String roles, String password, String username, String lastname, LocalDate date_de_naissance, boolean blocked) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.username = username;
        this.lastname = lastname;
        this.date_de_naissance = date_de_naissance;
        this.blocked = blocked;
    }

    public User() {

    }


    // Getters &setters


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", lastname='" + lastname + '\'' +
                ", date_de_naissance=" + date_de_naissance +
                ", blocked=" + blocked +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public Date getDate_de_naissance() {
        return Date.valueOf(date_de_naissance);
    }

    public void setDate_De_Naissance(LocalDate date_De_Naissance) {
        this.date_de_naissance = date_De_Naissance;
    }


    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


}