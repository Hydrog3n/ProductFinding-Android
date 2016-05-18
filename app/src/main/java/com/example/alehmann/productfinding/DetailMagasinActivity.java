package com.example.alehmann.productfinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailMagasinActivity extends AppCompatActivity {
    public static final String DETAIL_TEXT_KEY = "Detail_text";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_magasin);
        textView = (TextView)findViewById(R.id.text_view);

        String detailText = getIntent().getStringExtra(DETAIL_TEXT_KEY);
        if (detailText != null) {
            textView.setText(detailText);
        }
    }
}
