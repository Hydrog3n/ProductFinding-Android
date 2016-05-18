package com.example.alehmann.productfinding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by alehmann on 18/05/2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    protected ImageView newImg;


    public DownloadImageTask(ImageView newImage) {
        this.newImg = newImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null)
            newImg.setImageBitmap(result);
    }
}
