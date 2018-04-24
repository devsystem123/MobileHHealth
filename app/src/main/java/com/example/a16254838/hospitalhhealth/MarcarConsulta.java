package com.example.a16254838.hospitalhhealth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarcarConsulta extends AppCompatActivity {

    CalendarView Calendario;
    String API_URL;
    JSONObject objeto;
    //public String array_spinner[];
    JSONArray arrayEspecilidade;

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
                     data = year + "/0" + month + "/" + dayOfMonth;
                }else{
                     data = year + "/" + month + "/" + dayOfMonth;
                }
                Log.d("Data Calendario", data );
            }
        });


        /*new AsyncTask<Void,Void,Void>() {

            String retornoApi;
            JSONObject results;


            @Override
            protected Void doInBackground(Void... voids) {

                SystemClock.sleep(500);

                retornoApi = Http
                        .get(API_URL+"/Especialidade");

                try {
                    objeto = new JSONObject(retornoApi);
                    results = objeto.getJSONObject("results");

                    arrayEspecilidade = new JSONArray(results);

                    for (int i=0; i < arrayEspecilidade.length(); i++){
                        JSONObject item = arrayEspecilidade.getJSONObject(i);
                        Especialidade espec = Especialidade.create(
                                item.getInt("idEspecialidade"),
                                item.getString("nome"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                alert("API Spinner", String.valueOf(objeto));
            }
        }.execute();*/

        //Calendario.setDate(Long.parseLong(data));

        /*Spinner s = findViewById(R.id.SpinermarcarConsulta1);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, (List) arrayEspecilidade);
        s.setAdapter(adapter);*/

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

}
