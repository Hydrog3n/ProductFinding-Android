package com.example.alehmann.productfinding.Magasin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Produit.RecyclerProduitAdapter;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMagasinActivity extends AppCompatActivity {

    private Magasin mag;
    private TextView nameMagTxV;
    private TextView cpMagTxV;
    private TextView villeMagTxV;
    private TextView addressMagTxV;
    private ImageView imageMagasinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UI Content
        setContentView(R.layout.activity_detail_magasin);
        nameMagTxV = (TextView) findViewById(R.id.text_view_Mag_Name);
        cpMagTxV = (TextView) findViewById(R.id.text_view_Mag_Cp);
        villeMagTxV = (TextView) findViewById(R.id.text_view_Mag_Ville);
        addressMagTxV = (TextView) findViewById(R.id.text_view_Mag_Address);
        imageMagasinView = (ImageView) findViewById(R.id.image_magasin_view);


        //Set mag Name from bundle
        //Get bundle from RecyclerView and test content bundle
        Bundle objectBundle = this.getIntent().getExtras();

        if (objectBundle == null) {
            Toast toast = Toast.makeText(this, "ERROR bundle null", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        String id = this.getIntent().getStringExtra("IDMAG");
        //Load recycler view inside Detail magasin activity, passing the id for request
        //new RecyclerProduitAdapter(this, id);
        Call<Magasin> callMagasins = Service.getInstance().thisMagasin(id);
        //Request retrofit to get a Magasin
        callMagasins.enqueue(new Callback<Magasin>() {

            @Override
            public void onResponse(Call<Magasin> call, Response<Magasin> response) {
                if (response.isSuccessful()) {
                    mag = response.body();
                    setData(mag);
                }
            }

            @Override
            public void onFailure(Call<Magasin> call, Throwable t) {
                Log.e("Error", "Error getting mag");
            }
        });
    }

    public void setData(Magasin magasinShow){
        nameMagTxV.setText(magasinShow.getName());
        cpMagTxV.setText(magasinShow.getCp());
        villeMagTxV.setText(magasinShow.getVille());
        addressMagTxV.setText(magasinShow.getAddress());
        if (mag.getLogoUrl() != null)
            Picasso.with(this).load(mag.getLogoUrl()).into(imageMagasinView);
        else
            imageMagasinView.setImageBitmap(null);
    }


}