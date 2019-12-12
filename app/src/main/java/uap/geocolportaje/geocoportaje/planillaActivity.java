package uap.geocolportaje.geocoportaje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        }
        if(i != null){
            startActivity(i);
        }
    }


}