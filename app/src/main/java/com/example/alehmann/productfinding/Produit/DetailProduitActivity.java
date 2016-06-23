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

        prod = (Produit) this.getIntent().getExtras().getSerializable("prod");
        setData();
    }

    public void setData(){
        descProdTxV.setText(prod.getDescriptif());
        marqueProdTxV.setText(prod.getMarque());

        if (prod.getImageUrl() != null)
            Picasso.with(this).load(prod.getImageUrl()).into(imageProdView);
        else
            imageProdView.setImageBitmap(null);
    }
}
