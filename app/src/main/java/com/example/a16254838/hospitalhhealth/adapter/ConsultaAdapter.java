package com.example.a16254838.hospitalhhealth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a16254838.hospitalhhealth.ClassConsulta;
import com.example.a16254838.hospitalhhealth.R;

import java.util.ArrayList;

/**
 * Created by 16254866 on 25/04/2018.
 */

public class ConsultaAdapter extends ArrayAdapter<ClassConsulta> {

    ImageView ImgExame;
    TextView NomeExame;
    TextView DescExame;

    public ConsultaAdapter(Context context, ArrayList<ClassConsulta> lstConsul){
        super(context, 0, lstConsul);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){

        View v = convertView;

        if (v == null){

            v = LayoutInflater.from(getContext()).inflate(R.layout.lista_exames,null);

        }
        ClassConsulta item = getItem(position);

        ImgExame = v.findViewById(R.id.lista_curso_personalizada_imagem);
        NomeExame = v.findViewById(R.id.lista_curso_personalizada_nome);
        DescExame = v.findViewById(R.id.lista_curso_personalizada_descricao);

        DescExame.setText(item.getRelatorio());
        NomeExame.setText(item.getNome());



        /*
        txt_nome.setText(item.getNome());
        preco.setText("R$ : "+item.getPreco());
        txt_desc.setText(item.getDescricao());*/



        return v;
    }
}
