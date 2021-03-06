package com.example.alehmann.productfinding.Produit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.OpenProduct;
import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.Classes.ProduitInMagasin;
import com.example.alehmann.productfinding.R;
import com.example.alehmann.productfinding.Service.OpenFoodService;
import com.example.alehmann.productfinding.Service.Service;
import com.example.alehmann.productfinding.barcodereader.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MeAmine on 15/06/2016.
 */
public class AddProduitActivity extends AppCompatActivity {
    EditText descriptif_produit_editText;
    EditText ean_produit_editText;
    EditText marque_produit_editText;
    EditText prix_produit_editText;
    Button buttonNext;
    Button buttonAjout;
    TextView descriptifTextView;
    TextView marqueTextView;
    TextView prixTextView;

    Produit _produit;
    Magasin _magasin;

    Spinner spinnerBrands;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private String _ean;
    private String _url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_produit);
        descriptif_produit_editText =   (EditText) findViewById(R.id.descriptif_produit_editText);
        ean_produit_editText        =   (EditText) findViewById(R.id.ean_produit_editText);
        marque_produit_editText     =   (EditText) findViewById(R.id.marque_produit_editText);
        prix_produit_editText       =   (EditText) findViewById(R.id.prix_produit_editText);
        descriptifTextView          =   (TextView) findViewById(R.id.descriptif_produit_vextview);
        prixTextView                =   (TextView) findViewById(R.id.prix_produit_textview);
        marqueTextView              =   (TextView) findViewById(R.id.marque_produit_vextview);

        buttonAjout                 =   (Button) findViewById(R.id.add_produit_button);
        buttonNext = (Button) findViewById(R.id.next_produit_button);

        _magasin = (Magasin) this.getIntent().getExtras().getSerializable("mag");
    }

    public void button_ajout(View button){
        if (descriptif_produit_editText.getText().toString().trim().length() == 0
                || marque_produit_editText.getText().toString().trim().length() == 0
                || ean_produit_editText.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Champ requis", Toast.LENGTH_LONG).show();
            return;
        }
        String descProd       = descriptif_produit_editText.getText().toString();
        String marqueProd     = marque_produit_editText.getText().toString();
        String url = _url;
        String ean = ean_produit_editText.getText().toString();
        Produit prod = new Produit(descProd, marqueProd, url, ean);

        //TODO Check si le produit n'existe pas. (Utiliser l'ean)
        Call<Produit> call = Service.getInstance().createProduit(prod);
        call.enqueue(new Callback<Produit>() {
            @Override
            public void onResponse(Call<Produit> call, Response<Produit> response) {
                _produit = response.body();
                linkProduitToMagasin();
            }

            @Override
            public void onFailure(Call<Produit> call, Throwable t) {
                Log.d("Debug/add produit", t.getMessage());
            }

        });
    }

    public void buttonNext(View button) {
        _ean = ean_produit_editText.getText().toString();

        Call<Produit> call = Service.getInstance().checkProduit(_ean);

        call.enqueue(new Callback<Produit>() {
            @Override
            public void onResponse(Call<Produit> call, Response<Produit> response) {
                _produit = response.body();
                if (_produit != null)
                    showPrix();
                else {
                    checkOpenFood(_ean);
                }
            }

            @Override
            public void onFailure(Call<Produit> call, Throwable t) {
                checkOpenFood(_ean);
            }
        });
    }

    private void checkOpenFood (String ean) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fr.openfoodfacts.org/api/v0/produit/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodService serviceOpenFood = retrofit.create(OpenFoodService.class);

        Call<OpenProduct> openFoodCall = serviceOpenFood.getProduct(ean);

        openFoodCall.enqueue(new Callback<OpenProduct>() {

            @Override
            public void onResponse(Call<OpenProduct> call, Response<OpenProduct> response) {
                OpenProduct openFoodproduct = response.body();
                if (openFoodproduct.getProduct() != null) {
                    descriptif_produit_editText.setText(openFoodproduct.getProduct().getProduct_name_fr());
                    List<String> brands =  openFoodproduct.getProduct().getBrands_tags();
                    if (brands.size() > 0)
                        marque_produit_editText.setText(brands.get(0));
                    if (openFoodproduct.getProduct().getImage_url() != null)
                        _url = openFoodproduct.getProduct().getImage_url();
                }
                addProduit();
            }

            @Override
            public void onFailure(Call<OpenProduct> call, Throwable t) {
                addProduit();
            }
        });

    }

    private void addProduit() {
        descriptif_produit_editText.setVisibility(View.VISIBLE);
        marque_produit_editText.setVisibility(View.VISIBLE);
        prix_produit_editText.setVisibility(View.VISIBLE);
        buttonAjout.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.GONE);

        descriptifTextView.setVisibility(View.VISIBLE);
        marqueTextView.setVisibility(View.VISIBLE);
        prixTextView.setVisibility(View.VISIBLE);
    }

    private void showPrix() {
        buttonNext.setVisibility(View.GONE);
        buttonAjout.setVisibility(View.VISIBLE);
        buttonAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkProduitToMagasin();
            }
        });
        prixTextView.setVisibility(View.VISIBLE);
        prix_produit_editText.setVisibility(View.VISIBLE);
    }

    public void linkProduitToMagasin() {

        //TODO : Test if Prix is float
        if (prix_produit_editText.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Champ requis", Toast.LENGTH_LONG).show();
            return;
        }

        String prix = prix_produit_editText.getText().toString();
        ProduitInMagasin pim = new ProduitInMagasin(Float.valueOf(prix), _produit, _magasin);
        Call<ProduitInMagasin> call = Service.getInstance().linkProduit(pim);
        call.enqueue(new Callback<ProduitInMagasin>() {
            @Override
            public void onResponse(Call<ProduitInMagasin> call, Response<ProduitInMagasin> response) {
                Toast.makeText(getApplicationContext(), "Produit Ajouté", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<ProduitInMagasin> call, Throwable t) {
                Log.d("Debug/link produit", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showCamera(View view) {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    ean_produit_editText.setText(barcode.displayValue);
                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    Log.d("test", "No barcode captured, intent data is null");
                }
            } else {
                Log.d("test", "");
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                        //CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
