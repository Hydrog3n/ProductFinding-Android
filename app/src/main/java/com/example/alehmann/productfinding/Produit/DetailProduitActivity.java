package com.example.alehmann.productfinding.Produit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduitActivity extends AppCompatActivity {

    private Produit prod;
    private TextView descProdTxV;
    private TextView marqueProdTxV;
    private TextView eanProdTxV;
    private ImageView imageProdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produit);

        //UI Content
        descProdTxV = (TextView) findViewById(R.id.text_view_Prod_Desc);
        marqueProdTxV = (TextView) findViewById(R.id.text_view_Prod_Marque);
        imageProdView = (ImageView) findViewById(R.id.image_prod_view);

        //TODO : Set EAN
        //eanProdTxV = (TextView) findViewById(R.id.text_view_Prod_Ean);

        //Set mag Name from bundle
        //Get bundle from RecyclerView and test content bundle
        Bundle objectBundle = this.getIntent().getExtras();

        if (objectBundle == null) {
            Toast toast = Toast.makeText(this, "ERROR bundle null", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        String id = this.getIntent().getStringExtra("IDPROD");
        //Load recycler view inside Detail magasin activity, passing the id for request
        //new RecyclerProduitAdapter(this, id);
        Call<Produit> callProduit = Service.getInstance().thisProduit(id);
        //Request retrofit to get a Magasin
        callProduit.enqueue(new Callback<Produit>() {

            @Override
            public void onResponse(Call<Produit> call, Response<Produit> response) {
                if (response.isSuccessful()) {
                    prod = response.body();
                    setData(prod);
                }
            }

            @Override
            public void onFailure(Call<Produit> call, Throwable t) {
                Log.e("Error", "Error getting prod");
            }
        });
    }

    public void setData(Produit produitShow){
        descProdTxV.setText(produitShow.getDescriptif());
        marqueProdTxV.setText(produitShow.getMarque());
        //TODO : Set EAN
        //eanProdTxV.setText(produitShow.getEan());

        if (prod.getImageUrl() != null)
            Picasso.with(this).load(prod.getImageUrl()).into(imageProdView);
        else
            imageProdView.setImageBitmap(null);
    }
}
