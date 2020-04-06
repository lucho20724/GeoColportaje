package uap.geocolportaje.geocoportaje.FormulariosCreacion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.MapsActivity;
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;
import uap.geocolportaje.geocoportaje.Entidades.Punto;
import uap.geocolportaje.geocoportaje.Persistencia.pPunto;
import uap.geocolportaje.geocoportaje.R;

public class nuevopuntoActivity extends AppCompatActivity {

    ArrayList<String> coordenadas;
    EditText campoDescripcion, campoTitulo;
    Spinner comboCliente;

    ArrayList<String> listaClientes;
    ArrayList<Cliente> clienteList;

    int idCliente;

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevaubicacion);

        coordenadas = (ArrayList<String>) getIntent().getStringArrayListExtra("coordenadas");
        campoDescripcion= (EditText) findViewById(R.id.campoDescripcion);
        campoTitulo= (EditText)findViewById(R.id.campoTítulo);

        comboCliente= (Spinner) findViewById(R.id.comboCliente);

        consultarListaClientes();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaClientes);

        comboCliente.setAdapter(adaptador);

        comboCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long l) {
                if (posicion !=0) {
                    idCliente = clienteList.get(posicion-1).getId();
                }else {
                    idCliente = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public  void onClick(View view){
        Intent i=null;
        switch (view.getId()){
            case R.id.btnGuardar:
                i = new Intent(getApplicationContext(), MapsActivity.class);
                GuardarPuntos(coordenadas);
                finish();
                break;
        }
        if(i!=null){
            startActivity(i);
        }
    }

    private void consultarListaClientes() {
        pPunto p = new pPunto();
        clienteList = p.listarClientesSeleccionBD(this);
        obtenerLista();
    }

    private void obtenerLista() {
        listaClientes = new ArrayList<String>();
        listaClientes.add("Seleccione un cliente");

        for (int i=0;i<clienteList.size();i++){
            listaClientes.add(clienteList.get(i).getNombre()+" "+clienteList.get(i).getApellido());
        }
    }


    public void RecuperarCoordenadas(ArrayList<String> lista){
        double lng,lat;
        String mensaje;
        lat= Double.parseDouble(lista.get(0));
        lng= Double.parseDouble(lista.get(1));

        mensaje= "Lat: "+String.valueOf(lat)+" -  Long: "+ String.valueOf(lng);

    }

    private void GuardarPuntos(ArrayList<String> lista){
        Punto punto = new Punto();
        pPunto p = new pPunto();

        punto.setLatitud(Double.parseDouble(lista.get(0)));
        punto.setLongitud(Double.parseDouble(lista.get(1)));
        punto.setDescripcion(campoDescripcion.getText().toString());
        punto.setTitulo(campoTitulo.getText().toString());
        if(idCliente!=0){
            punto.setId_cliente(idCliente);
        }else{
            punto.setId_cliente(0);
        }

        p.guardarPuntoBD(punto,this);
        Toast.makeText(getApplicationContext(),"Punto Guardado",Toast.LENGTH_SHORT).show();
    }
}
