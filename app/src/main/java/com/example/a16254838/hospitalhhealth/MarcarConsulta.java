package com.example.a16254838.hospitalhhealth;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class MarcarConsulta extends AppCompatActivity {

    /*IdSpinner Especialidade*/
    String idEspecialidade;

    Boolean dataValida = false;
    CalendarView Calendario;
    String API_URL;
    JSONObject objeto;
    Spinner especialidade, hora;
    //public String array_spinner[];
    JSONArray arrayEspecilidade;
    JSONArray arrayHora;
    String data, dataBr;
    private String nomeEspecilidade, SpinnerHora;
    private ArrayList<String> arrayListSpinner = new ArrayList<String>();
    private ArrayList<String> arrayListSpinnerHora = new ArrayList<String>();
    String dataFormatada;

    /** Pegar a data atual do sistema */
    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);

        /** Pega a data atual do sistema*/
            Date dataAtual = new Date();


            dataFormatada= formataData.format(dataAtual);


            System.out.println("Data formatada " + dataFormatada );
        /** Pega a data atual do sistema*/


        Button MarcarConsulta = findViewById(R.id.Btn_MarcarConsulta);

        API_URL = getString(R.string.API_URL);

        Calendario = findViewById(R.id.Calendario);


        Calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (month <10) {
                     data = year + "-0" + month + "-" + dayOfMonth;
                     dataBr = dayOfMonth + "-0" + month + "-" + year ;
                }else{
                     data = year + "-" + month + "-" + dayOfMonth;
                     dataBr = dayOfMonth + "-" + month + "-" + year ;

                }
                Log.d("Data Calendario", data );

                new PreencherHoras().execute();
            }
        });


        new PreencherEspecialidade().execute();



        Calendar cad = new GregorianCalendar();
        Log.d("data" , cad.getTime()+"");

        MarcarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CadConsulta().execute();
            }
        });
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
                    .get(API_URL+"/ListarHoras?data="+data);

            arrayListSpinnerHora.clear();
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
        especialidade = (Spinner) findViewById(R.id.SpinermarcarConsulta1);

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hora);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        especialidade.setAdapter(spinnerArrayAdapter);
        //Método do Spinner para capturar o item selecionado Especialidade
        especialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                nomeEspecilidade = parent.getItemAtPosition(posicao).toString();
                new PegarEspecialidadeEspecifica().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void PreencherSpinnerHora(ArrayList<String> Especialidade) {

        //Identifica o Spinner no layout
        hora = (Spinner) findViewById(R.id.SpinermarcarConsulta3);


        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Especialidade);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        hora.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado Hora
        hora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                SpinnerHora = parent.getItemAtPosition(posicao).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private class CadConsulta extends AsyncTask<Void,Void,Void>
    {
        String retornoApi;
        String SpnHora;
        String Spnespecialidade;
        int paciente;
        JSONObject obj;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            SpnHora = SpinnerHora;
            Spnespecialidade = nomeEspecilidade;

            SharedPreferences preferences = getSharedPreferences("Perfil", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            paciente = preferences.getInt("idUser", 0);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String, String> valores = new HashMap<>();
            valores.put("data", data);
            valores.put("hora", SpnHora);
            valores.put("especilidade", idEspecialidade);
            valores.put("paciente", String.valueOf(paciente));

            retornoApi = Http.post(API_URL+"/CadConsulta", valores);


            try {
                obj = new JSONObject(retornoApi);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if (obj.getBoolean("sucesso")) {
                    Toast.makeText(MarcarConsulta.this, "Cadastrado Com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MarcarConsulta.this, MainActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class PegarEspecialidadeEspecifica extends AsyncTask<Void,Void,Void>
    {
        String retornoApi;
        String Spnespecialidade;
        JSONObject obj;
        JSONObject itemPegar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Spnespecialidade = nomeEspecilidade;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get(API_URL+"/PegaEspecialidadeEspecifica?especilidade="+Spnespecialidade);
            Log.d("Print", API_URL+"/PegaEspecialidadeEspecifica?especilidade="+Spnespecialidade);

            try {
                obj = new JSONObject(retornoApi);
                JSONArray results = obj.getJSONArray("results");
                for (int i=0; i < results.length(); i++){
                    itemPegar = results.getJSONObject(i);
                    idEspecialidade = itemPegar.getString("idEspecialidade");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

    }
}
