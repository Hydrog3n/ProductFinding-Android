package com.example.alehmann.productfinding.Classes;

public class Magasin {

    private long id;
    private String name;
    private String address;
    private String cp;
    private String ville;
    private String logoUrl;

    public Magasin(String name, String address, String cp, String ville) {
        this.name = name;
        this.address = address;
        this.cp = cp;
        this.ville = ville;

    }
    public Magasin(long id, String name, String address, String cp, String ville) {
        this(name,address,cp,ville);
        this.id = id;

    }
    public Magasin(String name, String address, String cp, String ville,String url) {

        this(name,address,cp,ville);
        this.logoUrl = url;

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
