package com.example.alehmann.productfinding.Utilisateur;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.alehmann.productfinding.Classes.Utilisateur;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.example.alehmann.productfinding.Session.SessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilisateurActivity extends AppCompatActivity {

    private SessionManager session;
    private Utilisateur _user;
    private TextView usernanme;
    private TextView password;
    private TextView lastname;
    private TextView firstname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);

        usernanme   = (TextView) findViewById(R.id.username_editText_setting);
        password    = (TextView) findViewById(R.id.password_editText_setting);
        lastname    = (TextView) findViewById(R.id.lastname_editText_setting);
        firstname   = (TextView) findViewById(R.id.firstname_editText_setting);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        Call<Utilisateur> utilisateurCall = Service.getInstance()
                .getDetails(user.get(SessionManager.KEY_TOKEN));

        utilisateurCall.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                _user = response.body();
                setData();
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {

                Log.e("t", t.getMessage());
            }
        });
    }

    private void setData() {
        usernanme.setText(_user.getUsername());
        lastname.setText(_user.getLastname());
        firstname.setText(_user.getFirstname());
    }

    public void updateUser(View view) {
        String password = null;
        if (this.password.getText().toString().length() > 0)
            password = this.password.getText().toString();

        Utilisateur userUpdate = new Utilisateur(usernanme.getText().toString(),password,
                firstname.getText().toString(), lastname.getText().toString());

        Call<Utilisateur> up = Service.getInstance().updateUtilisateur(String.valueOf(_user.getId()), userUpdate);
        up.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                _user = response.body();
                setData();
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {

            }
        });
    }

    public void logout(View view) {
        session.logoutUser();
    }

    public void deleteUser(View view) {
        final AlertDialog alertDialog = new AlertDialog.Builder(UtilisateurActivity.this).create();
        alertDialog.setTitle("Suppression");
        alertDialog.setMessage("Étes-vous sur de supprimer votre compte ? Cette action est irréversible.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Supprimer",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete();
                    alertDialog.dismiss();
                }
            });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Annuler",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete();
                    alertDialog.dismiss();
                }
            });
        alertDialog.show();

    }

    private void delete() {
        Call<Utilisateur> deleteUser = Service.getInstance().deleteUtilisateur(String.valueOf(_user.getId()));
        deleteUser.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                session.logoutUser();
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        session.checkLogin();
    }
}
