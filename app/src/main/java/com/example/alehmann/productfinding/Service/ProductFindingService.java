package com.example.alehmann.productfinding.Service;


import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.Classes.ProduitInMagasin;
import com.example.alehmann.productfinding.Classes.Utilisateur;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductFindingService {
    //
    // REQUEST FOR MAGASIN
    //
    @GET("magasin")
    Call<List<Magasin>> listMagasin();

    @POST("magasin/")
    Call<Magasin> createMagasin(@Body Magasin magasin);

    @GET("magasin/{id}")
    Call<Magasin> thisMagasin(@Path("id") String id);

    @GET("magasin/{id}/produits/")
    Call<List<Produit>> getProduitMagasin(@Path("id") String id);


    //
    // REQUEST FOR UTILISATEUR
    //
    @POST("utilisateur/new")
    Call<Utilisateur> createUser(@Body Utilisateur utilisateur);

    @POST("utilisateur/")
    Call<Utilisateur> loginUser(@Body List<String> loginInfos);

    //
    // REQUEST FOR PRODUIT
    //
    @POST("produit/")
    Call<Produit> createProduit(@Body Produit produit);

    @POST("produit/link")
    Call<ProduitInMagasin> linkProduit(@Body ProduitInMagasin produitInMagasin);

    @GET("produit/{id}")
    Call<Produit> thisProduit(@Path("id") String id);

    @GET("produit/ean/{ean}")
    Call<Produit> checkProduit(@Path("ean") String ean);

    @POST("produit/magasin/{id}")
    Call<List<Produit>> searchProduitInMagasin(@Body String search, @Path("id") String id);

}
