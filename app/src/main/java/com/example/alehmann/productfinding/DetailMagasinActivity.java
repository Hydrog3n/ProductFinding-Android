package com.example.alehmann.productfinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailMagasinActivity extends AppCompatActivity {
    private TextView textView;
    private String nameMag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_magasin);
        textView = (TextView)findViewById(R.id.text_view);

        //Get bundle from RecyclerView and test content bundle
        Bundle objectBundle  = this.getIntent().getExtras();

        //On récupère les données du Bundle
        if (objectBundle != null && objectBundle.containsKey("Name")) {
            nameMag = this.getIntent().getStringExtra("Name");
        }else
            nameMag = "Error";

        //Set mag Name from bundle
        textView.setText(nameMag);
    }
}