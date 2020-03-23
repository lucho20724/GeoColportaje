package uap.geocolportaje.geocoportaje.Informes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.R;

public class informepuntosActivity extends AppCompatActivity {

    TextView txTitulo, txTituloPunto, txDescripcion, txLatitud,
            txLongitud, txNombreCliente;

    String nombretemp;

    int idPunto, idCliente;

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informepuntos);

        txTitulo=(TextView)findViewById(R.id.lblTitulo);
        txTituloPunto=(TextView)findViewById(R.id.lblTituloPunto);
        txDescripcion=(TextView)findViewById(R.id.lblDescripcion);
        txLatitud=(TextView)findViewById(R.id.lblLatitud);
        txLongitud=(TextView)findViewById(R.id.lblLongitud);
        txNombreCliente=(TextView)findViewById(R.id.lblNombreCliente);

        idPunto=getIntent().getIntExtra("idPunto",0);

        cargarDatos();
    }

    private void cargarDatos() {
        conn= new Conexion(this,"BD",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();

        String sql= "SELECT id, titulo, descripcion, lat, long, id_cliente " +
                "FROM punto " +
                " WHERE id ="+String.valueOf(idPunto);

        try{
            Cursor cursor=db.rawQuery(sql,null);

            cursor.moveToFirst();

            txTitulo.setText("Punto N° "+String.valueOf(cursor.getInt(0)));
            txTituloPunto.setText("Título: "+cursor.getString(1));
            txDescripcion.setText("Descripción:  "+cursor.getString(2));
            txLatitud.setText("Latitud: "+String.valueOf(cursor.getDouble(3)));
            txLongitud.setText("Longitud: "+String.valueOf(cursor.getDouble(4)));

            idCliente=cursor.getInt(5);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }

    private void cargarCliente(){
        nombretemp="";
        conn= new Conexion(this,"BD",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();


        String sql= "SELECT c.nombre, c.apellido " +
                "FROM punto p" +
                "JOIN cliente c ON p.id_cliente = c.id " +
                "WHERE p.id= "+String.valueOf(idPunto);

        try{
            Cursor cursor=db.rawQuery(sql,null);
            cursor.moveToFirst();

            txNombreCliente.setText("Cliente: "+cursor.getString(0)+" "+cursor.getString(1));


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }

    }
}
