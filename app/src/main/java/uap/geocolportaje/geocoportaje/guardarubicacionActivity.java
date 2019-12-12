package uap.geocolportaje.geocoportaje;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class guardarubicacionActivity extends AppCompatActivity {

    ArrayList<String> coordenadas;
    EditText campoDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardarubicacion);

        coordenadas = (ArrayList<String>) getIntent().getStringArrayListExtra("coordenadas");
        campoDescripcion= (EditText) findViewById(R.id.campoDescripcion);
    }

    public  void onClick(View view){
        switch (view.getId()){
            case R.id.btnPrueba:
               RecuperarCoordenadas(coordenadas);
                break;
            case R.id.btnGuardar:
                try {
                    GuardarPuntos(coordenadas);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    public void RecuperarCoordenadas(ArrayList<String> lista){
        double lng,lat;
        String mensaje;
        lat= Double.parseDouble(lista.get(0));
        lng= Double.parseDouble(lista.get(1));

        mensaje= "Lat: "+String.valueOf(lat)+" -  Long: "+ String.valueOf(lng);

    }

    private void GuardarPuntos(ArrayList<String> lista){
        Conexion con= new Conexion(this,"BD",null,1);
        double lng,lat;
        String mensaje;
        lat= Double.parseDouble(lista.get(0));
        lng= Double.parseDouble(lista.get(1));

        SQLiteDatabase db= con.getWritableDatabase();

        String sql= "INSERT INTO punto ( "+
                "lat, long, descripcion) "+
                "VALUES ("+lat+" ,"+lng+" ,'"+campoDescripcion.getText().toString()+"')";

        try{
            db.execSQL(sql);
            db.close();
            Toast.makeText(getApplicationContext(),"Punto Guardado",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
