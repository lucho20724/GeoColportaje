package uap.geocolportaje.geocoportaje.Informes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.R;

public class informeclienteActivity extends AppCompatActivity {

    TextView txNombre, txApellido, txMail, txTelefono;

    int idCliente;

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informecliente);

        txNombre=(TextView)findViewById(R.id.lblNombre);
        txApellido=(TextView)findViewById(R.id.lblApellido);
        txMail=(TextView)findViewById(R.id.lblMail);
        txTelefono=(TextView)findViewById(R.id.lblTelefono);


        idCliente=getIntent().getIntExtra("idCliente",0);

        cargarDatos();
    }


    private void cargarDatos() {
        conn= new Conexion(this,"BD",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametro = {String.valueOf(idCliente)};

        try{
            Cursor cursor=db.rawQuery("SELECT nombre, apellido, mail, telefono " +
                    "FROM cliente WHERE id =?",parametro);

            cursor.moveToFirst();

            txNombre.setText("Nombre del cliente: "+cursor.getString(0));
            txApellido.setText("Apellido del cliente: "+cursor.getString(1));
            txMail.setText("Mail del cliente: "+cursor.getString(2));
            txTelefono.setText("Teléfono dell cliente :"+cursor.getString(3));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
