package uap.geocolportaje.geocoportaje.Informes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.R;

public class informeplanillaActivity extends AppCompatActivity {

    TextView txHorasPresentacion, txHorasEntrega, txPresentaciones, txSuscripciones, txFolletos,
    txOraciones, txVentas, txTitulo, txFecha;

    int idPlanilla;

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informeplanilla);

        txHorasPresentacion=(TextView)findViewById(R.id.lblNombre);
        txHorasEntrega=(TextView)findViewById(R.id.lblApellido);
        txPresentaciones=(TextView)findViewById(R.id.lblMail);
        txSuscripciones=(TextView)findViewById(R.id.lblTelefono);
        txFolletos=(TextView)findViewById(R.id.lblFolletos);
        txOraciones=(TextView)findViewById(R.id.lblOraciones);
        txVentas=(TextView)findViewById(R.id.lblVentas);

        txTitulo=(TextView)findViewById(R.id.lblTitulo);
        txFecha=(TextView)findViewById(R.id.lblFecha);

        idPlanilla=getIntent().getIntExtra("idPlanilla",0);

        cargarDatos();

    }

    private void cargarDatos() {
        conn= new Conexion(this,"BD",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametro = {String.valueOf(idPlanilla)};

        try{
            Cursor cursor=db.rawQuery("SELECT horas_presenta, horas_entrega, presentaciones," +
                    " suscripciones, folletos_misioneros, oraciones, cantidad_ventas, fecha, id " +
                    "FROM planilla WHERE id =?",parametro);

            cursor.moveToFirst();

            txHorasPresentacion.setText("Horas de Presentacion: "+String.valueOf(cursor.getInt(0)));
            txHorasEntrega.setText("Horas de Entrega: "+String.valueOf(cursor.getInt(1)));
            txPresentaciones.setText("Presentaciones: "+String.valueOf(cursor.getInt(2)));
            txSuscripciones.setText("Suscripciones :"+String.valueOf(cursor.getInt(3)));
            txFolletos.setText("Folletos: "+String.valueOf(cursor.getInt(4)));
            txOraciones.setText("Oraciones: "+String.valueOf(cursor.getInt(5)));
            txVentas.setText("Ventas :"+String.valueOf(cursor.getInt(6)));
            txFecha.setText(cursor.getString(7));
            txTitulo.setText("Planilla N° "+String.valueOf(cursor.getInt(8)));


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }

    }
}
