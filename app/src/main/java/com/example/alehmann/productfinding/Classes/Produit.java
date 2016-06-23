package com.example.alehmann.productfinding.Classes;

import java.io.Serializable;

public class Produit implements Serializable {
    private Long id;
    private String descriptif;
    private String marque;
    private String imageUrl;
    private String ean;

    private float prix;

    public Produit(String descProd, String marqueProd, String url) {
        descriptif = descProd;
        marque = marqueProd;
        imageUrl = url;
    }

    public Produit(String descProd, String marqueProd, String url, String codebarre) {
        this(descProd, marqueProd, url);
        ean = codebarre;
    }


    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

   public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
