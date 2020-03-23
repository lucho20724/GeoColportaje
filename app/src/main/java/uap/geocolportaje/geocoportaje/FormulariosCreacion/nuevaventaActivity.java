package uap.geocolportaje.geocoportaje.FormulariosCreacion;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uap.geocolportaje.geocoportaje.ActivitiesPrincipales.ventasActivity;
import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;
import uap.geocolportaje.geocoportaje.Entidades.Libro;
import uap.geocolportaje.geocoportaje.Entidades.Venta;
import uap.geocolportaje.geocoportaje.Persistencia.pVenta;
import uap.geocolportaje.geocoportaje.R;

public class nuevaventaActivity extends AppCompatActivity {

    ListView listViewLibrosSel;
    TextView lblCliente;
    EditText campoPrecio;

    int idCliente;
    ArrayList<Integer> idsLibros;
    ArrayAdapter adaptador;

    //Spinner
    Spinner comboCuotas;
    String cuota;
    //--------------

    //Fecha
    SimpleDateFormat sdf;
    String fecha;

    Context context;


    Cliente cliente;

    Conexion conn;

    ArrayList<String> listaInfoLibros;
    ArrayList<Libro> listaLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevaventa);

        conn= new Conexion(this,"BD",null,1);
        context= getApplicationContext();

        listViewLibrosSel=(ListView)findViewById(R.id.listViewLibrosSel);
        lblCliente=(TextView)findViewById(R.id.lblNombreCliente);
        comboCuotas=(Spinner)findViewById(R.id.spinerCuotas);
        campoPrecio=(EditText)findViewById(R.id.edPrecio);

        idCliente=getIntent().getIntExtra("idCliente",0);
        idsLibros = getIntent().getIntegerArrayListExtra("idsLibros");

        cliente=consultarCliente();
        consultarListaLibroSel();

        //Setear nombre del cliente
        lblCliente.setText("Cliente: "+cliente.getNombre()+" "+cliente.getApellido());

        //Lista de libros
        adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaInfoLibros);
        listViewLibrosSel.setChoiceMode(ListView.CHOICE_MODE_NONE);
        listViewLibrosSel.setAdapter(adaptador);

        /*Fecha Actual*/
        sdf=new SimpleDateFormat("yyy-MM-dd");
        fecha =sdf.format(new Date());

        //Cantidad de cuotas - spinner
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.combo_cuotas,android.R.layout.simple_spinner_item);
        comboCuotas.setAdapter(adapter);

        comboCuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                cuota=parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onClick(View view){
        Intent i=null;
        switch (view.getId()){
            case R.id.btnGuardarVenta:
                if(validar()) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);//Cartel de confirmacion (AlertDialog)
                    alerta.setMessage("Esta seguro de guardar la venta? POSTERIORMENTE NO PODRA MODIFICARLA.");
                    alerta.setTitle("Atención");
                    alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            transformarCuota();
                            guardarVenta();
                            guardarVentaLibro();
                            finish();
                        }
                    });
                    alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog dialog = alerta.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                    break;
                }

                break;
            case R.id.btnCancelar:
                finish();
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }

    private boolean validar(){
        boolean valido=false;

        if (!campoPrecio.getText().toString().isEmpty()){
            valido=true;
        }
        return valido;
    }


    private void  transformarCuota(){
        switch (cuota){
            case "1 Cuota":
                cuota="1";
                break;
            case "2 Cuotas":
                cuota="2";
                break;
            case "3 Cuotas":
                cuota="3";
                break;
        }
    }

    private void guardarVenta() {
        Venta venta = new Venta();

        venta.setPreciototal(Double.parseDouble(campoPrecio.getText().toString()));
        venta.setCuotas(Integer.parseInt(cuota));
        venta.setFecha(fecha);
        venta.setId_cliente(idCliente);

        pVenta p = new pVenta();

        try {
            p.guardarVentaBD(venta,context);

        }catch (Exception e){

        }

    }

    private void guardarVentaLibro() {
        pVenta p= new pVenta();
        int idVenta=p.ultimaVentaID(context);

        for(int i=0;i<idsLibros.size();i++) {
            p.guardarVentaLibroDB(idVenta,idsLibros.get(i),context);

        }
    }

    private void obtenerLista() {
        listaInfoLibros = new ArrayList<String>();

        for (int i=0; i<listaLibros.size();i++){
            listaInfoLibros.add(String.valueOf(i+1)+" - "+listaLibros.get(i).getNombre()+" - "+listaLibros.get(i).getAutor());
        }
    }

    private void consultarListaLibroSel() {

        /*TODO
        realizar esta funcion en la clase de persistencia
         */
        SQLiteDatabase db= conn.getReadableDatabase();

        Libro libro = null;
        listaLibros = new ArrayList<Libro>();

        try{
            Cursor cursor= db.rawQuery("SELECT nombre, autor FROM libro WHERE id IN ("+transformarIds()+")",null);

            while (cursor.moveToNext()){
                libro = new Libro();
                libro.setNombre(cursor.getString(0));
                libro.setAutor(cursor.getString(1));

                listaLibros.add(libro);
            }
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }

        obtenerLista();
    }

    private String transformarIds(){
        String parametros="";
        for (int i=0;i<idsLibros.size();i++){
            if(i==0){
                parametros=String.valueOf(idsLibros.get(i));

            }else{
                parametros=parametros+", "+String.valueOf(idsLibros.get(i));
            }
        }
        return parametros;
    }

    private Cliente consultarCliente() {
        Cliente cliente = null;
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametro = {String.valueOf(idCliente)};

        try{
            Cursor cursor = db.rawQuery("SELECT id, nombre, apellido, mail, telefono FROM cliente" +
                    " WHERE id=?", parametro);

            cursor.moveToFirst();

            cliente = new Cliente();
            cliente.setId(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setApellido(cursor.getString(2));
            cliente.setMail(cursor.getString(3));
            cliente.setTelefono(cursor.getString(4));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG);
        }
        
        return cliente;
    }


}
