package com.example.a16254838.hospitalhhealth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.a16254838.hospitalhhealth.adapter.AdapterCursosPersonalizado;
import com.example.a16254838.hospitalhhealth.model.Categoria;
import com.example.a16254838.hospitalhhealth.model.Curso;
import com.example.a16254838.hospitalhhealth.model.EstadoAtual;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ExameAdapter adapter;
    String retornoJson;
    String API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        final TabWidget tabWidget = tabHost.getTabWidget();
        final FrameLayout tabContent = tabHost.getTabContentView();

        // Get the original tab textviews and remove them from the viewgroup.
        TextView[] originalTextViews = new TextView[tabWidget.getTabCount()];
        for (int index = 0; index < tabWidget.getTabCount(); index++) {
            originalTextViews[index] = (TextView) tabWidget.getChildTabViewAt(index);
        }
        tabWidget.removeAllViews();

        // Ensure that all tab content childs are not visible at startup.
        for (int index = 0; index < tabContent.getChildCount(); index++) {
            tabContent.getChildAt(index).setVisibility(View.GONE);
        }

        // Create the tabspec based on the textview childs in the xml file.
        // Or create simple tabspec instances in any other way...
        for (int index = 0; index < originalTextViews.length; index++) {
            final TextView tabWidgetTextView = originalTextViews[index];
            final View tabContentView = tabContent.getChildAt(index);
            TabHost.TabSpec tabSpec = tabHost.newTabSpec((String) tabWidgetTextView.getTag());
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return tabContentView;
                }
            });
            if (tabWidgetTextView.getBackground() == null) {
                tabSpec.setIndicator(tabWidgetTextView.getText());
            } else {
                tabSpec.setIndicator(tabWidgetTextView.getText(), tabWidgetTextView.getBackground());
            }
            tabHost.addTab(tabSpec);
        }

//		tabHost.setCurrentTab(0);
    }


    public void selecionarOpcao(View view) {

        switch (view.getId()) {
            case R.id.consultasMarcadas:
                startActivity(new Intent(this,ConsultasMarcadas.class));
                break;
            case R.id.MarcarConsulta:
                startActivity(new Intent(this,MarcarConsulta.class));
                break;

        }

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
                    JSONArray jsonArray = new JSONArray(retornoJson);

                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        Exame e = Exame.create(
                                item.getInt("idResultadoExame"),
                                item.getInt("idPaciente"),
                                item.getInt("idMedico"),
                                item.getInt("idExame"),
                                item.getString("resultado")
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
            }
        }.execute();
    }




}
