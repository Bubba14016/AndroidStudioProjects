package com.example.bubba.gasolinera12api23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bubba on 2/5/2018.
 */

public class BaseDatos extends SQLiteOpenHelper{

    public static final String TABLA_VENTA="TVentas";
    public static final String TABLA_PRECIO="TPrecios";

    String sqlCrearTablaPrecios="Create table "+TABLA_PRECIO+" (idprecio INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, cantidad REAL)";
    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCrearTablaPrecios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_PRECIO+"");
        this.onCreate(sqLiteDatabase);
    }

    public void consultarPrecios(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_PRECIO, null);
            int pos = 1;
            //MainActivity.listaPalabras.clear();
            if (cursor.moveToFirst()) {
                do {
                    switch (pos) {
                        case 1:
                            MainActivity.precios.setPrecioGalonSuper(cursor.getFloat(2));
                            pos++;
                            break;
                        case 2:
                            MainActivity.precios.setPrecioGalonRegular(cursor.getFloat(2));
                            pos++;
                            break;
                        case 3:
                            MainActivity.precios.setPrecioGalonDisel(cursor.getFloat(2));
                            pos++;
                            break;

                    }

                } while (cursor.moveToNext());
            }
            db.close();
        }catch (Exception e){
            Log.i("msg",e.getMessage());
        }
    }

    public void llenarPrecios(){
        SQLiteDatabase db=getReadableDatabase();
        try{

            db.execSQL("INSERT INTO " + TABLA_PRECIO + " VALUES (null,'super','3')");
            db.setTransactionSuccessful();
            db.execSQL("INSERT INTO " + TABLA_PRECIO + " VALUES (null,'regular','2')");
            db.setTransactionSuccessful();
            db.execSQL("INSERT INTO " + TABLA_PRECIO + " VALUES (null,'diesel','1')");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("msg",e.getMessage());
        }finally {

            db.endTransaction();
            db.close();
        }
    }
}
