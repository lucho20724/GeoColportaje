package uap.geocolportaje.geocoportaje;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Conexion conn;
    String nombre;
    Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new Conexion(this,"BD",null,1);

        TextView tvNombre = (TextView) findViewById (R.id.lblNombre);

        if (!consultarUsuario()){
            Intent i= new Intent(MainActivity.this,usuarioActivity.class);
            try{
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }
        else{
            SQLiteDatabase db=conn.getReadableDatabase();

            Cursor cursor=db.rawQuery("SELECT nombre "+
                    " FROM usuario ",null);

            try {
                if(cursor !=null) {
                    if(cursor.getCount() > 0){
                        cursor.moveToFirst();
                        nombre=cursor.getString(0);
                    }
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
            finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                tvNombre.setText("Bienvenido "+nombre);
            }
        }
    }

    public void onClick(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.btnVentas: //Evento Registrar
                i = new Intent(MainActivity.this, ventasActivity.class);
                break;

            case R.id.btnMapa:
                i = new Intent(MainActivity.this, MapsActivity.class);
                break;

            case R.id.btnPlanillas:
                i = new Intent (MainActivity.this,planillaActivity.class);
                break;

            case R.id.btnLibros:
                i = new Intent(MainActivity.this,libroActivity.class);
                break;

        }

        if(i !=null){
            startActivity(i);
        }
    }


    private boolean consultarUsuario() {
        int count=0;
        SQLiteDatabase db=conn.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT count(*) "+
                " FROM usuario ",null);

        try {
            if(cursor !=null) {
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    count=cursor.getInt(0);
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        if(count>0){
            return true;
        }else{
            return false;
        }
    }

}