package uap.geocolportaje.geocoportaje;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class usuarioActivity extends AppCompatActivity {

    EditText campoNombre, campoApellido, campoMail, campoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        campoNombre = (EditText)findViewById(R.id.edNombre);
        campoApellido = (EditText)findViewById(R.id.edApellido);
        campoMail = (EditText)findViewById(R.id.edMail);
        campoTelefono = (EditText)findViewById(R.id.edTel);
    }

    public void onClick(View view){
        Intent i = null;
        switch (view.getId()){
            case R.id.btnGuardarUsuario:
                guardarUsuario();
                i = new Intent(usuarioActivity.this,MainActivity.class);
                i.putExtra("NombreUsuario",campoNombre.getText().toString());
                break;
        }
        if(i!=null){
            startActivity(i);
        }
    }

    private void guardarUsuario() {
        Conexion con= new Conexion(this,"BD",null,1);
        SQLiteDatabase db=con.getWritableDatabase();

        String sql= "INSERT INTO usuario "+
                "(nombre, apellido, mail, telefono) "+
                "VALUES ('"+campoNombre.getText().toString()+"', '"+campoApellido.getText().toString()+"', '"+
                campoMail.getText().toString()+"', '"+campoTelefono.getText().toString()+"')";

        db.execSQL(sql);
        db.close();

        Toast.makeText(getApplicationContext(),"Usuario guardado",Toast.LENGTH_SHORT).show();
    }
}