package uap.geocolportaje.geocoportaje.FormulariosCreacion;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;
import uap.geocolportaje.geocoportaje.Persistencia.pCliente;
import uap.geocolportaje.geocoportaje.R;

public class nuevoclienteActivity extends AppCompatActivity {

    EditText campoNombre, campoApellido, campoMail, campoTelefono;
    int idCliente=0;
    boolean modificar=false;
    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevocliente);

        conn = new Conexion(this,"BD",null,1);

        campoNombre = (EditText)findViewById(R.id.edNombre);
        campoApellido = (EditText)findViewById(R.id.edApellido);
        campoMail = (EditText)findViewById(R.id.edMail);
        campoTelefono = (EditText)findViewById(R.id.edTel);

        idCliente = getIntent().getIntExtra("idCliente",0);

        if(idCliente !=0){
            CargarDatos();
            modificar=true;
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnGuardarCliente:
                if(validar()){
                    if (modificar){
                        AlertDialog.Builder alerta= new AlertDialog.Builder(this);//Cartel de confirmacion (AlertDialog)
                        alerta.setMessage("Esta seguro de modificar al cliente?");
                        alerta.setTitle("Atención");
                        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                modificarCliente();
                                finish();
                            }
                        });
                        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog dialog=alerta.create();
                        dialog.show();
                    }else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(this);//Cartel de confirmacion (AlertDialog)
                        alerta.setMessage("Esta seguro de cargar el cliente?");
                        alerta.setTitle("Atención");
                        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                guardarCliente();
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
    }

    private boolean validar(){
        boolean valido=false;

        if (!campoNombre.getText().toString().isEmpty() && !campoApellido.getText().toString().isEmpty() && !campoMail.getText().toString().isEmpty() &&
                !campoTelefono.getText().toString().isEmpty()){
            valido=true;
        }
        return valido;
    }



    private void CargarDatos() {
        pCliente p = new pCliente();
        Cliente cliente = p.buscarClienteIdBD(idCliente,getApplicationContext());

        campoNombre.setText(cliente.getNombre());
        campoApellido.setText(cliente.getApellido());
        campoMail.setText(cliente.getMail());
        campoTelefono.setText(cliente.getTelefono());
    }

    private void modificarCliente(){
        Cliente cliente = new Cliente();
        pCliente p = new pCliente();

        cliente.setNombre(campoNombre.getText().toString());
        cliente.setApellido(campoApellido.getText().toString());
        cliente.setMail(campoMail.getText().toString());
        cliente.setTelefono(campoTelefono.getText().toString());

        p.modificarClienteBD(cliente,getApplicationContext(),idCliente);

        Toast.makeText(getApplicationContext(),"Cliente Modificado",Toast.LENGTH_SHORT).show();

    }

    private void guardarCliente(){
        Cliente cliente = new Cliente();
        pCliente p = new pCliente();

        cliente.setNombre(campoNombre.getText().toString());
        cliente.setApellido(campoApellido.getText().toString());
        cliente.setMail(campoMail.getText().toString());
        cliente.setTelefono(campoTelefono.getText().toString());

        p.guardarClienteDB(cliente, getApplicationContext());

        Toast.makeText(getApplicationContext(),"Libro Guardado",Toast.LENGTH_SHORT).show();

    }
}
