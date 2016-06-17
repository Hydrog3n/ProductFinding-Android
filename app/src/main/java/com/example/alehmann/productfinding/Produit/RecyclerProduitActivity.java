package com.example.alehmann.productfinding.Produit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.alehmann.productfinding.R;

/**
 * Created by alehmann on 17/06/2016.
 */
public class RecyclerProduitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_magasin);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_produit_view);
        recyclerView.setAdapter(new RecyclerProduitAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
