package uap.geocolportaje.geocoportaje.ActivitiesPrincipales;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Usuario;
import uap.geocolportaje.geocoportaje.Persistencia.pUsuario;
import uap.geocolportaje.geocoportaje.R;

public class usuarioActivity extends AppCompatActivity {

    EditText campoNombre, campoApellido, campoMail, campoTelefono;

    Conexion conn;

    boolean modiificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        conn = new Conexion(this,"BD",null,1);

        campoNombre = (EditText)findViewById(R.id.edNombre);
        campoApellido = (EditText)findViewById(R.id.edApellido);
        campoMail = (EditText)findViewById(R.id.edMail);
        campoTelefono = (EditText)findViewById(R.id.edTel);

        modiificar= getIntent().getBooleanExtra("Modificar",false);

        if(modiificar){
            cargarDatos();
        }
    }


    public void onClick(View view){
        Intent i = null;
        switch (view.getId()){
            case R.id.btnGuardarCliente:
                if(modiificar){
                    if (validar()) {
                        modificarUsuario();
                    }else{
                        Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }else{
                    if (validar()) {
                        guardarUsuario();
                    }else{
                        Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

                i = new Intent(usuarioActivity.this,MainActivity.class);
                i.putExtra("NombreUsuario",campoNombre.getText().toString());
                break;
        }
        if(i!=null){
            startActivity(i);
        }
    }

    private boolean validar() {
        boolean valido=false;

        if (!campoNombre.getText().toString().isEmpty() && !campoApellido.getText().toString().isEmpty() && !campoMail.getText().toString().isEmpty() && !campoTelefono.getText().toString().isEmpty()){
            valido=true;
        }

        return valido;
    }

    private void modificarUsuario() {
        Usuario usuario = new Usuario();
        pUsuario p = new pUsuario();

        usuario.setNombre(campoNombre.getText().toString());
        usuario.setApellido(campoApellido.getText().toString());
        usuario.setMail(campoMail.getText().toString());
        usuario.setTelefono(campoTelefono.getText().toString());

        p.modificarUsuarioBD(usuario,this);
        Toast.makeText(getApplicationContext(),"Usuario modificado",Toast.LENGTH_SHORT).show();
    }

    private void guardarUsuario() {
        pUsuario p = new pUsuario();
        Usuario usuario = new Usuario();

        usuario.setNombre(campoNombre.getText().toString());
        usuario.setApellido(campoApellido.getText().toString());
        usuario.setMail(campoMail.getText().toString());
        usuario.setTelefono(campoTelefono.getText().toString());

        p.guardarUsuarioBD(usuario, this);
        Toast.makeText(getApplicationContext(),"Usuario guardado",Toast.LENGTH_SHORT).show();
    }

    private void cargarDatos() {
        Usuario usuario;
        pUsuario p = new pUsuario();

        usuario=p.buscarUsuarioDB(getApplicationContext());

        campoNombre.setText(usuario.getNombre().toString());
        campoApellido.setText(usuario.getApellido().toString());
        campoMail.setText(usuario.getMail().toString());
        campoTelefono.setText(usuario.getTelefono().toString());

    }
}