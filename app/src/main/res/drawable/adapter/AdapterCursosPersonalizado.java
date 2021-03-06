package br.com.alura.cursos.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.cursos.R;

/**
 * Created by alex on 02/07/17.
 */

public class AdapterCursosPersonalizado extends BaseAdapter {

    private final List<br.com.alura.cursos.model.Curso> cursos;
    private final Activity act;

    public AdapterCursosPersonalizado(List<br.com.alura.cursos.model.Curso> cursos, Activity act) {
        this.cursos = cursos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.lista_curso_personalizada, parent, false);

        br.com.alura.cursos.model.Curso curso = cursos.get(position);
        
        TextView nome = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_nome);
        TextView descricao = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_descricao);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_curso_personalizada_imagem);

        nome.setText(curso.getNome());
        descricao.setText(curso.getDescricao());

        br.com.alura.cursos.model.Categoria categoria = curso.getCategoria();

        if (categoria.equals(br.com.alura.cursos.model.Categoria.JAVA)) {
            imagem.setImageResource(R.drawable.java);
        } else if (categoria.equals(br.com.alura.cursos.model.Categoria.ANDROID)) {
            imagem.setImageResource(R.drawable.android);
        } else if (categoria.equals(br.com.alura.cursos.model.Categoria.HTML)) {
            imagem.setImageResource(R.drawable.html);
        }

        return view;
    }
}
