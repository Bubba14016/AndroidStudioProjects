package com.example.examen.examenapi23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseSQLite extends SQLiteOpenHelper {
    public static final String TABLA_ESTUDIATE="TEstudiante";
    public static final String TABLA_PREGUNTAS="TPreguntas";
    public static final String TABLA_RESPUESTAS="TRespuestas";

    String sqlCreateEstudiante="Create table "+TABLA_ESTUDIATE+" (idestudiante INTEGER PRIMARY KEY AUTOINCREMENT, carnet TEXT, nombre" +
            "TEXT, examinado TEXT)";

    String sqlCreatePreguntas="Create table "+TABLA_PREGUNTAS+" (idpreguntas INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, ra" +
            "TEXT, rb TEXT, rc TEXT, rd TEXT, rcorrecta TEXT)";


    String sqlCreateRespuestas="Create table "+TABLA_RESPUESTAS+" (idrespuestas INTEGER PRIMARY KEY AUTOINCREMENT, idestudiante INTEGER, idpreguntas" +
            "INTEGER, restudiante TEXT)";



    public BaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateEstudiante);
        db.execSQL(sqlCreatePreguntas);
        db.execSQL(sqlCreateRespuestas);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_ESTUDIATE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_PREGUNTAS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_RESPUESTAS);
    }

    public void insertarE(Estudiante estudiante){
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO " + TABLA_ESTUDIATE + " VALUES(null,'"
                    + estudiante.getCarnet().toString().toUpperCase() + "', '" + estudiante.getNombre().toString().toUpperCase() + "', '"
                    + estudiante.getExamindao().toString() + "')");
            db.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            db.endTransaction();
        }
        db.close();
    }


    public void consultarE(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_ESTUDIATE+" ORDER BY NOMBRE",null);
        MainActivity.listaEstudiantes.clear();
        if (cursor.moveToFirst()){
            do{
                MainActivity.listaEstudiantes.add(new Estudiante(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }while(cursor.moveToNext());
        }
        db.close();

    }
    public Estudiante login(String usuario, String pass){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_ESTUDIATE+" WHERE CARNET='"+usuario+"' AND CARNET='"+pass+"'",null);
        Estudiante estudiante=null;
        if (cursor.moveToFirst()){
            do{
                estudiante=new Estudiante(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
            }while(cursor.moveToNext());
        }
        db.close();
        return estudiante;
    }
}
