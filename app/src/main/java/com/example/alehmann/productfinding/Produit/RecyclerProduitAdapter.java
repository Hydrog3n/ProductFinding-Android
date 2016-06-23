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
import com.squareup.picasso.Picasso;

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
    private List<Produit> _produitList;

    public RecyclerProduitAdapter(Context c, List<Produit> produitList) {
        _context = c;

        //TODO : find a way to pass the magasin id to get product
        _produitList = produitList;

    }

    @Override
    public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View cell = LayoutInflater.from(_context).inflate(R.layout.cell,viewGroup,false);
        return new CellHolder(cell);
    }

    @Override
    public void onBindViewHolder(CellHolder cellHolder, int i) {
        cellHolder.setData(_produitList.get(i));
    }

    @Override
    public int getItemCount() {
        return _produitList.size();
    }

    public void setProduits(List<Produit> p) {
        _produitList = p;
        notifyDataSetChanged();
    }

    public class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView _cellLabel;
        private TextView _cellPrix;

        private ImageView _image;

        private Produit _produit;
        public CellHolder(View itemView) {
            super(itemView);
            _cellLabel = (TextView) itemView.findViewById(R.id.cell_text);
            _cellPrix = (TextView) itemView.findViewById(R.id.cell_prix);
            TextView device = (TextView) itemView.findViewById(R.id.device);
            device.setVisibility(View.VISIBLE);
            _cellLabel.setOnClickListener(this);
            _image = (ImageView)itemView.findViewById(R.id.cell_image);
        }

        public void setData(Produit prod){
            _produit = prod;


            String prix = String.valueOf(prod.getPrix());

            _cellPrix.setText(prix);
            if (prod.getImageUrl() != null)
                Picasso.with(_context).load(prod.getImageUrl()).into(_image);
            else
                _image.setImageBitmap(null);

            _cellLabel.setText(prod.getDescriptif());
        }

        @Override
        public void onClick(View view) {
            Intent detailIntent = new Intent(_context, DetailProduitActivity.class);
            detailIntent.putExtra("prod", _produit);
            _context.startActivity(detailIntent);
        }
    }
}
