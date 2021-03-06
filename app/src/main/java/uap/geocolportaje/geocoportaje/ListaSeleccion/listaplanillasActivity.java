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
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaplanillaActivity;
import uap.geocolportaje.geocoportaje.Informes.informeplanillaActivity;
import uap.geocolportaje.geocoportaje.Persistencia.pPlanilla;
import uap.geocolportaje.geocoportaje.R;

public class listaplanillasActivity extends AppCompatActivity {

    ListView listViewPlanillas;
    Conexion conn;
    boolean eliminar, modificar;
    boolean ver;
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
        modificar = getIntent().getBooleanExtra("Modificar",false);

        ver = getIntent().getBooleanExtra("Ver",false);

        listViewPlanillas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (eliminar){
                    eliminarPlanilla(listaPlanillas.get(pos).getId());
                }else if (ver){
                    Intent i= new Intent(getApplicationContext(),informeplanillaActivity.class);

                    i.putExtra("idPlanilla",listaPlanillas.get(pos).getId());
                    startActivity(i);
                } else if (modificar) {
                    Intent i= new Intent(getApplicationContext(),nuevaplanillaActivity.class);

                    i.putExtra("idPlanilla",listaPlanillas.get(pos).getId());
                    i.putExtra("Modificar",true);
                    startActivity(i);
                }
            }
        });
    }

    private void eliminarPlanilla(Integer id){
        pPlanilla p = new pPlanilla();
        Intent i = new Intent(this,planillaActivity.class);
        p.eliminarPlanillaBD(id,context);
        Toast.makeText(getApplicationContext(),"Planilla Eliminada",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaPlanillas.size();i++){
            listaInformacion.add(listaPlanillas.get(i).getId()+" - "+listaPlanillas.get(i).getFecha());
        }
    }


    private void consultarListaPlanilla(){
        listaPlanillas=new ArrayList<Planilla>();
        pPlanilla p = new pPlanilla();

        listaPlanillas=p.listarPlanillasBD(listaPlanillas,context);
        obtenerLista();
    }
}
