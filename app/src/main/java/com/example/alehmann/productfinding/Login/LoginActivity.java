package com.example.alehmann.productfinding.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.alehmann.productfinding.Magasin.RecyclerMagasinActivity;

import com.example.alehmann.productfinding.Classes.Utilisateur;
import com.example.alehmann.productfinding.Magasin.NewMagasinActivity;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.example.alehmann.productfinding.Session.SessionManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText login_EditText;
    EditText password_EditText;
    Utilisateur utilisateur;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        login_EditText = (EditText) findViewById(R.id.login_editText);
        password_EditText = (EditText) findViewById(R.id.password_editText);
    }

    public void button_connexion(View button){
        String login = login_EditText.getText().toString();
        String password = password_EditText.getText().toString();

        if (login.trim().length() > 0 && password.trim().length() > 0) {

            List<String> loginInfos = new ArrayList<>();
            loginInfos.add(login);
            loginInfos.add(password);

            // Appel WebService connexion return l'utilisateur

            Call<Utilisateur> callUtilisateur = Service.getInstance().loginUser(loginInfos);
            callUtilisateur.enqueue(new Callback<Utilisateur>() {
                @Override
                public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                    utilisateur = response.body();
                    if (utilisateur.getUsername() != null) {
                        session.createLoginSession(utilisateur.getUsername(), utilisateur.getToken());
                        showMagasinList();
                    } else {
                        setupAlertError("Impossible de se connecter", "Login ou mot de passe incorrect");
                    }
                }

                @Override
                public void onFailure(Call<Utilisateur> call, Throwable t) {
                    setupAlertError("Une erreur c'est produite", "Merci de vérifier votre connexion internet.");
                }
            });
        }
    }

    public void button_inscription(View button){
        Intent i = new Intent(this, RegistrerActivity.class);
        startActivity(i);
    }

    public void button_addMag(View button) {
        Intent r = new Intent(this, NewMagasinActivity.class);
        startActivity(r);
    }

    public void showMagasinList(){
        Intent i = new Intent(this, RecyclerMagasinActivity.class);
        startActivity(i);
    }

    public void setupAlertError(String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
