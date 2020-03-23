package uap.geocolportaje.geocoportaje.Informes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.R;

public class informeventaActivity extends AppCompatActivity {

    TextView txtTitulo, txtFecha, txtNombrCliente, txtCuotas, txtPrecio;
    ListView listViewLibrosVenta;

    String nombretemp;
    ArrayList<String> listaNombreLibros;

    ArrayAdapter adaptador;


    int idVenta;

    int idCliente;

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informeventa);

        txtTitulo=(TextView)findViewById(R.id.lblTitulo);
        txtFecha=(TextView)findViewById(R.id.lblFecha);
        txtNombrCliente=(TextView)findViewById(R.id.lblMail);
        txtCuotas=(TextView)findViewById(R.id.lblApellido);
        txtPrecio=(TextView)findViewById(R.id.lblNombre);
        listViewLibrosVenta=(ListView)findViewById(R.id.listViewLibrosVenta);

        idVenta=getIntent().getIntExtra("idVenta",0);


        cargarDatos();
        cargarCliente();
        cargarLibros();

        adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaNombreLibros);
        listViewLibrosVenta.setChoiceMode(ListView.CHOICE_MODE_NONE);
        listViewLibrosVenta.setAdapter(adaptador);
    }

    private void cargarLibros() {
        conn= new Conexion(this,"BD",null,1);

        listaNombreLibros = new ArrayList<String>();
        String nombreLibro="";

        SQLiteDatabase db=conn.getReadableDatabase();

        String sql= "SELECT l.nombre, l.autor " +
                "FROM libro l " +
                "JOIN venta_libro vl ON l.id=vl.id_libro" +
                " WHERE vl.id_venta ="+String.valueOf(idVenta);

        try{
            Cursor cursor=db.rawQuery(sql,null);
            while (cursor.moveToNext()){
                nombreLibro=cursor.getString(0)+" ("+cursor.getString(1)+")";

                listaNombreLibros.add(nombreLibro);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }

    private void cargarDatos() {
        conn= new Conexion(this,"BD",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();

        String sql= "SELECT v.id, v.fecha, v.cuotas, v.preciototal, v.id_cliente " +
                    "FROM venta v " +
                    " WHERE v.id ="+String.valueOf(idVenta);

        try{
            Cursor cursor=db.rawQuery(sql,null);

            cursor.moveToFirst();

            txtTitulo.setText("Venta N° "+String.valueOf(cursor.getInt(0)));
            txtFecha.setText("Fecha de la compra:  "+cursor.getString(1));
            txtCuotas.setText("Cantidad de cuotas: ° "+String.valueOf(cursor.getInt(2)));
            txtPrecio.setText("Precio: $ "+String.valueOf(cursor.getDouble(3)));
            idCliente=cursor.getInt(4);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }

    private void cargarCliente(){
        nombretemp="";
        conn= new Conexion(this,"BD",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();


        String sql= "SELECT v.id, v.fecha, c.nombre, c.apellido "+
                "FROM venta v " +
                "JOIN cliente c ON v.id_cliente = c.id " +
                "WHERE v.id ="+String.valueOf(idVenta);


        try{
            Cursor cursor=db.rawQuery(sql,null);
            cursor.moveToFirst();

            txtNombrCliente.setText("Cliente: "+cursor.getString(2)+" "+cursor.getString(3));


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }

    }
}
