package com.example.alehmann.productfinding.Magasin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.Service;
import com.example.alehmann.productfinding.Session.SessionManager;
import com.example.alehmann.productfinding.database.sqlite.MagasinManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerMagasinActivity extends AppCompatActivity {

    SessionManager session;
    private EditText sortMagasinEditText;
    private List<Magasin> _magasins;
    private RecyclerView recyclerView;
    private List<Magasin> _sortedMagasin;
    private MagasinManager mm = new MagasinManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        setContentView(R.layout.activity_recycler_magasin);

        sortMagasinEditText = (EditText) findViewById(R.id.recherche_magasin_edit_text);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_magasin_view);
        recyclerView.setAdapter(new RecyclerMagasinAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getAdapter().notifyDataSetChanged();

        mm.open();
        Call<List<Magasin>> callMagasins = Service.getInstance().listMagasin();

        callMagasins.enqueue(new Callback<List<Magasin>>() {

            @Override
            public void onResponse(Call<List<Magasin>> call, Response<List<Magasin>> response) {
                if (response.isSuccessful()) {
                    _magasins = response.body();
                    updateMagasinsList(_magasins);
                    mm.deleteAllMagasin();
                    mm.addAllMagasins(_magasins);
                }
            }

            @Override
            public void onFailure(Call<List<Magasin>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                _magasins=mm.getAllMagasins();
                updateMagasinsList(_magasins);
            }
        });

        sortMagasinEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _sortedMagasin = new ArrayList<Magasin>();
                for(Magasin m : _magasins) {
                    if (m.getName().toLowerCase().contains(s.toString().toLowerCase()))
                        _sortedMagasin.add(m);
                }
                updateMagasinsList(_sortedMagasin);
            }
        });
    }

    private void updateMagasinsList(List<Magasin> m) {
        RecyclerMagasinAdapter rma = (RecyclerMagasinAdapter) recyclerView.getAdapter();
        rma.setData(m);
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