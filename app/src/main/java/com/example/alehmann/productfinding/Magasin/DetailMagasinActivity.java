package com.example.alehmann.productfinding.Magasin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.Produit.AddProduitActivity;
import com.example.alehmann.productfinding.Produit.RecyclerProduitAdapter;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMagasinActivity extends AppCompatActivity {

    private Context _context;
    private Magasin mag;
    private TextView nameMagTxV;
    private TextView cpMagTxV;
    private TextView villeMagTxV;
    private TextView addressMagTxV;
    private ImageView imageMagasinView;
    private EditText rechercheProduit;

    private List<Produit> _produits;

    private RecyclerView recyclerViewProduits;
    private ArrayList<Produit> _filtredProduit;

    @Override
    public void onResume() {
        super.onResume();
        updateProduitData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UI Content

        _context = getApplicationContext();

        setContentView(R.layout.activity_detail_magasin);

        nameMagTxV = (TextView) findViewById(R.id.text_view_Mag_Name);
        cpMagTxV = (TextView) findViewById(R.id.text_view_Mag_Cp);
        villeMagTxV = (TextView) findViewById(R.id.text_view_Mag_Ville);
        addressMagTxV = (TextView) findViewById(R.id.text_view_Mag_Address);
        imageMagasinView = (ImageView) findViewById(R.id.image_magasin_view);
        rechercheProduit = (EditText) findViewById(R.id.recherche_produit_magasin);

        rechercheProduit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _filtredProduit = new ArrayList<Produit>();
                for(Produit p : _produits) {
                    if (p.getDescriptif().contains(s))
                        _filtredProduit.add(p);
                }
                updateProduitList(_filtredProduit);
            }
        });

        mag = (Magasin) this.getIntent().getExtras().getSerializable("mag");

        nameMagTxV.setText(mag.getName());
        cpMagTxV.setText(mag.getCp());
        villeMagTxV.setText(mag.getVille());
        addressMagTxV.setText(mag.getAddress());
        

        _produits = new ArrayList<Produit>();

        recyclerViewProduits = (RecyclerView) findViewById(R.id.recycler_produit_view);
        recyclerViewProduits.setAdapter(new RecyclerProduitAdapter(this, _produits));
        recyclerViewProduits.setLayoutManager(new LinearLayoutManager(this));

        if (mag.getLogoUrl() != null)
            Picasso.with(this).load(mag.getLogoUrl()).into(imageMagasinView);
        else
            imageMagasinView.setImageBitmap(null);
    }

    public void updateProduitList(List<Produit> produits) {
        RecyclerProduitAdapter rpa = (RecyclerProduitAdapter) recyclerViewProduits.getAdapter();
        rpa.setProduits(produits);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent r = new Intent(this, AddProduitActivity.class);
                r.putExtra("mag", mag);
                startActivity(r);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateProduitData() {
        Call<List<Produit>> callProduit = Service.getInstance().getProduitMagasin(""+mag.getId()+"");

        callProduit.enqueue(new Callback<List<Produit>>() {

            @Override
            public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                if (response.isSuccessful()) {
                    _produits = response.body();
                    updateProduitList(_produits);
                }
            }

            @Override
            public void onFailure(Call<List<Produit>> call, Throwable t) {
                Toast toast = Toast.makeText(_context, t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}