package com.example.alehmann.productfinding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Service.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alehmann on 04/05/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CellHolder>{


    private final Context _context;

    private List<Magasin> magasins = new ArrayList<Magasin>();
    public RecyclerAdapter(Context c) {
        _context = c;
        Call<List<Magasin>> callMagasins = Service.getInstance().listMagasin();

        callMagasins.enqueue(new Callback<List<Magasin>> () {

            @Override
            public void onResponse(Call<List<Magasin>> call, Response<List<Magasin>> response) {
                if (response.isSuccessful()) {
                    magasins = response.body();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Magasin>> call, Throwable t) {
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
        cellHolder.setData(magasins.get(i));
    }

    @Override
    public int getItemCount() {
        return magasins.size();
    }

    public class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView _cellLabel;

        private ImageView _image;
        //private Magasin _data;
        private Bundle objectBundle;

        public CellHolder(View itemView) {
            super(itemView);
            _cellLabel = (TextView) itemView.findViewById(R.id.cell_text);
            _cellLabel.setOnClickListener(this);
            _image = (ImageView)itemView.findViewById(R.id.cell_image);
        }

        public void setData(Magasin mag){
            //Create bundle
            objectBundle = new Bundle();

            Log.e("test magasin", mag.getName());

            //Set name mag in bundle
            objectBundle.putString("Name", mag.getName());

            //Verify if image exist
            if (mag.getLogoUrl() != null) {
                //Image in cell
                //TODO Set image in cell correctly not async
                _image.setImageBitmap(null);
                new DownloadImageTask(_image).execute(mag.getLogoUrl());
            }
            else
                Log.e("Erreur Magasin", "GetLogoNull; Sur ce maragsin -> " + mag.getName());

            //Name Mag in cell
            _cellLabel.setText(mag.getName());
            //_data = mag;
        }

        @Override
        public void onClick(View view) {
            Intent detailIntent = new Intent(_context,DetailMagasinActivity.class);
            //detailIntent.putExtra(DetailMagasinActivity.MAGASIN_NAME, _data);
            detailIntent.putExtras(objectBundle);
            _context.startActivity(detailIntent);
        }
    }
}
