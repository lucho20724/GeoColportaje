package uap.geocolportaje.geocoportaje.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevolibroActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listalibrosActivity;
import uap.geocolportaje.geocoportaje.R;

public class libroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);
    }

    public void onClick(View view){
        Intent i=null;
        switch (view.getId()){
            case R.id.btnNuevoLibro:
                i = new Intent(this,nuevolibroActivity.class);
                break;
            case R.id.btnEditarLibro:
                i = new Intent(this,listalibrosActivity.class);
                break;
            case R.id.btnEliminarLibro:
                i = new Intent(this,listalibrosActivity.class);
                i.putExtra("Eliminar",true);
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }
}
