package com.example.alehmann.productfinding.Magasin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Produit.AddProduitActivity;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.example.alehmann.productfinding.database.sqlite.MagasinManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MeAmine on 04/05/2016.
 */
public class AddMagasinActivity extends AppCompatActivity {
    private EditText nom_magasin_editText;
    private EditText ville_magasin_editText;
    private EditText cp_magasin_editText;
    private EditText adresse_magasin_editText;

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
        if (nom_magasin_editText.getText().toString().trim().length() == 0
                || ville_magasin_editText.getText().toString().trim().length() == 0
                || cp_magasin_editText.getText().toString().trim().length() == 0
                || adresse_magasin_editText.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Champ requis", Toast.LENGTH_LONG).show();
            return;
        }

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
                Toast.makeText(getApplicationContext(), "Magasin ajouté avec succes", Toast.LENGTH_LONG).show();
                returnMagasins();

            }

            @Override
            public void onFailure(Call<Magasin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Magasin n'a pas était ajouté", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void returnMagasins() {
        Intent r = new Intent(this, RecyclerMagasinActivity.class);
        startActivity(r);
    }
}
