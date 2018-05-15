package com.example.a16254838.hospitalhhealth;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarcarConsulta extends AppCompatActivity {

    CalendarView Calendario;
    String API_URL;
    JSONObject objeto;
    Spinner spn1;
    //public String array_spinner[];
    JSONArray arrayEspecilidade;
    JSONArray arrayHora;
    private String nome;
    private ArrayList<String> arrayListSpinner = new ArrayList<String>();
    private ArrayList<String> arrayListSpinnerHora = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);

        API_URL = getString(R.string.API_URL);

        Calendario = findViewById(R.id.Calendario);


        Calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String data;
                if (month <10) {
                     data = year + "-0" + month + "-" + dayOfMonth;
                }else{
                     data = year + "-" + month + "-" + dayOfMonth;
                }
                Log.d("Data Calendario", data );
            }
        });


        new PreencherEspecialidade().execute();
        new PreencherHoras().execute();
    }

    private class PreencherEspecialidade extends AsyncTask<Void,Void,Void>{
            String retornoApi;
            JSONObject results;


            @Override
            protected Void doInBackground(Void... voids) {

                SystemClock.sleep(500);

                retornoApi = Http
                        .get(API_URL+"/Especialidade");


                try {
                    objeto = new JSONObject(retornoApi);
                    //results = objeto.getJSONObject("results");

                    arrayEspecilidade = objeto.getJSONArray("results");

                    for (int i=0; i < arrayEspecilidade.length(); i++){
                        JSONObject item = arrayEspecilidade.getJSONObject(i);
                        arrayListSpinner.add(item.getString("nome"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                PreencherSpinner(arrayListSpinner);
                //alert("API Spinner", String.valueOf(arrayEspecilidade));
            }

    }

    private class PreencherHoras extends AsyncTask<Void,Void,Void>{

        String retornoApi;
        JSONObject results;


        @Override
        protected Void doInBackground(Void... voids) {

            SystemClock.sleep(500);

            retornoApi = Http
                    .get(API_URL+"/ListarHoras");


            try {
                objeto = new JSONObject(retornoApi);
                //results = objeto.getJSONObject("results");

                arrayHora = objeto.getJSONArray("results");

                for (int i=0; i < arrayHora.length(); i++){
                    JSONObject item = arrayHora.getJSONObject(i);
                    arrayListSpinnerHora.add(item.getString("hora"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            PreencherSpinnerHora(arrayListSpinnerHora);
            //alert("API Spinner", String.valueOf(arrayEspecilidade));
        }
    }
    public void SalvarConsulta(View view) {
    }

    public class SpinnerExample extends MarcarConsulta {

        JSONObject results = objeto.getJSONObject("results");

        public SpinnerExample() throws JSONException {
        }

    }

    private void alert(String titulo, String msg){


        AlertDialog.Builder builder = new AlertDialog.Builder( MarcarConsulta.this);

        builder.setMessage(msg)
                .setTitle(titulo);

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();

        //mostrar o alerta
        dialog.show();
    }

    public void PreencherSpinner(ArrayList<String> hora) {

        //Identifica o Spinner no layout
        spn1 = (Spinner) findViewById(R.id.SpinermarcarConsulta1);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hora);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn1.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                nome = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                //Toast.makeText(MarcarConsulta.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

        public void PreencherSpinnerHora(ArrayList<String> Especialidade) {

        //Identifica o Spinner no layout
        spn1 = (Spinner) findViewById(R.id.SpinermarcarConsulta3);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Especialidade);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn1.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                nome = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                //Toast.makeText(MarcarConsulta.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
