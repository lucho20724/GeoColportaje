package uap.geocolportaje.geocoportaje;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class nuevaplanillaActivity extends AppCompatActivity {

    EditText campoHorasPresetacion, campoHorasEntrega, campoPresentaciones,
            campoSuscripciones, campoFolletos, campoOraciones, campoVentas, campoFecha;
    Button botonFecha;
    private int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_nuevaplanilla);*/

        campoHorasPresetacion = (EditText) findViewById(R.id.edHorasEntrega);
        campoHorasEntrega = (EditText) findViewById(R.id.edHorasEntrega);
        campoPresentaciones = (EditText) findViewById(R.id.edPresentaciones);
        campoSuscripciones = (EditText) findViewById(R.id.edSuscripciones);
        campoFolletos = (EditText) findViewById(R.id.edFolletos);
        campoOraciones = (EditText) findViewById(R.id.edOraciones);
        campoVentas = (EditText) findViewById(R.id.edVentas);
        campoFecha = (EditText) findViewById(R.id.edFecha);

        botonFecha = (Button) findViewById(R.id.btnFecha);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardarPlanilla:
                guardarPlanilla();
                break;
        }
    }

    private void guardarPlanilla() {
        Conexion con = new Conexion(this, "BD", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();

        Utilidades ut = new Utilidades();

        String sql = "INSERT INTO planilla " +
                "(horas_presenta, horas_entrega, presentaciones, suscripciones, folletos_misioneros, oraciones, cantidad_ventas, fecha, id_usuario ) " +
                "VALUES (" + campoHorasPresetacion.getText().toString() + ", " + campoHorasEntrega.getText().toString() + ", " +
                campoPresentaciones.getText().toString() + ", " + campoSuscripciones.getText().toString() + ", " + campoFolletos.getText().toString() + ", " +
                campoOraciones.getText().toString() + ", " + campoVentas.getText().toString() + ", " + campoFecha.getText().toString() + ", " + ut.ObtenerIdUsuario(this) +
                ")";

        try {
            db.execSQL(sql);
            Toast.makeText(getApplicationContext(), "Planilla Guardada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }
}

