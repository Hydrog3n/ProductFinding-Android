package com.example.alehmann.productfinding.Magasin;

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


import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alehmann on 04/05/2016.
 */
public class RecyclerMagasinAdapter extends RecyclerView.Adapter<RecyclerMagasinAdapter.CellHolder>{


    private final Context _context;

    private List<Magasin> magasins = new ArrayList<Magasin>();
    public RecyclerMagasinAdapter(Context c) {
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
        private Bundle objectBundle;

        public CellHolder(View itemView) {
            super(itemView);
            _cellLabel = (TextView) itemView.findViewById(R.id.cell_text);
            _cellLabel.setOnClickListener(this);
            _image = (ImageView)itemView.findViewById(R.id.cell_image);
        }
        public void setData(Magasin mag){
            //TODO Pass Object Magasin in EXTRAS
            //Creating objectBundle to pass in extra
            objectBundle = new Bundle();
            String idStringMap = Long.toString(mag.getId());
            objectBundle.putString("IDMAG",idStringMap);
            //objectBundle.putString("Address", mag.getAddress());
            //objectBundle.putString("CP", mag.getCp());
            //objectBundle.putString("Ville", mag.getVille());

            if (mag.getLogoUrl() != null)
                Picasso.with(_context).load(mag.getLogoUrl()).into(_image);
            else
                _image.setImageBitmap(null);

            _cellLabel.setText(mag.getName());
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
