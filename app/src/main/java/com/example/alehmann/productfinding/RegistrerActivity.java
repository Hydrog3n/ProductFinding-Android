package com.example.alehmann.productfinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RegistrerActivity extends AppCompatActivity {
    EditText id_editText;
    EditText password_editText;
    EditText firstname_editText;
    EditText lastname_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);
        id_editText = (EditText)findViewById(R.id.id_editText);
        password_editText = (EditText)findViewById(R.id.password_editText);
        firstname_editText = (EditText)findViewById(R.id.firstname_editText);
        lastname_editText = (EditText)findViewById(R.id.lastname_editText);
    }
}
