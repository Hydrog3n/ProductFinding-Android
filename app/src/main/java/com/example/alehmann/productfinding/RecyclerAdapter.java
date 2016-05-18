package com.example.alehmann.productfinding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by alehmann on 04/05/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CellHolder>{

    private final Context _context;

    public RecyclerAdapter(Context c) {
        _context = c;
    }

    @Override
    public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View cell = LayoutInflater.from(_context).inflate(R.layout.cell,viewGroup,false);
        return new CellHolder(cell);
    }

    @Override
    public void onBindViewHolder(CellHolder cellHolder, int i) {
        cellHolder.setData("Cellule "+i);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView _cellLabel;
        public ImageView _image;
        private String _data;


        public CellHolder(View itemView) {
            super(itemView);
            _cellLabel = (TextView) itemView.findViewById(R.id.cell_text);
            _cellLabel.setOnClickListener(this);
            _image = (ImageView)itemView.findViewById(R.id.cell_image);
        }

        public void setData(String t){
            //New img loader Cell
            //TODO : Cancel download if already download
            new DownloadImageTask(_image).execute("http://dummyimage.com/600x400/000/fff");
            _cellLabel.setText(t);
            _data = t;
        }

        @Override
        public void onClick(View view) {
            Intent detailIntent = new Intent(_context,DetailMagasinActivity.class);
            detailIntent.putExtra(DetailMagasinActivity.DETAIL_TEXT_KEY, _data);
            _context.startActivity(detailIntent);
        }
    }
}
