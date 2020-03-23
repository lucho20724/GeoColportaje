package uap.geocolportaje.geocoportaje.ListaSeleccion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.MapsActivity;
import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.planillaActivity;
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;
import uap.geocolportaje.geocoportaje.Entidades.Punto;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaplanillaActivity;
import uap.geocolportaje.geocoportaje.Informes.informeplanillaActivity;
import uap.geocolportaje.geocoportaje.Informes.informepuntosActivity;
import uap.geocolportaje.geocoportaje.R;

public class listapuntoActivity extends AppCompatActivity {

    ListView listViewPuntos;
    Conexion conn;
    boolean eliminar, modificar;
    boolean ver;
    ArrayList<String> listaInformacion;
    ArrayList<Punto> listaPuntos;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapunto);
        context = getApplicationContext();

        conn = new Conexion(this, "BD", null, 1);

        listViewPuntos = (ListView) findViewById(R.id.listViewPuntos);

        consultarListaPlanilla();

        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, listaInformacion);
        listViewPuntos.setAdapter(adaptador);

        eliminar = getIntent().getBooleanExtra("Eliminar", false);
        modificar = getIntent().getBooleanExtra("Modificar", false);

        ver = getIntent().getBooleanExtra("Ver", false);

        listViewPuntos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (eliminar) {
                    eliminarPuntos(listaPuntos.get(pos).getId());
                } else if (ver) {
                    Intent i = new Intent(getApplicationContext(), informepuntosActivity.class);

                    i.putExtra("idPunto", listaPuntos.get(pos).getId());
                    startActivity(i);
                }
            }
        });
    }

    private void eliminarPuntos(Integer id){
        Intent i=new Intent(this,MapsActivity.class);
        SQLiteDatabase db= conn.getWritableDatabase();

        String sql= "DELETE FROM punto "+
                "WHERE id="+String.valueOf(id);

        db.execSQL(sql);
        Toast.makeText(getApplicationContext(),"Punto Eliminado",Toast.LENGTH_LONG).show();
        startActivity(i);

    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaPuntos.size();i++){
            listaInformacion.add(String.valueOf(i)+"- "+listaPuntos.get(i).getTitulo()+" ("+listaPuntos.get(i).getDescripcion()+")");
        }
    }

    private void consultarListaPlanilla() {
        SQLiteDatabase db= conn.getReadableDatabase();

        Punto punto=null;
        listaPuntos=new ArrayList<Punto>();

        try{
            Cursor cursor=db.rawQuery("SELECT id, lat, long, descripcion, titulo, id_cliente " +
                    " FROM punto ",null);

            while (cursor.moveToNext()){
                punto = new Punto();
                punto.setId(cursor.getInt(0));
                punto.setLatitud(cursor.getDouble(1));
                punto.setLongitud(cursor.getDouble(2));
                punto.setDescripcion(cursor.getString(3));
                punto.setTitulo(cursor.getString(4));
                punto.setId_cliente(cursor.getInt(5));


                listaPuntos.add(punto);
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }
        obtenerLista();
    }
}
