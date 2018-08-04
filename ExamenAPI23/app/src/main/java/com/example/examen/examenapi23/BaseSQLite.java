package com.example.examen.examenapi23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
        onCreate(db);
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
            Log.d("errooooooooooor: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
        db.close();
    }

    public void insertarP(Pregunta p){

        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {

            db.execSQL("INSERT INTO " + TABLA_PREGUNTAS + " VALUES(null,'"
                    + p.getItem().toString().toUpperCase() + "', '" + p.getRa() + "', '"
                    + p.getRb() + "','"+p.getRc()+"' , '"+p.getRd()+"' , '"+p.getRcorrecta()+"')");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("errooooooooooor: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
        db.close();
    }

    public void insertarR(Respuesta respuesta, Estudiante estudiante){

        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {

            db.execSQL("INSERT INTO " + TABLA_RESPUESTAS + " VALUES(null,'"
                    + estudiante.getIdestudiante() + "', '" +respuesta.getIdpreguntas() + "', '"
                    + respuesta.getRestudiante().toString()+"')");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("errooooooooooor: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
        db.close();
    }

    public void modificarP(Pregunta p){

        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {

            db.execSQL("UPDATE " + TABLA_PREGUNTAS + " set item='"
                    + p.getItem().toString().toUpperCase() + "', ra='" + p.getRa() + "', rb='"
                    + p.getRb() + "', rc='"+p.getRc()+"' , rd='"+p.getRd()+"' , rcorrecta='"+p.getRcorrecta()+"' WHERE idpreguntas='"+p.getIdpregunta()+"'");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("errooooooooooor: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
        db.close();
    }

    public void actualizarEstudiante(Estudiante e){

        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try {

            db.execSQL("UPDATE " + TABLA_ESTUDIATE + " set examinado='1'"
                  +" WHERE idestudiante='"+e.getIdestudiante()+"'");
            db.setTransactionSuccessful();
        }catch (Exception ex){
            Log.d("errooooooooooor: ",ex.getMessage());
        }finally {
            db.endTransaction();
        }
        db.close();
    }

    public double calculoNota(int id){
        double nota=0.00, total=0.00, buenas=0.00;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT count(*) FROM "+TABLA_ESTUDIATE+" as e, "+TABLA_RESPUESTAS+" as r, "+TABLA_PREGUNTAS+
                " as p where e.idestudiante=r.idestudiante and p.idpreguntas=r.idpreguntas and p.rcorrecta=r.restudiante and e.idestudiante="+ id,null);
       // Estudiante estudiante=null;

        cursor.moveToFirst();
        buenas=cursor.getInt(0);

        Cursor cursor2=db.rawQuery("select count(*) from "+ TABLA_PREGUNTAS+"", null);
        cursor2.moveToFirst();
        total=cursor2.getDouble(0);
        nota=(10/total)*buenas;

        db.close();
        return nota;
    }


    public void consultarE(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_ESTUDIATE+" ",null);
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
    public void consultarP(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_PREGUNTAS,null);
        MainActivity.listaPreguntas.clear();
        if (cursor.moveToFirst()){
            do{
                MainActivity.listaPreguntas.add(new Pregunta(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }while(cursor.moveToNext());
        }
        db.close();
        //return temp;
    }

    public void Respuestas(){
        try{
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_PREGUNTAS,null);
        MainActivity.listaRespuestas.clear();
        int cuenta=1;
        if (cursor.moveToFirst()){
            do{
                MainActivity.listaRespuestas.add(new Respuesta(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cuenta++));
            }while(cursor.moveToNext());
        }
        db.close();
        //return temp;
    }catch (Exception e){
            System.out.println(e.getMessage());
    }
    }
    public Pregunta consultarP(int id){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_PREGUNTAS+" WHERE idpreguntas='"+id+"'",null);
        Pregunta temp=null;
        if (cursor.moveToFirst()){
            do{
                temp=new Pregunta(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
            }while(cursor.moveToNext());
        }
        db.close();
        return temp;
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
    public boolean examinado(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLA_RESPUESTAS ,null);
        boolean estado=false;
        if (cursor.moveToFirst()){
            do{
               estado=true;

            }while(cursor.moveToNext());
        }
        db.close();
        return estado;
    }

    public void borrarP(int id){
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("DELETE FROM "+TABLA_PREGUNTAS+" WHERE IDPREGUNTAS='"+id+"'");
        }catch(Exception e){

        }finally {
            db.endTransaction();
        }
        db.close();

    }

    public void borrarExamen(int id){
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("DELETE FROM "+TABLA_RESPUESTAS+" WHERE IDESTUDIANTE='"+id+"'");
        }catch(Exception e){

        }finally {
            db.endTransaction();
        }
        db.close();

    }

    public void suprimirExaminado(int id){
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("UPDATE "+TABLA_ESTUDIATE+" SET examinado='0' WHERE IDESTUDIANTE='"+id+"'");
        }catch(Exception e){

        }finally {
            db.endTransaction();
        }
        db.close();

    }
}
