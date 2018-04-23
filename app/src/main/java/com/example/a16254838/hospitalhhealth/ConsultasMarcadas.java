package com.example.a16254838.hospitalhhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ConsultasMarcadas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_marcadas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void btnMarcarconsulta(View view) {
        switch (view.getId()) {

            case R.id.btnMarcarconsulta:
                startActivity(new Intent(this,MarcarConsulta.class));
                break;

        }
    }
}
