package com.example.alehmann.productfinding.Produit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alehmann on 17/06/2016.
 */
public class RecyclerProduitAdapter extends RecyclerView.Adapter<RecyclerProduitAdapter.CellHolder>{


    private final Context _context;

    private List<Produit> produits = new ArrayList<Produit>();
    public RecyclerProduitAdapter(Context c) {
        _context = c;

        //TODO : find a way to pass the magasin id to get product
        Call<List<Produit>> callProduits = Service.getInstance().getProduitMagasin("1");

        callProduits.enqueue(new Callback<List<Produit>>() {

            @Override
            public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                if (response.isSuccessful()) {
                    produits = response.body();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Produit>> call, Throwable t) {
                Toast toast = Toast.makeText(_context, t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View cell = LayoutInflater.from(_context).inflate(R.layout.cell,viewGroup,false);
        return new CellHolder(cell);
    }

    @Override
    public void onBindViewHolder(CellHolder cellHolder, int i) {
        cellHolder.setData(produits.get(i));
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }

    public class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView _cellLabel;

        private ImageView _image;
        private Bundle objectBundle;

        public CellHolder(View itemView) {
            super(itemView);
            _cellLabel = (TextView) itemView.findViewById(R.id.cell_text);
            _cellLabel.setOnClickListener(this);
            _image = (ImageView)itemView.findViewById(R.id.cell_image);
        }
        public void setData(Produit prod){
            //TODO Pass Object Produits in EXTRAS
            //Creating objectBundle to pass in extra
            objectBundle = new Bundle();
            //String idStringMap = Long.toString(mag.getId());
            //objectBundle.putString("IDMAG",idStringMap);
            //objectBundle.putString("Address", mag.getAddress());
            //objectBundle.putString("CP", mag.getCp());
            //objectBundle.putString("Ville", mag.getVille());

            //if (mag.getLogoUrl() != null)
            //    Picasso.with(_context).load(mag.getLogoUrl()).into(_image);
            //else
            //    _image.setImageBitmap(null);

            _cellLabel.setText(prod.getDescriptif());
        }

        @Override
        public void onClick(View view) {
            //TODO : Detail produit Activity
            //Intent detailIntent = new Intent(_context,DetailMagasinActivity.class);
            //detailIntent.putExtra(DetailMagasinActivity.MAGASIN_NAME, _data);
            //detailIntent.putExtras(objectBundle);
            //_context.startActivity(detailIntent);
        }
    }
}
