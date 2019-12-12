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

import uap.geocolportaje.geocoportaje.Activities.libroActivity;
import uap.geocolportaje.geocoportaje.Activities.planillaActivity;
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Libro;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaplanillaActivity;
import uap.geocolportaje.geocoportaje.R;

public class listaplanillasActivity extends AppCompatActivity {

    ListView listViewPlanillas;
    Conexion conn;
    boolean eliminar;
    ArrayList<String> listaInformacion;
    ArrayList<Planilla> listaPlanillas;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaplanillas);
        context=getApplicationContext();

        conn= new Conexion(this,"BD",null,1);

        listViewPlanillas = (ListView) findViewById(R.id.listViewPlanillas);


        consultarListaPlanilla();


        ArrayAdapter adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaInformacion);
        listViewPlanillas.setAdapter(adaptador);

        eliminar = getIntent().getBooleanExtra("Eliminar",false);

        listViewPlanillas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (eliminar){
                    eliminarPlanilla(listaPlanillas.get(pos).getId());
                }else {
                    Intent i= new Intent(getApplicationContext(),nuevaplanillaActivity.class);

                    i.putExtra("idPlanilla",listaPlanillas.get(pos).getId());
                    startActivity(i);
                }
            }
        });
    }

    private void eliminarPlanilla(Integer id){
        Intent i=new Intent(this,planillaActivity.class);
        SQLiteDatabase db= conn.getWritableDatabase();

        String sql= "DELETE FROM planilla "+
                "WHERE id="+String.valueOf(id);

        db.execSQL(sql);
        Toast.makeText(getApplicationContext(),"Planilla Eliminado",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaPlanillas.size();i++){
            listaInformacion.add(listaPlanillas.get(i).getId()+" - "+listaPlanillas.get(i).getFecha());
        }
    }


    private void consultarListaPlanilla(){
        SQLiteDatabase db= conn.getReadableDatabase();

        Planilla planilla=null;
        listaPlanillas=new ArrayList<Planilla>();

        try{
            Cursor cursor=db.rawQuery("SELECT id, horas_presenta, horas_entrega, " +
                    "presentaciones, suscripciones, folletos_misioneros, oraciones, " +
                    "cantidad_ventas, fecha FROM planilla ",null);

            while (cursor.moveToNext()){
                planilla = new Planilla();
                planilla.setId(cursor.getInt(0));
                planilla.setHoras_presenta(cursor.getInt(1));
                planilla.setHoras_entrega(cursor.getInt(2));
                planilla.setPresentaciones(cursor.getInt(3));
                planilla.setSuscripciones(cursor.getInt(4));
                planilla.setFolletos_misioneros(cursor.getInt(5));
                planilla.setOraciones(cursor.getInt(6));
                planilla.setCantidad_ventas(cursor.getInt(7));
                planilla.setFecha(cursor.getString(8));

                listaPlanillas.add(planilla);
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }


        obtenerLista();
    }
}
