package com.example.alehmann.productfinding.Service;


import com.example.alehmann.productfinding.Classes.Magasin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductFindingService {
    @GET("magasin")
    Call<List<Magasin>> listMagasin();
}
