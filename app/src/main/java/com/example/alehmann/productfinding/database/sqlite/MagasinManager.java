package com.example.alehmann.productfinding.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alehmann.productfinding.Classes.Magasin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MeAmine on 21/06/2016.
 */
public class MagasinManager {
    private static final String TABLE_NAME          = "magasin";
    public static final String KEY_ID_MAGASIN       ="id";
    public static final String KEY_NAME_MAGASIN     ="name";
    public static final String KEY_ADRESS_MAGASIN   ="adress";
    public static final String KEY_CP_MAGASIN       ="cp";
    public static final String KEY_VILLE_MAGASIN    ="ville";
    public static final String KEY_LOGO_MAGASIN     ="logoUrl";
    public static final String CREATE_TABLE_MAGASIN = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_MAGASIN+" INTEGER primary key," +
            " "+KEY_NAME_MAGASIN+" TEXT," +
            " "+KEY_ADRESS_MAGASIN+" TEXT," +
            " "+KEY_CP_MAGASIN+" TEXT," +
            " "+KEY_VILLE_MAGASIN+" TEXT," +
            " "+KEY_LOGO_MAGASIN+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public MagasinManager(Context context)
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

    public long addMagasin(Magasin magasin) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_MAGASIN,      magasin.getId());
        values.put(KEY_NAME_MAGASIN,    magasin.getName());
        values.put(KEY_ADRESS_MAGASIN,  magasin.getAddress());
        values.put(KEY_CP_MAGASIN,      magasin.getCp());
        values.put(KEY_VILLE_MAGASIN,   magasin.getVille());
        values.put(KEY_LOGO_MAGASIN,    magasin.getLogoUrl());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public long addAllMagasins(List<Magasin> magasins) {
        // Ajout d'un enregistrement dans la table
        long err=0;
        for( Magasin mag : magasins){
            ContentValues values = new ContentValues();
            values.put(KEY_ID_MAGASIN,      mag.getId());
            values.put(KEY_NAME_MAGASIN,    mag.getName());
            values.put(KEY_ADRESS_MAGASIN,  mag.getAddress());
            values.put(KEY_CP_MAGASIN,      mag.getCp());
            values.put(KEY_VILLE_MAGASIN,   mag.getVille());
            values.put(KEY_LOGO_MAGASIN,    mag.getLogoUrl());
            if(db.insert(TABLE_NAME,null,values)==-1){
             err=-1;
            }
        }
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return err;
    }

    public int updateMag(Magasin magasin) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_MAGASIN, magasin.getName());
        values.put(KEY_ADRESS_MAGASIN,  magasin.getAddress());
        values.put(KEY_CP_MAGASIN,      magasin.getCp());
        values.put(KEY_VILLE_MAGASIN,   magasin.getVille());
        values.put(KEY_LOGO_MAGASIN,    magasin.getLogoUrl());

        String where = KEY_ID_MAGASIN+" = ?";
        String[] whereArgs = {magasin.getId()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteMagasin(Magasin magasin) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_MAGASIN+" = ?";
        String[] whereArgs = {magasin.getId()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }
    public void deleteAllMagasin() {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        db.execSQL("delete from "+ TABLE_NAME);
    }

    public Magasin getMagasin(int id) {
        // Retourne le magasin dont l'id est passé en paramètre

        Magasin m=new Magasin("","","","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_MAGASIN+"="+id, null);
        if (c.moveToFirst()) {
            m.setId(c.getInt(c.getColumnIndex(KEY_ID_MAGASIN)));
            m.setAddress(c.getString(c.getColumnIndex(KEY_ADRESS_MAGASIN)));
            m.setCp(c.getString(c.getColumnIndex(KEY_CP_MAGASIN)));
            m.setName(c.getString(c.getColumnIndex(KEY_NAME_MAGASIN)));
            m.setVille(c.getString(c.getColumnIndex(KEY_VILLE_MAGASIN)));
            m.setLogoUrl(c.getString(c.getColumnIndex(KEY_LOGO_MAGASIN)));
            c.close();
        }

        return m;
    }

    public Cursor getMagasins() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public List<Magasin> getAllMagasins(){
        Cursor c = this.getMagasins();
        List<Magasin> listMag = new ArrayList<>();

        if (c.moveToFirst())
        {
            do {
                Magasin leMagasin = new Magasin();
                leMagasin.setId(c.getInt(c.getColumnIndex(MagasinManager.KEY_ID_MAGASIN)));
                leMagasin.setName(c.getString(c.getColumnIndex(MagasinManager.KEY_NAME_MAGASIN)));
                leMagasin.setAddress(c.getString(c.getColumnIndex(MagasinManager.KEY_ADRESS_MAGASIN)));
                leMagasin.setVille(c.getString(c.getColumnIndex(MagasinManager.KEY_VILLE_MAGASIN)));
                leMagasin.setCp(c.getString(c.getColumnIndex(MagasinManager.KEY_CP_MAGASIN)));
                leMagasin.setLogoUrl(c.getString(c.getColumnIndex(MagasinManager.KEY_LOGO_MAGASIN)));
                listMag.add(leMagasin);
            }
            while (c.moveToNext());
        }
        c.close();
        return listMag;
    }

}
