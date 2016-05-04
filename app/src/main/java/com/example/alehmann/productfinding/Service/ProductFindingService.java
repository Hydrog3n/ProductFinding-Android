package com.example.alehmann.productfinding.Service;


import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Utilisateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductFindingService {
    @GET("magasin")
    Call<List<Magasin>> listMagasin();

    @POST("magasin/")
    Call<Magasin> createMagasin(@Body Magasin magasin);

    @POST("utilisateur/")
    Call<Utilisateur> createUser(@Body Utilisateur utilisateur);
}
