package com.example.a16254838.hospitalhhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Consulta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        Log.d("Consulta","Consulta");

    }
    public void selecionarOpcao(View view) {

        switch (view.getId()) {
            case R.id.consultasMarcadas:
                startActivity(new Intent(this,MarcarConsulta.class));
                break;
            case R.id.MarcarConsulta:
                startActivity(new Intent(this,MarcarConsulta.class));
                break;

        }

    }
}


