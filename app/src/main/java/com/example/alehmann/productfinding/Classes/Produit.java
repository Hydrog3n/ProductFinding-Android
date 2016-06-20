package com.example.alehmann.productfinding.Classes;

public class Produit {
    private int id;
    private String descriptif;
    private String marque;
    private String imageUrl;

    public Produit(String desc, String ma){
        this.descriptif = desc;
        this.marque = ma;
    }
    public Produit(String desc, String ma, String img){
        this(desc,ma);
        this.imageUrl = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
