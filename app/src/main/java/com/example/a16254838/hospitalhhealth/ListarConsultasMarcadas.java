package com.example.a16254838.hospitalhhealth;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a16254838.hospitalhhealth.adapter.ConsultaAdapter;
import com.example.a16254838.hospitalhhealth.adapter.ListarConsultaMarcadaAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListarConsultasMarcadas extends AppCompatActivity {

    String API_URL;
    ListView listView;
    ListarConsultaMarcadaAdapter adapterConsulta;
    ListView ListaConsulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_consultas_marcadas);

        adapterConsulta = new ListarConsultaMarcadaAdapter(ListarConsultasMarcadas.this,new ArrayList<ClassAgendaConsulta>());

        API_URL = getString(R.string.API_URL);

        ListaConsulta = findViewById(R.id.list_consulta_marcada);



        ListaConsulta.setAdapter(adapterConsulta);

         /*Listar Exames */
        //listView = findViewById(R.id.list_consulta_marcada);

        /*definir adapter na lista*/
        //listView.setAdapter(adapterConsulta);

        new ListarConsultas().execute();

        /** Abre ah tela com calendario com a consulta selecionada */
        ListaConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassAgendaConsulta Consul = (ClassAgendaConsulta) ListaConsulta.getItemAtPosition(i);


                Intent intent = new Intent(getApplicationContext(),ConsultasMarcadas.class);

                intent.putExtra("idConsulta",Consul.getIdConsulta());
                intent.putExtra("data",Consul.getData());
                intent.putExtra("hora",Consul.getHora());
                intent.putExtra("paciente",Consul.getIdPaciente());
                intent.putExtra("especialidae",Consul.getIdEspecialidade());


                startActivity(intent);
            }}
        );
    }



    private class ListarConsultas extends AsyncTask<Void,Void,Void>{

        String RetornoApi;
        JSONObject object;
        int paciente;
        ArrayList<ClassAgendaConsulta> lstConsula = new ArrayList<ClassAgendaConsulta>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences preferences = getSharedPreferences("Perfil", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            paciente = preferences.getInt("idUser", 0);

            HashMap<String, String> valores = new HashMap<>();
            valores.put("paciente", String.valueOf(paciente));

            RetornoApi = Http.post(API_URL+"/PegarConsultaMarcada",valores);

            try {
                object = new JSONObject(RetornoApi);

                JSONArray results = object.getJSONArray("results");

                for (int i=0; i < results.length(); i++){
                    JSONObject item = results.getJSONObject(i);
                    ClassAgendaConsulta Consul = ClassAgendaConsulta.create(
                            item.getInt("idAgendaConsulta"),
                            item.getString("data"),
                            item.getString("hora"),
                            item.optInt("idPaciente"),
                            item.optInt("idEspecialidade")
                    );
                    lstConsula.add(Consul);

                }

            } catch (Exception e) {
                Log.e("ERRO :", e.getMessage());
                e.printStackTrace();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //alert("MsG", String.valueOf(object));

            adapterConsulta.addAll(lstConsula);
        }
    }
    public void alert(String titulo, String msg){


        AlertDialog.Builder builder = new AlertDialog.Builder(this );

        builder.setMessage(msg)
                .setTitle(titulo);

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();

        //mostrar o alerta
        dialog.show();
    }
}
