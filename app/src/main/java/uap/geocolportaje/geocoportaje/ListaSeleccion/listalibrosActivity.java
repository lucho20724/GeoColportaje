package uap.geocolportaje.geocoportaje.ListaSeleccion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Libro;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaventaActivity;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevolibroActivity;
import uap.geocolportaje.geocoportaje.Persistencia.pLibro;
import uap.geocolportaje.geocoportaje.R;
import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.libroActivity;

public class listalibrosActivity extends AppCompatActivity {

    Button btnVenta;

    ListView listViewLibros;
    Conexion conn;
    boolean eliminar;
    ArrayList<String> listaInformacion;
    ArrayList<Libro> listaLibros;
    Context context;

    ArrayAdapter adaptador;

    //Proceso de venta
    int idCliente;
    ArrayList<Integer> idsSeleccionados;
    TextView lblLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalibros);

        context=getApplicationContext();

        conn= new Conexion(this,"BD",null,1);

        btnVenta=(Button)findViewById(R.id.btnSeguirVenta);

        listViewLibros = (ListView) findViewById(R.id.listViewLibros);

        consultarListaLibro();

        idCliente = getIntent().getIntExtra("idCliente",0);
        eliminar = getIntent().getBooleanExtra("Eliminar",false);

        if(idCliente!=0){
            adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice,listaInformacion);
            listViewLibros.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            idsSeleccionados = new ArrayList<Integer>();
            lblLibro=(TextView) findViewById(R.id.lblLibro);
            lblLibro.setText("Seleccione los libros:");

        }else{
             adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaInformacion);
            btnVenta.setVisibility(View.INVISIBLE);
        }
        listViewLibros.setAdapter(adaptador);

        //Evento al clikear un Item
        listViewLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
         public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
             if (eliminar){
                 eliminarLibro(listaLibros.get(pos).getId());
             }else  if(idCliente!=0){
                 Integer idTemp=listaLibros.get(pos).getId();
                 capturarIds(idTemp);
             }
             else {
                 Intent i= new Intent(getApplicationContext(),nuevolibroActivity.class);

                 i.putExtra("idLibro",listaLibros.get(pos).getId());
                 startActivity(i);
             }
            }
        });
        //----------------------------------------------------------------------------------------------
    }

    public  void onClick(View view) {
        Intent i =null;
        switch (view.getId()){
            case R.id.btnSeguirVenta:
                if(idsSeleccionados.size()>0){
                    i = new Intent(getApplicationContext(),nuevaventaActivity.class);
                    i.putIntegerArrayListExtra("idsLibros",idsSeleccionados);
                    i.putExtra("idCliente",idCliente);
                }else {
                    Toast.makeText(getApplicationContext(),"Debe seleccionar al menos un libro.",Toast.LENGTH_SHORT).show();
                }
        }
        if(i!=null){
            startActivity(i);
        }
    }

    private void capturarIds(Integer id ){
        if(idsSeleccionados.contains(id)){
            idsSeleccionados.remove((id)); //si ya esta marcado y se vuelve a marcar
        }else{
            idsSeleccionados.add(id);
        }
    }


    private void eliminarLibro(Integer id){
        pLibro p = new pLibro();
        Intent i=new Intent(this,libroActivity.class);
        p.eliminarLibroBD(id,context);
        Toast.makeText(getApplicationContext(),"Libro Eliminado",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaLibros.size();i++){
            listaInformacion.add(listaLibros.get(i).getNombre()+" - "+listaLibros.get(i).getAutor());
        }
    }

    /*TODO
    obtenerLista y consultarListaLibro en el mismo metodo
     */

    private void consultarListaLibro(){
        listaLibros=new ArrayList<Libro>();
        pLibro p = new pLibro();

        listaLibros = p.listarLibrosBD(listaLibros, context);
        obtenerLista();
    }
}