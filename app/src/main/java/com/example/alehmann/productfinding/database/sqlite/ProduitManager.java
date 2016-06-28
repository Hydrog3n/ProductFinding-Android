package com.example.alehmann.productfinding.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alehmann.productfinding.Classes.Produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MeAmine on 22/06/2016.
 */
public class ProduitManager {

    private static final String TABLE_NAME = "produit";
    public static final String KEY_ID_PRODUIT           ="id";
    public static final String KEY_DESCRIPTIF_PRODUIT   ="descriptif";
    public static final String KEY_MARQUE_PRODUIT       ="marque";
    public static final String KEY_IMAGEURL_PRODUIT     ="imageUrl";
    public static final String KEY_EAN_PRODUIT          ="ean";
    public static final String CREATE_TABLE_PRODUIT     = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_PRODUIT          +" INTEGER primary key autoincrement," +
            " "+KEY_DESCRIPTIF_PRODUIT  +" TEXT," +
            " "+KEY_MARQUE_PRODUIT      +" TEXT," +
            " "+KEY_IMAGEURL_PRODUIT    +" TEXT," +
            " "+KEY_EAN_PRODUIT         +" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public ProduitManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addProduit(Produit produit) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTIF_PRODUIT   ,   produit.getDescriptif());
        values.put(KEY_MARQUE_PRODUIT       ,   produit.getMarque());
        values.put(KEY_IMAGEURL_PRODUIT     ,   produit.getImageUrl());
        values.put(KEY_EAN_PRODUIT          ,   produit.getImageUrl());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }
    public long addAllProduits(List<Produit> produits) {
        // Ajout d'un enregistrement dans la table
        long err=0;
        for( Produit prod : produits){
            ContentValues values = new ContentValues();
            values.put(KEY_DESCRIPTIF_PRODUIT   ,   prod.getDescriptif());
            values.put(KEY_MARQUE_PRODUIT       ,   prod.getMarque());
            values.put(KEY_IMAGEURL_PRODUIT     ,   prod.getImageUrl());
            values.put(KEY_EAN_PRODUIT          ,   prod.getImageUrl());
            if(db.insert(TABLE_NAME,null,values)==-1){
                err=-1;
            }
        }
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return err;
    }

    public int updateMag(Produit produit) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTIF_PRODUIT   ,   produit.getDescriptif());
        values.put(KEY_MARQUE_PRODUIT       ,   produit.getMarque());
        values.put(KEY_IMAGEURL_PRODUIT     ,   produit.getImageUrl());
        values.put(KEY_EAN_PRODUIT          ,   produit.getImageUrl());

        String where = KEY_ID_PRODUIT+" = ?";
        String[] whereArgs = {produit.getId()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteProduit(Produit produit) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_PRODUIT+" = ?";
        String[] whereArgs = {produit.getId()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Produit getProduit(int id) {
        // Retourne le produit dont l'id est passé en paramètre

        Produit p = new Produit("","","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_PRODUIT+"="+id, null);
        if (c.moveToFirst()) {
            p.setId(c.getInt(c.getColumnIndex(KEY_ID_PRODUIT)));
            p.setDescriptif(c.getString(c.getColumnIndex(KEY_DESCRIPTIF_PRODUIT)));
            p.setMarque(c.getString(c.getColumnIndex(KEY_MARQUE_PRODUIT)));
            p.setImageUrl(c.getString(c.getColumnIndex(KEY_IMAGEURL_PRODUIT)));
            p.setEan(c.getString(c.getColumnIndex(KEY_EAN_PRODUIT)));
            c.close();
        }

        return p;
    }

    public Cursor getProduits() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
    public List<Produit> getAllMagasins(){
        Cursor c = this.getProduits();
        List<Produit> listPro = new ArrayList<>();

        if (c.moveToFirst())
        {
            do {
                Produit leProduit = new Produit();
                leProduit.setId(c.getInt(c.getColumnIndex(KEY_ID_PRODUIT)));
                leProduit.setDescriptif(c.getString(c.getColumnIndex(KEY_DESCRIPTIF_PRODUIT)));
                leProduit.setMarque(c.getString(c.getColumnIndex(KEY_MARQUE_PRODUIT)));
                leProduit.setImageUrl(c.getString(c.getColumnIndex(KEY_IMAGEURL_PRODUIT)));
                leProduit.setEan(c.getString(c.getColumnIndex(KEY_EAN_PRODUIT)));
                listPro.add(leProduit);
            }
            while (c.moveToNext());
        }
        c.close();
        return listPro;
    }

}
