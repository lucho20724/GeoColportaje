package uap.geocolportaje.geocoportaje;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import uap.geocolportaje.geocoportaje.Utilidades.Utilidades;


public class nuevaplanillaActivity extends AppCompatActivity {

    EditText campoHorasPresetacion, campoHorasEntrega, campoPresentaciones,
            campoSuscripciones, campoFolletos, campoOraciones, campoVentas, campoDia, campoMes, campoAnio;
    CheckBox checkHoy;

    String fecha;
    SimpleDateFormat sdf;

    /*TODO
    validaciones en los campos -> not null
    boton cancelar
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevaplanilla);

        /*Referencia de componentes*/
        campoHorasPresetacion = (EditText) findViewById(R.id.edHorasPresentacion);
        campoHorasEntrega = (EditText) findViewById(R.id.edHorasEntrega);
        campoPresentaciones = (EditText) findViewById(R.id.edPresentaciones);
        campoSuscripciones = (EditText) findViewById(R.id.edSuscripciones);
        campoFolletos = (EditText) findViewById(R.id.edFolletos);
        campoOraciones = (EditText) findViewById(R.id.edOraciones);
        campoVentas = (EditText) findViewById(R.id.edVentas);

        campoDia = (EditText) findViewById(R.id.edDia);
        campoMes = (EditText) findViewById(R.id.edMes);
        campoAnio = (EditText) findViewById(R.id.edAnio);


        checkHoy = (CheckBox) findViewById(R.id.ckHoy);

        checkHoy.setChecked(true);

        campoAnio.setVisibility(View.INVISIBLE);
        campoDia.setVisibility(View.INVISIBLE);
        campoMes.setVisibility(View.INVISIBLE);
        //-------------------------------------------------------------------------

        /*Fecha Actual*/
        sdf=new SimpleDateFormat("yyy-MM-dd");
        fecha =sdf.format(new Date());
        //-------------------------------------------------------------------------

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardarPlanilla:
                if (!checkHoy.isChecked()){
                    setearFecha();
                }
                guardarPlanilla();
                break;

            case R.id.ckHoy:
                mostrarCamposFecha();
                break;
        }
    }

    private void setearFecha() {
        String dia,mes,anio;

        dia=campoDia.getText().toString();
        mes=campoMes.getText().toString();
        anio=campoAnio.getText().toString();

        fecha= anio+"-"+mes+"-"+dia;

        Toast.makeText(getApplicationContext(),"Nueva fecha: "+fecha, Toast.LENGTH_SHORT).show();


    }

    private void mostrarCamposFecha() {
        if (checkHoy.isChecked()){
            campoAnio.setVisibility(View.INVISIBLE);
            campoDia.setVisibility(View.INVISIBLE);
            campoMes.setVisibility(View.INVISIBLE);
        }else{
            campoAnio.setVisibility(View.VISIBLE);
            campoDia.setVisibility(View.VISIBLE);
            campoMes.setVisibility(View.VISIBLE);
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
                campoOraciones.getText().toString() + ", " + campoVentas.getText().toString() + ", '" + fecha + "', " + ut.ObtenerIdUsuario(this) +
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

