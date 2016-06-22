package com.example.alehmann.productfinding.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Utilisateur;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.example.alehmann.productfinding.Session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrerActivity extends AppCompatActivity {
    EditText username_editText;
    EditText password_editText;
    EditText firstname_editText;
    EditText lastname_editText;
    Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);
        username_editText = (EditText) findViewById(R.id.username_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        firstname_editText = (EditText) findViewById(R.id.firstname_editText);
        lastname_editText = (EditText) findViewById(R.id.lastname_editText);
        register_button = (Button) findViewById(R.id.register_button);
    }

    private void createNewUser(String id, String password, String firstname, String lastname){
        Utilisateur newUser = new Utilisateur(id, password, firstname, lastname);
        Call<Utilisateur> callUtilisateur = Service.getInstance().createUser(newUser);
        callUtilisateur.enqueue(new Callback<Utilisateur> () {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                Toast.makeText(getApplicationContext(), "Utilisateur créé", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erreur création utilisateur", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void inscriptionAction(View Button){
        if (username_editText.getText().toString().trim().length() == 0
                || password_editText.getText().toString().trim().length() == 0
                || firstname_editText.getText().toString().trim().length() == 0
                || lastname_editText.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Champ requis", Toast.LENGTH_LONG).show();
            return;
        }

        createNewUser(username_editText.getText().toString(),
                password_editText.getText().toString(),
                firstname_editText.getText().toString(),
                lastname_editText.getText().toString()
        );
        new SessionManager(getApplicationContext()).checkLogin();
    }
}
