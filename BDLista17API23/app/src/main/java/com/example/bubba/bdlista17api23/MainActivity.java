package com.example.bubba.bdlista17api23;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class    MainActivity extends AppCompatActivity {
    public static ArrayList<Palabra> listaPalabras;
    ListView lista;
    EditText palabra;
    PalabraSQLite palabraSQLite;
    public static boolean editar=false;
    private static int posicion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaPalabras=new ArrayList<>();
        lista=(ListView) findViewById(R.id.lista);
        palabra=(EditText) findViewById(R.id.texto);
        registerForContextMenu(lista);
        llenar();


    }

    public void llenar(){
        palabraSQLite=new PalabraSQLite(this,"DBPalabras",null,1);
        palabraSQLite.consultarPalabras();
        ArrayAdapter<Palabra> adapter= new ArrayAdapter<Palabra>(this, android.R.layout.simple_list_item_1, MainActivity.listaPalabras);
        lista.setAdapter(adapter);
    }
    public void agregar(View view){
        if (palabra.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),"Ingrese la palabra",Toast.LENGTH_SHORT).show();
        }else{
            if (!editar){
                if(palabraSQLite.insertar(palabra.getText().toString())){
                    Toast.makeText(getBaseContext(),"Eeeexito!!!!!",Toast.LENGTH_SHORT).show();
                    palabra.setText("");
                    palabra.requestFocus();
                }else{
                    Toast.makeText(getBaseContext(),"JA JA",Toast.LENGTH_SHORT).show();
                }
            }else{
                /////////////////////////////
                if(palabraSQLite.modificar(palabra.getText().toString(),MainActivity.listaPalabras.get(posicion).getId())) {
                    Toast.makeText(getBaseContext(),"Eeeexito!!!!!",Toast.LENGTH_SHORT).show();
                    palabra.setText("");
                    palabra.requestFocus();
                }else{
                    Toast.makeText(getBaseContext(),"JA JA",Toast.LENGTH_SHORT).show();
                }
                editar=false;
                ////////////////////////////
            }
            llenar();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater=getMenuInflater();
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Que quiere hacer con \n"+lista.getAdapter().getItem(info.position).toString());
        inflater.inflate(R.menu.menu,menu);
        posicion=info.position;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcion1:
                palabra.setText(MainActivity.listaPalabras.get(posicion).getPalabra());
                palabra.setSelection(MainActivity.listaPalabras.get(posicion).getPalabra().length());
                editar=true;
                break;
            case R.id.opcion2:
                msg("Seguro que desea eliminar");
                break;


        }
        return super.onContextItemSelected(item);
    }

    public void msg(String txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage(txt);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(palabraSQLite.eliminar(MainActivity.listaPalabras.get(posicion).getId())){
                    Toast.makeText(getBaseContext(),"Eliminado",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"Error al eliminar",Toast.LENGTH_SHORT).show();
                }
                llenar();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
