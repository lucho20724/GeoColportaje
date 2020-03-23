package uap.geocolportaje.geocoportaje.ActivitiesPrincipales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevoclienteActivity;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevolibroActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listaclientesActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listalibrosActivity;
import uap.geocolportaje.geocoportaje.R;

public class clienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
    }

    public void  onClick(View view){
        Intent i = null;
        switch (view.getId()){
            case R.id.btnCrearCliente:
                i = new Intent(this,nuevoclienteActivity.class);
                break;
            case R.id.btnEditarCliente:
                i = new Intent(this,listaclientesActivity.class);
                break;
            case R.id.btnEliminarCliente:
                i = new Intent(this,listaclientesActivity.class);
                i.putExtra("Eliminar",true);
                break;
            case R.id.btnVerCliente:
                i = new Intent(this,listaclientesActivity.class);
                i.putExtra("Ver",true);
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }
}
