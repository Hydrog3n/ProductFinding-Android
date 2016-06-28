package com.example.alehmann.productfinding.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alehmann.productfinding.Classes.Magasin;
import com.example.alehmann.productfinding.Classes.Produit;
import com.example.alehmann.productfinding.Classes.ProduitInMagasin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MeAmine on 22/06/2016.
 */
public class ProduitInMagasinManager {
    private static final String TABLE_NAME = "produitinmagasin";
    public static final String KEY_ID_MAGASIN = "idMagasin";
    public static final String KEY_ID_PRODUIT = "idProduit";
    public static final String KEY_PRIX = "prix";
    public static final String CREATE_TABLE_PRODUITINMAGASIN = "CREATE TABLE " + TABLE_NAME +
            " (" +
            " " + KEY_ID_MAGASIN + " INTEGER," +
            " " + KEY_ID_PRODUIT + " INTEGER," +
            " " + KEY_PRIX + " REAL, " +
            "  PRIMARY KEY ("+KEY_ID_MAGASIN+","+KEY_ID_PRODUIT+")" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public ProduitInMagasinManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addProduitOfMagasins(List<ProduitInMagasin> produitInMagasin) {
        // Ajout d'un enregistrement dans la table
        long err = 0;
        for (ProduitInMagasin prod : produitInMagasin) {
            ContentValues values = new ContentValues();
            values.put(KEY_ID_MAGASIN, prod.getId().getMagasin().getId());
            values.put(KEY_ID_PRODUIT, prod.getId().getProduit().getId());
            values.put(KEY_PRIX, prod.getPrix());
            if (db.insert(TABLE_NAME, null, values) == -1) {
                err = -1;
            }
        }
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return err;
    }

    public long addProduitsUnMagasin(List<Produit> produit, long id) {
        // Ajout d'un enregistrement dans la table
        long err = 0;
        for (Produit prod : produit) {
            ContentValues values = new ContentValues();
            values.put(KEY_ID_MAGASIN, id);
            values.put(KEY_ID_PRODUIT, prod.getId());
            values.put(KEY_PRIX, prod.getPrix());

            if (db.insert(TABLE_NAME, null, values) == -1) {
                err = -1;
            }
        }
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return err;
    }



    public int updateProdInMag(ProduitInMagasin prodmag) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_PRIX, prodmag.getPrix());

        String where = KEY_ID_MAGASIN + " = " + prodmag.getId().getMagasin().getId() + " AND " + KEY_ID_PRODUIT + " = " + prodmag.getId().getProduit().getId();
        //String[] whereArgs = {magasin.getId()+""};

        return db.update(TABLE_NAME, values, where, null);
    }

    public int deleteProduitInMagasin(ProduitInMagasin prodmag) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_MAGASIN + " = " + prodmag.getId().getMagasin().getId() + " AND " + KEY_ID_PRODUIT + " = " + prodmag.getId().getProduit().getId();
        //String[] whereArgs = {magasin.getId()+""};

        return db.delete(TABLE_NAME, where, null);
    }

    public void deleteAllProduitInMagasin() {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        db.execSQL("delete from " + TABLE_NAME);
    }

    public ProduitInMagasin getProduitInMagasin(ProduitInMagasin.Id id) {
        // Retourne le magasin dont l'id est passé en paramètre

        ProduitInMagasin piM = new ProduitInMagasin();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_MAGASIN + " = " + id.getMagasin().getId() + " AND " + KEY_ID_PRODUIT + " = " + id.getProduit().getId(), null);
        if (c.moveToFirst()) {
            piM.setId(id);
            piM.setPrix(c.getInt(c.getColumnIndex(KEY_PRIX)));

            c.close();
        }

        return piM;
    }

    public Cursor getProduitsInMagasins() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<ProduitInMagasin> getAllProduitInMagasins() {
        Cursor c = this.getProduitsInMagasins();
        List<ProduitInMagasin> listProdMag = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Cursor m = db.rawQuery("SELECT * from magasin where id = " + c.getInt(c.getColumnIndex(ProduitInMagasinManager.KEY_ID_MAGASIN)), null);
                Cursor p = db.rawQuery("SELECT * from produit where id = " + c.getInt(c.getColumnIndex(ProduitInMagasinManager.KEY_ID_PRODUIT)), null);
                if (m.moveToFirst() && p.moveToFirst()) {

                    Produit leProd = new Produit();
                    leProd.setId(p.getInt(p.getColumnIndex(ProduitManager.KEY_ID_PRODUIT)));
                    leProd.setDescriptif(p.getString(p.getColumnIndex(ProduitManager.KEY_DESCRIPTIF_PRODUIT)));
                    leProd.setEan(p.getString(p.getColumnIndex(ProduitManager.KEY_EAN_PRODUIT)));
                    leProd.setImageUrl(p.getString(p.getColumnIndex(ProduitManager.KEY_IMAGEURL_PRODUIT)));
                    leProd.setMarque(p.getString(p.getColumnIndex(ProduitManager.KEY_MARQUE_PRODUIT)));


                    Magasin leMagasin = new Magasin();
                    leMagasin.setId(m.getInt(m.getColumnIndex(MagasinManager.KEY_ID_MAGASIN)));
                    leMagasin.setName(m.getString(m.getColumnIndex(MagasinManager.KEY_NAME_MAGASIN)));
                    leMagasin.setAddress(m.getString(m.getColumnIndex(MagasinManager.KEY_ADRESS_MAGASIN)));
                    leMagasin.setVille(m.getString(m.getColumnIndex(MagasinManager.KEY_VILLE_MAGASIN)));
                    leMagasin.setCp(m.getString(m.getColumnIndex(MagasinManager.KEY_CP_MAGASIN)));
                    leMagasin.setLogoUrl(m.getString(m.getColumnIndex(MagasinManager.KEY_LOGO_MAGASIN)));


                    ProduitInMagasin leProdInMagasin = new ProduitInMagasin(leProd,leMagasin);
                    leProdInMagasin.setPrix(c.getInt(c.getColumnIndex(ProduitInMagasinManager.KEY_PRIX)));
                    listProdMag.add(leProdInMagasin);
                    m.close();
                    p.close();
                }


            }
            while (c.moveToNext());
        }
        c.close();
        return listProdMag;
    }


    public Cursor getProduitsUnMagasins(long id) {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+KEY_ID_MAGASIN+" = "+id, null);
    }

    public List<Produit> getAllProduitUnMagasins(long id) {
        Cursor c = this.getProduitsUnMagasins(id);
        List<Produit> listProdMag = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Cursor p = db.rawQuery("SELECT * from produit where id = " + c.getInt(c.getColumnIndex(ProduitInMagasinManager.KEY_ID_PRODUIT)), null);
                if (p.moveToFirst()) {

                    Produit leProd = new Produit();
                    leProd.setId(p.getInt(p.getColumnIndex(ProduitManager.KEY_ID_PRODUIT)));
                    leProd.setDescriptif(p.getString(p.getColumnIndex(ProduitManager.KEY_DESCRIPTIF_PRODUIT)));
                    leProd.setEan(p.getString(p.getColumnIndex(ProduitManager.KEY_EAN_PRODUIT)));
                    leProd.setImageUrl(p.getString(p.getColumnIndex(ProduitManager.KEY_IMAGEURL_PRODUIT)));
                    leProd.setMarque(p.getString(p.getColumnIndex(ProduitManager.KEY_MARQUE_PRODUIT)));
                    leProd.setPrix(c.getLong(c.getColumnIndex(ProduitInMagasinManager.KEY_PRIX)));
                    listProdMag.add(leProd);
                    p.close();
                }


            }
            while (c.moveToNext());
        }
        c.close();
        return listProdMag;
    }


}
