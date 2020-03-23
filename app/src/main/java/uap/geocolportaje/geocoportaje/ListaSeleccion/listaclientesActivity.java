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

import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.clienteActivity;
import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.libroActivity;
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;
import uap.geocolportaje.geocoportaje.Entidades.Libro;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevoclienteActivity;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevolibroActivity;
import uap.geocolportaje.geocoportaje.Informes.informeclienteActivity;
import uap.geocolportaje.geocoportaje.R;

public class listaclientesActivity extends AppCompatActivity {

    ListView listViewClientes;
    Conexion conn;
    boolean eliminar, ventaCliente, verCliente;
    ArrayList<String> listaInformacion;
    ArrayList<Cliente> listaClientes;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaclientes);

        context = getApplicationContext();

        conn = new Conexion(this, "BD", null, 1);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes);

        consultarListaCliente();

        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, listaInformacion);
        listViewClientes.setAdapter(adaptador);

        eliminar = getIntent().getBooleanExtra("Eliminar", false);
        ventaCliente = getIntent().getBooleanExtra("VentaCliente",false);
        verCliente = getIntent().getBooleanExtra("Ver",false);

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (eliminar) {
                    eliminarCliente(listaClientes.get(pos).getId());
                }else if(ventaCliente){
                    Intent i = new Intent(getApplicationContext(),listalibrosActivity.class);

                    i.putExtra("idCliente", listaClientes.get(pos).getId());
                    startActivity(i);
                }else if(verCliente){
                        Intent i = new Intent(getApplicationContext(),informeclienteActivity.class);

                        i.putExtra("idCliente", listaClientes.get(pos).getId());
                        startActivity(i);
                }
                else {
                    Intent i = new Intent(getApplicationContext(), nuevoclienteActivity.class);

                    i.putExtra("idCliente", listaClientes.get(pos).getId());
                    startActivity(i);
                }
            }
        });
    }

    private void eliminarCliente(Integer id){
        Intent i=new Intent(this,clienteActivity.class);
        SQLiteDatabase db= conn.getWritableDatabase();

        String sql= "DELETE FROM cliente "+
                "WHERE id="+String.valueOf(id);

        db.execSQL(sql);
        Toast.makeText(getApplicationContext(),"Cliente Eliminado",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaClientes.size();i++){
            listaInformacion.add(listaClientes.get(i).getNombre()+" - "+listaClientes.get(i).getApellido());
        }
    }

    private void consultarListaCliente(){
        SQLiteDatabase db= conn.getReadableDatabase();

        Cliente cliente=null;
        listaClientes=new ArrayList<Cliente>();

        try{
            Cursor cursor=db.rawQuery("SELECT id, nombre, apellido FROM cliente",null);

            while (cursor.moveToNext()){
                cliente = new Cliente();
                cliente.setId(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
                cliente.setApellido(cursor.getString(2));

                listaClientes.add(cliente);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

        obtenerLista();
    }





}
