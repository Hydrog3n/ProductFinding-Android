package com.example.alehmann.productfinding.Magasin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alehmann.productfinding.R;

public class RecyclerMagasinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_magasin);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_magasin_view);
        recyclerView.setAdapter(new RecyclerMagasinAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}