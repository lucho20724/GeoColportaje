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

import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.planillaActivity;
import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.ventasActivity;
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;
import uap.geocolportaje.geocoportaje.Entidades.Venta;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaplanillaActivity;
import uap.geocolportaje.geocoportaje.Informes.informeplanillaActivity;
import uap.geocolportaje.geocoportaje.Informes.informeventaActivity;
import uap.geocolportaje.geocoportaje.R;

public class listaventasActivity extends AppCompatActivity {

    ListView listViewVentas;
    Conexion conn;
    boolean eliminar;
    boolean ver;
    ArrayList<String> listaInformacion;
    ArrayList<String> listaNombreClientes;
    ArrayList<Venta> listaVentas;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaventas);
        context=getApplicationContext();

        conn= new Conexion(this,"BD",null,1);

        listViewVentas = (ListView) findViewById(R.id.listViewVentas);


        consultarListaVenta();

        ArrayAdapter adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaInformacion);
        listViewVentas.setAdapter(adaptador);

        eliminar = getIntent().getBooleanExtra("Eliminar",false);
        ver = getIntent().getBooleanExtra("Ver",false);

        listViewVentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (eliminar){
                    eliminarVenta(listaVentas.get(pos).getId());
                }else if (ver){
                    Intent i= new Intent(getApplicationContext(),informeventaActivity.class);

                    i.putExtra("idVenta",listaVentas.get(pos).getId());
                    startActivity(i);
                }
                else {
                    Intent i= new Intent(getApplicationContext(),nuevaplanillaActivity.class);

                    i.putExtra("idVenta",listaVentas.get(pos).getId());
                    startActivity(i);
                }
            }
        });
    }

    private void consultarListaVenta() {
        SQLiteDatabase db= conn.getReadableDatabase();

        Venta venta=null;
        String nombreCliente="";
        listaVentas=new ArrayList<Venta>();
        listaNombreClientes= new ArrayList<String>();

        try{
            Cursor cursor=db.rawQuery("SELECT v.id, v.fecha, c.nombre, c.apellido "+
                    "FROM venta v " +
                    "JOIN cliente c ON v.id_cliente = c.id ",null);

            while (cursor.moveToNext()){
                venta = new Venta();
                venta.setId(cursor.getInt(0));
                venta.setFecha(cursor.getString(1));


                nombreCliente= cursor.getString(2)+" "+cursor.getString(3);

                listaNombreClientes.add(nombreCliente);
                listaVentas.add(venta);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();


        for (int i=0; i<listaVentas.size();i++){
            listaInformacion.add(String.valueOf(i+1)+" - "+listaVentas.get(i).getFecha()+" - "+listaNombreClientes.get(i));
        }
    }

    private void eliminarVenta(Integer id){
        Intent i=new Intent(this,ventasActivity.class);
        SQLiteDatabase db= conn.getWritableDatabase();

        String sql= "DELETE FROM venta "+
                "WHERE id="+String.valueOf(id);

        db.execSQL(sql);
        Toast.makeText(getApplicationContext(),"Venta Eliminada",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

}
