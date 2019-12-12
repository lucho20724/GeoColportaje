package uap.geocolportaje.geocoportaje;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Entidades.Libro;

public class listalibrosActivity extends AppCompatActivity {

    ListView listViewLibros;
    Conexion conn;
    boolean eliminar;
    ArrayList<String> listaInformacion;
    ArrayList<Libro> listaLibros;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalibros);
        context=getApplicationContext();

        conn= new Conexion(this,"BD",null,1);

        listViewLibros = (ListView) findViewById(R.id.listViewLibros);

        consultarListaLibro();


        ArrayAdapter adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaInformacion);
        listViewLibros.setAdapter(adaptador);

        eliminar = getIntent().getBooleanExtra("Eliminar",false);

        listViewLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (eliminar){
                    eliminarLibro(listaLibros.get(pos).getId());
                }else {
                    Intent i= new Intent(getApplicationContext(),nuevolibroActivity.class);

                    i.putExtra("idLibro",listaLibros.get(pos).getId());
                    startActivity(i);
                }
            }
        });
    }

    private void eliminarLibro(Integer id){
        Intent i=new Intent(this,libroActivity.class);
        SQLiteDatabase db= conn.getWritableDatabase();

        String sql= "DELETE FROM libro "+
                "WHERE id="+String.valueOf(id);

        db.execSQL(sql);
        Toast.makeText(getApplicationContext(),"Libro Eliminado",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaLibros.size();i++){
            listaInformacion.add(listaLibros.get(i).getId()+" - "+listaLibros.get(i).getNombre());
        }
    }

    private void consultarListaLibro(){
        SQLiteDatabase db= conn.getReadableDatabase();

        Libro libro=null;
        listaLibros=new ArrayList<Libro>();

        Cursor cursor=db.rawQuery("SELECT id, nombre, autor FROM libro",null);

        while (cursor.moveToNext()){
            libro = new Libro();
            libro.setId(cursor.getInt(0));
            libro.setNombre(cursor.getString(1));
            libro.setAutor(cursor.getString(2));

            listaLibros.add(libro);
        }
        obtenerLista();
    }
}