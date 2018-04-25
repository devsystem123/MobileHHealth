package com.example.a16254838.hospitalhhealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.a16254838.hospitalhhealth.adapter.AdapterCursosPersonalizado;
import com.example.a16254838.hospitalhhealth.model.Categoria;
import com.example.a16254838.hospitalhhealth.model.Curso;
import com.example.a16254838.hospitalhhealth.model.EstadoAtual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lista_Historico extends AppCompatActivity {
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__historico);

        lista = findViewById(R.id.lista_consulta);

    }

}
