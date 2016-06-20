package com.example.alehmann.productfinding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText login_EditText;
    EditText password_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_EditText = (EditText) findViewById(R.id.login_editText);
        password_EditText = (EditText) findViewById(R.id.login_editText);
    }

    public void button_connexion(View button){
        String login = login_EditText.getText().toString();
        String password = login_EditText.getText().toString();
    }

    public void button_inscription(View button){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void button_addMag(View button){
        Intent r = new Intent(this, NewMagasinActivity.class);
        startActivity(r);
    }
}
