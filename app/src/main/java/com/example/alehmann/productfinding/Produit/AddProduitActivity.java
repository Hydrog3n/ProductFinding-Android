package com.example.alehmann.productfinding.Produit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MeAmine on 15/06/2016.
 */
public class AddProduitActivity extends AppCompatActivity {
    EditText descriptif_produit_editText;
    EditText marque_produit_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_produit);
        descriptif_produit_editText =   (EditText) findViewById(R.id.descriptif_produit_editText);
        marque_produit_editText     =   (EditText) findViewById(R.id.marque_produit_editText);
    }

    public void button_ajout(View button){
        String descProd       = descriptif_produit_editText.getText().toString();
        String marqueProd     = marque_produit_editText.getText().toString();
        String url = "http://dummyimage.com/600x400/000/fff";
        Produit prod = new Produit(descProd,marqueProd,url);
        Call<Produit> call = Service.getInstance().createProduit(prod);
        call.enqueue(new Callback<Produit>() {
            @Override
            public void onResponse(Call<Produit> call, Response<Produit> response) {
                //TODO
            }

            @Override
            public void onFailure(Call<Produit> call, Throwable t) {
                //TODO
            }

        });


    }

}
