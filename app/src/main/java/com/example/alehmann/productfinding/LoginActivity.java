package com.example.alehmann.productfinding;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alehmann.productfinding.Classes.Utilisateur;
import com.example.alehmann.productfinding.Service.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText login_EditText;
    EditText password_EditText;
    Utilisateur utilisateur;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_EditText = (EditText) findViewById(R.id.login_editText);
        password_EditText = (EditText) findViewById(R.id.password_editText);
    }

    public void button_connexion(View button){
        String login = login_EditText.getText().toString();
        String password = password_EditText.getText().toString();
        List<String> loginInfos = new ArrayList<>();
        loginInfos.add(login);
        loginInfos.add(password);
        // Appel WebService connexion return l'utilisateur
        Call<Utilisateur> callUtilisateur = Service.getInstance().loginUser(loginInfos);
        callUtilisateur.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                Log.e("oui", "Jesuis làààà");
                utilisateur = response.body();
                Log.e("Response : ", response.body().toString());
                Log.e("L'utilisateur", utilisateur.getFirstname() + utilisateur.getLastname());
                if(utilisateur.getUsername() != null) {
                    goListShops();
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Impossible de se connecter");
                    alertDialog.setMessage("Login ou mot de passe incorrecte");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Log.e("oui", t.getMessage());
            }
        });
    }

    public void button_inscription(View button){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void button_addMag(View button) {
        Intent r = new Intent(this, NewMagasinActivity.class);
        startActivity(r);
    }

    public void goListShops(){
        Intent i = new Intent(this, RecyclerMagasinActivity.class);
        startActivity(i);
    }
}
