package com.example.bubba.bdlista17api23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bubba on 17/04/2018.
 */

public class PalabraSQLite extends SQLiteOpenHelper{

    public static final String TABLA_PALABRA="TPalabra";
    String sqlCreatePalabra="Create table "+TABLA_PALABRA+" (idpalabra INTEGER PRIMARY KEY AUTOINCREMENT, palabra TEXT)";

    public PalabraSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreatePalabra);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_PALABRA+"");
        this.onCreate(sqLiteDatabase);
    }

    public void consultarPalabras(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLA_PALABRA+" order by palabra", null);
        MainActivity.listaPalabras.clear();
        if (cursor.moveToFirst()){
            do {
                MainActivity.listaPalabras.add(new Palabra(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        db.close();
    }

    public boolean insertar(String texto){
        boolean estado=false;
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO " + TABLA_PALABRA + " VALUES (null,'" + texto.toUpperCase() + "')");
            db.setTransactionSuccessful();
            estado=true;
        }catch (Exception e){
            estado=false;
        }finally {

            db.endTransaction();
            db.close();
        }

        return estado;
    }

    public boolean modificar(String texto, int id){
        boolean estado=false;
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("UPDATE " + TABLA_PALABRA + " SET palabra='" + texto.toUpperCase() + "' WHERE idpalabra="+id);
            db.setTransactionSuccessful();
            estado=true;
        }catch (Exception e){
            estado=false;
        }finally {

            db.endTransaction();
            db.close();
        }

        return estado;
    }
    public boolean eliminar(int id){
        boolean estado=false;
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + TABLA_PALABRA + " WHERE idpalabra="+id+"");
            db.setTransactionSuccessful();
            estado=true;
        }catch (Exception e){
            estado=false;
        }finally {

            db.endTransaction();
            db.close();
        }

        return estado;
    }
}

