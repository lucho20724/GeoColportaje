package uap.geocolportaje.geocoportaje.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaplanillaActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listaplanillasActivity;
import uap.geocolportaje.geocoportaje.R;

public class planillaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planilla);
    }


    public void onClick(View view){
        Intent i=null;
        switch (view.getId()){
            case R.id.btnCrearPlanilla:
                i= new Intent(planillaActivity.this,nuevaplanillaActivity.class);
                break;
            case R.id.btnEditarPlanilla:
                i = new Intent(this,listaplanillasActivity.class);
                break;
            case R.id.btnEliminarPlanilla:
                i = new Intent(this,listaplanillasActivity.class);
                i.putExtra("Eliminar",true);
                break;
            case R.id.btnVerPlanilla:
                i = new Intent(this,listaplanillasActivity.class);
                i.putExtra("Ver",true);
                break;
        }
        if(i != null){
            startActivity(i);
        }
    }


}