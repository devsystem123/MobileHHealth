package com.example.a16254838.hospitalhhealth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    public static final String MANTER_CONCTADO="manter_conectado";

    private EditText usuario;
    private EditText senha;
    Button btn_login;
    ProgressBar progressBar;
    String API_URL;

    CheckBox manterConectado;
    Usuario user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.CPF_Login);
        senha = findViewById(R.id.senha);
        btn_login = findViewById(R.id.btn_Login);
        progressBar = findViewById(R.id.progress_bar);
        manterConectado = findViewById(R.id.manterConectado);

        //buscando a URL da API
        API_URL = getString(R.string.API_URL);

        //botão de login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //Efetuando o login
                new LoginTask().execute();
                //Toast.makeText(LoginActivity.this, "Funciona", Toast.LENGTH_SHORT).show();
            }
        });


        SharedPreferences preferencias = getSharedPreferences("Perfil",MODE_PRIVATE);
        boolean conectado = preferencias.getBoolean(MANTER_CONCTADO,false);

        if(conectado) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LoginTask extends AsyncTask<Void,Void,Void> {

        String _email, _senha;
        String retornoApi;
        JSONObject objeto;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);

            _email = usuario.getText().toString();
            _senha = senha.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SystemClock.sleep(500);


            HashMap<String, String> valores = new HashMap<>();
            valores.put("senha", _senha);
            valores.put("cpf", _email);


            retornoApi = Http
                    .post(API_URL + "/Login", valores);


            try {
                objeto = new JSONObject(retornoApi);
                JSONArray results = objeto.getJSONArray("results");
                //JSONObject results = objeto.getJSONObject("results");

                for (int i=0; i < results.length(); i++){
                    JSONObject item = results.getJSONObject(i);
                     user = Usuario.create(
                             item.getInt("idPaciente"));
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

            progressBar.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);



            //alert("API", String.valueOf(objeto));

            try {
                if (objeto.getBoolean("sucesso")) {
                    //alert("Login", String.valueOf(objeto));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    SharedPreferences preferencias = getSharedPreferences("Perfil", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putBoolean(MANTER_CONCTADO, manterConectado.isChecked());
                    editor.putInt("idUser",user.getId());
                    //editor.commit();
                    editor.apply();

                } else {
                    Toast.makeText(LoginActivity.this, "CPF ou Senha estão incorretos", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public void alert(String titulo, String msg){


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this );

        builder.setMessage(msg)
                .setTitle(titulo);

        builder.setPositiveButton("OK", null);

        android.app.AlertDialog dialog = builder.create();

        //mostrar o alerta
        dialog.show();
    }
}

