package com.example.alehmann.productfinding.Magasin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Session.SessionManager;

public class RecyclerMagasinActivity extends AppCompatActivity {

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        setContentView(R.layout.activity_recycler_magasin);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_magasin_view);
        recyclerView.setAdapter(new RecyclerMagasinAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent r = new Intent(this, AddMagasinActivity.class);
                startActivity(r);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}