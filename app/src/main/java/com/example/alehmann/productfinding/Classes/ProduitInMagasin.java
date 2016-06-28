package com.example.alehmann.productfinding.Classes;

import java.io.Serializable;

/**
 * Created by hydrog3n on 21/06/2016.
 */
public class ProduitInMagasin {

    private float prix;

    private Id id;

    public ProduitInMagasin(float prix, Produit produit, Magasin mag) {
        this.prix = prix;
        this.setId(new Id(produit, mag));
    }
    public ProduitInMagasin(Produit produit, Magasin mag) {
        this.setId(new Id(produit, mag));
    }
    public ProduitInMagasin() {

    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }


    public class Id implements Serializable {

        private Produit produit;
        private Magasin magasin;

        public Id(Produit p, Magasin mag) {
            produit = p;
            magasin = mag;
        }

        public Produit getProduit() {
            return produit;
        }

        public void setProduit(Produit produit) {
            this.produit = produit;
        }

        public Magasin getMagasin() {
            return magasin;
        }

        public void setMagasin(Magasin magasin) {
            this.magasin = magasin;
        }
    }
}
