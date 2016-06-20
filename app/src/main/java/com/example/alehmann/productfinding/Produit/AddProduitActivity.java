package com.example.alehmann.productfinding.Produit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.Classes.ProduitInMagasin;
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
    EditText prix_produit_editText;

    Produit _produit;
    Magasin _magasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_produit);
        descriptif_produit_editText =   (EditText) findViewById(R.id.descriptif_produit_editText);
        marque_produit_editText     =   (EditText) findViewById(R.id.marque_produit_editText);
        prix_produit_editText       =   (EditText) findViewById(R.id.prix_produit_editText);

        _magasin = (Magasin) this.getIntent().getExtras().getSerializable("mag");
    }

    public void button_ajout(View button){
        String descProd       = descriptif_produit_editText.getText().toString();
        String marqueProd     = marque_produit_editText.getText().toString();
        String url = "http://dummyimage.com/600x400/000/fff";
        Produit prod = new Produit(descProd,marqueProd,url);

        //TODO Check si le produit n'existe pas. (Utiliser l'ean)
        Call<Produit> call = Service.getInstance().createProduit(prod);
        call.enqueue(new Callback<Produit>() {
            @Override
            public void onResponse(Call<Produit> call, Response<Produit> response) {
                _produit = response.body();
                linkProduitToMagasin();
            }

            @Override
            public void onFailure(Call<Produit> call, Throwable t) {
                Log.d("Debug/add produit", t.getMessage());
            }

        });


    }

    public void linkProduitToMagasin() {
        String prix = prix_produit_editText.getText().toString();

        ProduitInMagasin pim = new ProduitInMagasin(Float.valueOf(prix), _produit, _magasin);
        Call<ProduitInMagasin> call = Service.getInstance().linkProduit(pim);
        call.enqueue(new Callback<ProduitInMagasin>() {
            @Override
            public void onResponse(Call<ProduitInMagasin> call, Response<ProduitInMagasin> response) {
                Toast.makeText(getApplicationContext(), "Produit Ajouté", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<ProduitInMagasin> call, Throwable t) {
                Log.d("Debug/link produit", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
