package uap.geocolportaje.geocoportaje.ActivitiesPrincipales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevolibroActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listaclientesActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listalibrosActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listaventasActivity;
import uap.geocolportaje.geocoportaje.R;

public class ventasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
    }

    public void onClick(View view){
        Intent i=null;
        switch (view.getId()){
            case R.id.btnNuevaVenta:
                i = new Intent(this,listaclientesActivity.class);
                i.putExtra("VentaCliente",true);
                break;
            case R.id.btnVerVenta:
                i = new Intent(this,listaventasActivity.class);
                i.putExtra("Ver",true);
                break;
            case R.id.btnEliminarVenta:
                i = new Intent(this,listaventasActivity.class);
                i.putExtra("Eliminar",true);
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }
}
