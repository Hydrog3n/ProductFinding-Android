package com.example.alehmann.productfinding.Service;

import com.example.alehmann.productfinding.Classes.OpenProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hydrog3n on 22/06/2016.
 */
public interface OpenFoodService {

    @GET("{ean}.json")
    Call<OpenProduct> getProduct(@Path("ean") String ean);
}
