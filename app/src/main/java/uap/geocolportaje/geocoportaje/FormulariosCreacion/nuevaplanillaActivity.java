package uap.geocolportaje.geocoportaje.FormulariosCreacion;

import android.database.Cursor;
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

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;
import uap.geocolportaje.geocoportaje.Persistencia.pPlanilla;
import uap.geocolportaje.geocoportaje.R;
import uap.geocolportaje.geocoportaje.Utilidades.Utilidades;


public class nuevaplanillaActivity extends AppCompatActivity {

    EditText campoHorasPresetacion, campoHorasEntrega, campoPresentaciones,
            campoSuscripciones, campoFolletos, campoOraciones, campoVentas, campoDia, campoMes, campoAnio;
    CheckBox checkHoy;

    String fecha;
    SimpleDateFormat sdf;

    int idPlanilla=0;
    boolean modificar=false;

    Conexion conn;

    /*TODO
    validaciones en los campos -> not null
    boton cancelar
     */

    /*TODO
    * Nombre del campo con textview
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevaplanilla);

        conn = new Conexion(this,"BD",null,1);

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

        idPlanilla= getIntent().getIntExtra("idPlanilla",0);

        if (idPlanilla !=0){
            CargarDatos();
            modificar=true;
        }
    }


    /*TODO
    * Mensajes de Confirmacion
    * */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardarPlanilla:
                if(validar()){
                    if (!checkHoy.isChecked()){
                        setearFecha();
                    }
                    if(modificar){
                        modificarPlanilla();
                    }else {
                        guardarPlanilla();
                    }

                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                    break;
                }
                break;

            case R.id.ckHoy:
                mostrarCamposFecha();
                break;

            case R.id.btnCancelar:
                finish();
                break;
        }
    }

    private boolean validar(){
        boolean valido=false;

        if (!campoHorasPresetacion.getText().toString().isEmpty() && !campoHorasEntrega.getText().toString().isEmpty() && !campoPresentaciones.getText().toString().isEmpty() &&
                !campoSuscripciones.getText().toString().isEmpty() && !campoFolletos.getText().toString().isEmpty()
                && !campoOraciones.getText().toString().isEmpty()&& !campoVentas.getText().toString().isEmpty()){
            valido=true;
        }

        return valido;
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

    private void CargarDatos() {
        Planilla planilla;
        pPlanilla p = new pPlanilla();

        planilla = p.buscarPlanillaIdBD(idPlanilla,getApplicationContext());

        campoHorasPresetacion.setText(planilla.getHoras_presenta());
        campoHorasEntrega.setText(planilla.getHoras_entrega());
        campoPresentaciones.setText(planilla.getPresentaciones());
        campoSuscripciones.setText(planilla.getSuscripciones());
        campoFolletos.setText(planilla.getFolletos_misioneros());
        campoOraciones.setText(planilla.getOraciones());
        campoVentas.setText(planilla.getCantidad_ventas());
        /*TODO
        * Tratamiento de la fecha
        * */
    }

    private void guardarPlanilla() {
        Planilla planilla = new Planilla();
        pPlanilla p = new pPlanilla();
        Utilidades ut = new Utilidades();

        planilla.setHoras_presenta(Integer.parseInt(campoHorasPresetacion.getText().toString()));
        planilla.setHoras_entrega(Integer.parseInt(campoHorasEntrega.getText().toString()));
        planilla.setPresentaciones(Integer.parseInt(campoPresentaciones.getText().toString()));
        planilla.setSuscripciones(Integer.parseInt(campoSuscripciones.getText().toString()));
        planilla.setFolletos_misioneros(Integer.parseInt(campoFolletos.getText().toString()));
        planilla.setOraciones(Integer.parseInt(campoOraciones.getText().toString()));
        planilla.setCantidad_ventas(Integer.parseInt(campoVentas.getText().toString()));
        planilla.setFecha(fecha);

        p.guardarPlanillaBD(planilla,ut.ObtenerIdUsuario(getApplicationContext()),getApplicationContext());

        Toast.makeText(getApplicationContext(),"Planilla Guardada",Toast.LENGTH_SHORT).show();
    }

    private void modificarPlanilla(){
        Planilla planilla = new Planilla();
        pPlanilla p = new pPlanilla();
        Utilidades ut = new Utilidades();

        planilla.setHoras_presenta(Integer.parseInt(campoHorasPresetacion.getText().toString()));
        planilla.setHoras_entrega(Integer.parseInt(campoHorasEntrega.getText().toString()));
        planilla.setPresentaciones(Integer.parseInt(campoPresentaciones.getText().toString()));
        planilla.setSuscripciones(Integer.parseInt(campoSuscripciones.getText().toString()));
        planilla.setFolletos_misioneros(Integer.parseInt(campoFolletos.getText().toString()));
        planilla.setOraciones(Integer.parseInt(campoOraciones.getText().toString()));
        planilla.setCantidad_ventas(Integer.parseInt(campoVentas.getText().toString()));
        planilla.setFecha(fecha);

        p.modificarPlanillaBD(planilla, getApplicationContext(), ut.ObtenerIdUsuario(getApplicationContext()), idPlanilla);

        Toast.makeText(getApplicationContext(),"Planilla Modificada",Toast.LENGTH_SHORT).show();
    }


}

