package com.example.alehmann.productfinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Service.Service;

/**
 * Created by MeAmine on 04/05/2016.
 */
public class NewMagasinActivity extends AppCompatActivity {
    EditText nom_magasain_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_magasin);
        nom_magasain_editText = (EditText) findViewById(R.id.nom_magasain_editText);
    }

    public void button_ajout(View button){
        String nomMag = nom_magasain_editText.getText().toString();
        Magasin Mag = new Magasin(1,nomMag);
        Service.service.createMagasin(Mag);


    }




}
