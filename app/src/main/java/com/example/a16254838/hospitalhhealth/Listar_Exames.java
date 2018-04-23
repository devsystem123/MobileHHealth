package com.example.a16254838.hospitalhhealth;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a16254838.hospitalhhealth.adapter.AdapterCursosPersonalizado;
import com.example.a16254838.hospitalhhealth.model.Categoria;
import com.example.a16254838.hospitalhhealth.model.Curso;
import com.example.a16254838.hospitalhhealth.model.EstadoAtual;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Listar_Exames extends AppCompatActivity {

    ListView listView;
    ExameAdapter adapter;
    String retornoJson;
    String API_URL;
    JSONObject objeto;
    Button bt_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar__exames);

        bt_list = findViewById(R.id.bt_list);

        bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Efetuando o login
                //ListarExames();
                Toast.makeText(Listar_Exames.this, "Funciona", Toast.LENGTH_SHORT).show();
            }
        });

        /*bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Efetuando o login

            }
        });*/
    }

    public void ListarExames(){
        ListView lista = findViewById(R.id.lista);

        API_URL = getString(R.string.API_URL);


        lista.setAdapter(adapter);

        listView = findViewById(R.id.listaExames);


        /* Criando adater*/
        adapter = new ExameAdapter(this,new ArrayList<Exame>());

        /*definir adapter na lista*/
        listView.setAdapter(adapter);

        //Roda o comando em segundo plano.
        new AsyncTask<Void, Void, Void>(){

            ArrayList<Exame> lstExame = new ArrayList<Exame>();

            @Override
            protected Void doInBackground(Void... voids) {
                retornoJson = Http.get(API_URL+"/ListarResultadoExame");

                Log.d("Retorno :", retornoJson);

                try {

                    objeto = new JSONObject(retornoJson);

                    JSONObject results = objeto.getJSONObject("results");

                    for (int i=0; i < results.length(); i++){
                        Exame e = Exame.create(
                                results.getInt("idResultadoExame"),
                                results.getInt("idPaciente"),
                                results.getInt("idMedico"),
                                results.getInt("idExame"),
                                results.getString("resultado")
                        );
                        lstExame.add(e);
                    }

                } catch (Exception e) {
                    Log.e("ERRO :",e.getMessage());
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.addAll(lstExame);
                alert("API TESTE", String.valueOf(objeto));
            }
        }.execute();
    }

    private void alert(String titulo, String msg){


        AlertDialog.Builder builder = new AlertDialog.Builder(this );

        builder.setMessage(msg)
                .setTitle(titulo);

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();

        //mostrar o alerta
        dialog.show();
    }

}
