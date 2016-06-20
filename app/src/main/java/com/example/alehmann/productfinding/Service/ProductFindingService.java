package com.example.alehmann.productfinding.Service;


import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Produit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductFindingService {
    @GET("magasin")
    Call<List<Magasin>> listMagasin();

    @GET("magasin/{id}")
    Call<Magasin> thisMagasin(@Path("id") String id);

    @GET("magasin/{id}/produits/")
    Call<List<Produit>> getProduitMagasin(@Path("id") String id);

    @GET("produit/{id}")
    Call<Produit> thisProduit(@Path("id") String id);

    @POST("magasin/")
    Call<Magasin> createMagasin(@Body Magasin magasin);

    @POST("produit/magasin/{id}")
    Call<List<Produit>> searchProduitInMagasin(@Body String search, @Path("id") String id);

}
