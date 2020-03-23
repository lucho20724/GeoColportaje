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
import uap.geocolportaje.geocoportaje.Entidades.Libro;
import uap.geocolportaje.geocoportaje.Persistencia.pLibro;
import uap.geocolportaje.geocoportaje.R;

public class nuevolibroActivity extends AppCompatActivity {

    EditText campoLibro, campoAutor, campoEditorial;
    int idLibro=0;
    boolean modificar=false;
    Conexion conn;

      /*TODO
    validaciones en los campos -> not null
    boton cancelar
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevolibro);

        conn = new Conexion(this,"BD",null,1);

        campoLibro=(EditText) findViewById(R.id.edLibro);
        campoAutor=(EditText) findViewById(R.id.edAutor);
        campoEditorial=(EditText) findViewById(R.id.edEditorial);

        idLibro = getIntent().getIntExtra("idLibro",0);

        if(idLibro !=0){
            CargarDatos();
            modificar=true;
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnGuardarVenta:
                if(validar()){
                    if (modificar){
                        AlertDialog.Builder alerta= new AlertDialog.Builder(this);//Cartel de confirmacion (AlertDialog)
                        alerta.setMessage("Esta seguro de modificar el libro?");
                        alerta.setTitle("Atención");
                        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                modificarLibro();
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
                        AlertDialog.Builder alerta= new AlertDialog.Builder(this);//Cartel de confirmacion (AlertDialog)
                        alerta.setMessage("Esta seguro de cargar el libro?");
                        alerta.setTitle("Atención");
                        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                guardarLibro();
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
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Debe completar al menos el nombre de libro y el autor",Toast.LENGTH_SHORT).show();
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

        if (!campoLibro.getText().toString().isEmpty() && !campoAutor.getText().toString().isEmpty()){
            valido=true;
        }
        return valido;
    }

    private void CargarDatos() {
        pLibro p = new pLibro();
        Libro libro = p.buscarLibroIdBD(idLibro, getApplicationContext());

        campoLibro.setText(libro.getNombre());
        campoAutor.setText(libro.getAutor());
        campoEditorial.setText(libro.getEditorial());
    }

    private void modificarLibro() {
        Conexion conn = new Conexion(this,"BD",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();

        String sql= "UPDATE libro "+
                "SET nombre= '"+campoLibro.getText().toString()+"', autor= '"+campoAutor.getText().toString()+
                "', editorial= '"+campoEditorial.getText().toString()+"'"+
                "WHERE id= "+String.valueOf(idLibro);

        db.execSQL(sql);
        db.close();
        Toast.makeText(getApplicationContext(),"Libro Modificado",Toast.LENGTH_SHORT).show();

    }

    private void guardarLibro() {
        Libro libro = new Libro();
        pLibro p = new pLibro();

        libro.setNombre(campoLibro.getText().toString());
        libro.setAutor(campoAutor.getText().toString());
        libro.setEditorial(campoEditorial.getText().toString());

        p.guardarLibroBD(libro, getApplicationContext());

        Toast.makeText(getApplicationContext(),"Libro Guardado",Toast.LENGTH_SHORT).show();
    }


}