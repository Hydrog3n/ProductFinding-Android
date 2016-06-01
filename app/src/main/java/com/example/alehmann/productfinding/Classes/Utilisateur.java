package com.example.alehmann.productfinding.Classes;

public class Utilisateur {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public Utilisateur(String username, String password, String firstname, String lastname){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public Utilisateur(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
