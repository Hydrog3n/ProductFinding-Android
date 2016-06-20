package com.example.alehmann.productfinding.Magasin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Produit.NewProduitActivity;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MeAmine on 04/05/2016.
 */
public class NewMagasinActivity extends AppCompatActivity {
    EditText nom_magasin_editText;
    EditText ville_magasin_editText;
    EditText cp_magasin_editText;
    EditText adresse_magasin_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_magasin);
        nom_magasin_editText       =   (EditText) findViewById(R.id.nom_magasin_editText);
        ville_magasin_editText     =   (EditText) findViewById(R.id.ville_magasin_editText);
        cp_magasin_editText        =   (EditText) findViewById(R.id.cp_magasin_editText);
        adresse_magasin_editText   =   (EditText) findViewById(R.id.adresse_magasin_editText);
    }

    public void button_ajout(View button){
        String nomMag       = nom_magasin_editText.getText().toString();
        String adresseMag   = adresse_magasin_editText.getText().toString();
        String villeMag     = ville_magasin_editText.getText().toString();
        String cpMag        = cp_magasin_editText.getText().toString();
        String url = "http://lorempixel.com/400/200";
        Magasin Mag = new Magasin(nomMag,adresseMag,cpMag,villeMag,url);
        Call<Magasin> call = Service.getInstance().createMagasin(Mag);
        call.enqueue(new Callback<Magasin>() {
            @Override
            public void onResponse(Call<Magasin> call, Response<Magasin> response) {
                Toast.makeText(getApplicationContext(), "Magasin ajouté avec succes", Toast.LENGTH_LONG);

            }

            @Override
            public void onFailure(Call<Magasin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Magasin n'a pas était ajouté", Toast.LENGTH_LONG);
            }
        });


    }

    public void button_addProd(View button){
        Intent i = new Intent(this, NewProduitActivity.class);
        startActivity(i);
    }
}
