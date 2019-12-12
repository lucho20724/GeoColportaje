package uap.geocolportaje.geocoportaje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        }

        if(i!=null){
            startActivity(i);
        }
    }
}
