package com.example.a16254838.hospitalhhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class ConsultasMarcadas extends AppCompatActivity {

    TextView txtConsulta;
    String API_URL;
    CalendarView calendario;
    /* Dados Que vem do Intent da Classe - ListarConsultasMarcadas*/

    int IdConsulta ;
    String dataConsulta;
    String horaConsulta;
    int IdPacinte;
    int IdEspecialidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_marcadas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendario = findViewById(R.id.caledarioConsultaMarcada);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();
        IdConsulta = dados.getInt("id");
        dataConsulta = dados.getString("data");
        horaConsulta = dados.getString("hora");
        IdPacinte = dados.getInt("paciente");
        IdEspecialidade = dados.getInt("especialidae");


        API_URL = getString(R.string.API_URL);
        txtConsulta = findViewById(R.id.TextConsultaMarcada);


        txtConsulta.setText("Hora : "+horaConsulta+"\n"+"Especialidade : "+IdEspecialidade);

        //Long dataCal = Long.valueOf(dataConsulta);

        String date = dataConsulta;
        String parts[] = date.split("-");

        int day = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[0]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (month - 1));
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();

        calendario.setDate(milliTime,true,true);

    }

    public void btnMarcarconsulta(View view) {
        switch (view.getId()) {

            case R.id.btnMarcarconsulta:
                startActivity(new Intent(this,MarcarConsulta.class));
                break;

        }
    }



    private class ListarConsultaMarcada extends AsyncTask<Void,Void,Void>{

        String retornoApi;
        int paciente;
        JSONObject objeto;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SharedPreferences preferences = getSharedPreferences("Perfil", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            paciente = preferences.getInt("idUser", 0);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String, String> valores = new HashMap<>();
            valores.put("paciente", String.valueOf(paciente));

            retornoApi = Http.post(API_URL+"/PegarConsultaMarcada", valores);


            try {
                objeto = new JSONObject(retornoApi);
                JSONArray results = objeto.getJSONArray("results");
                for (int i=0; i < results.length(); i++){
                    JSONObject item = results.getJSONObject(i);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
}
