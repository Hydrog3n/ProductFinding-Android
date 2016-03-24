package com.example.alehmann.productfinding.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static Service instance;
    public static ProductFindingService service;

    private Service() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://productfinding.heroku.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ProductFindingService.class);
    }

    public static ProductFindingService getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance.service;
    }

    private static Service createInstance() {
       return new Service();
    }
}
