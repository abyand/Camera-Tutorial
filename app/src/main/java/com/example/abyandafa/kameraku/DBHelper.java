package com.example.abyandafa.kameraku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

/**
 * Created by Abyan Dafa on 11/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAMA = "kamera.db";
    private static final String TABLE_FOTO = "IMAGES";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAMA, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +TABLE_FOTO + "(nama TEXT PRIMARY KEY, foto TEXT);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addImage(Foto image)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nama", image.getNama());
        values.put("foto", image.getPath());

        db.insert(TABLE_FOTO, null, values);
        db.close();

    }

    public Foto cariData(String nama)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_FOTO, new String[] {"nama", "foto"}, "nama=?", new String[] {nama}, null, null, null, null);

        if(cursor != null) cursor.moveToFirst();

        Foto foto = new Foto(cursor.getString(0), cursor.getString(1));

        return foto;
    }



}
