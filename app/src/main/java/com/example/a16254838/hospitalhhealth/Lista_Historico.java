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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__historico);
        ListView lista = (ListView) findViewById(R.id.lista);
        List<Curso> cursos = todosOsCursos();
        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(cursos, this);
        lista.setAdapter(adapter);
    }

    /**
     * Exemplo qualquer de devolução de uma lista de cursos.
     * Para esse exemplo será considerado um hard coded.
     *
     * @return lista com todos os cursos
     */
    private List<Curso> todosOsCursos() {
        return new ArrayList<>(Arrays.asList(
                new Curso("Java", "básico de Java", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Android", "boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID)));

    }
}
