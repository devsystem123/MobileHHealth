package com.example.a16254838.hospitalhhealth;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16254838.hospitalhhealth.adapter.ConsultaAdapter;
import com.example.a16254838.hospitalhhealth.adapter.ExameAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    private ZXingScannerView mScannerView;
    /*Listar_Exames.java*/
        ListView listView;
        ExameAdapter adapter;
        String retornoJson;
        JSONObject objeto;

    String API_URL;
    Button bt_list;

    /*Lista_Historico*/
        ConsultaAdapter adapterConsulta;
        String retornoJsonConsulta;


    /*QrCode*/
        public static TextView tvresult;
        public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
        private static int SPLASH_TIME_OUT = 2000;

    private String TAG = "tag";

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


        /** Listar_Exames.java*/

            /**Chama Função que lista o historico de exames*/
            ListarExames();


            /** Click nas listas para intent com o Chrome que resulta no download do exame*/
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String url = "http://www.facebook.com";
                        Intent in = new Intent(Intent.ACTION_VIEW);
                        in.setData(Uri.parse(url));
                        startActivity(in);
                    }}
                );

            /**Lista_Historico.java*/

            /**Chama Função que lista historico de consultas*/
            ListarConsulta();

        /*Chama a função pra ler o QrCode*/
        Button btnScan = findViewById(R.id.btnScan);
        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAndRequestPermissions()) {
                    // carry on the normal flow, as the case of  permissions  granted.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity

                            Intent i = new Intent(MainActivity.this, ScanActivity.class);
                            startActivity(i);

                            // close this activity
                            finish();
                        }
                    }, SPLASH_TIME_OUT);
                }
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void selecionarOpcao(View view) {

        switch (view.getId()) {
            case R.id.consultasMarcadas:
                startActivity(new Intent(this,ListarConsultasMarcadas.class));
                break;
            case R.id.MarcarConsulta:
                startActivity(new Intent(this,MarcarConsulta.class));
                break;

        }

    }

    // Listar_Exames.java
        /*
        * Codigo da tela listar exames Listar_Exames
        *
        * */
        public void ListarExames(){

            API_URL = getString(R.string.API_URL);


            /*Listar Exames */
            listView = findViewById(R.id.listar_Exames_);


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

                        JSONArray ArrayResult = objeto.getJSONArray("results");

                        //JSONArray ArrayResult = new JSONArray(results);

                        for (int i=0; i < ArrayResult.length(); i++){
                            JSONObject item = ArrayResult.getJSONObject(i);
                            Exame e = Exame.create(
                                    item.getInt("idResultadoExame"),
                                    item.optInt("idPaciente"),
                                    item.optInt("idMedico"),
                                    item.optInt("idExame"),
                                    item.getString("resultado"),
                                    item.getString("nome")
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
                    //alert("API TESTE", String.valueOf(objeto));
                    adapter.addAll(lstExame);
                }
            }.execute();




        }

        public void ListarConsulta(){
            API_URL = getString(R.string.API_URL);


            /*Listar Exames */
            ListView ListaConsulta = findViewById(R.id.lista_consulta);


            /* Criando adater*/
            adapterConsulta = new ConsultaAdapter(this,new ArrayList<ClassConsulta>());


            /*definir adapter na lista*/
            ListaConsulta.setAdapter(adapterConsulta);
            //Roda o comando em segundo plano.
            new AsyncTask<Void, Void, Void>(){

                ArrayList<ClassConsulta> lstConsula = new ArrayList<ClassConsulta>();

                @Override
                protected Void doInBackground(Void... voids) {
                    retornoJsonConsulta = Http.get(API_URL+"/ListarResultadoConsulta");

                    Log.d("Retorno :", retornoJsonConsulta);

                    try {
                        objeto = new JSONObject(retornoJsonConsulta);

                        JSONArray ArrayResult = objeto.getJSONArray("results");

                        //JSONArray ArrayResult = new JSONArray(results);

                        for (int i=0; i < ArrayResult.length(); i++){
                            JSONObject item = ArrayResult.getJSONObject(i);
                            ClassConsulta Consul = ClassConsulta.create(
                                    item.getInt("idResConsulta"),
                                    item.optInt("idPaciente"),
                                    item.optInt("idMedico"),
                                    item.optInt("idConsulta"),
                                    item.getString("Relatorio"),
                                    item.getString("nome")
                            );
                            lstConsula.add(Consul);
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
                    //alert("API TESTE", String.valueOf(objeto));
                    adapterConsulta.addAll(lstConsula);
                }
            }.execute();
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

    //Criar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);//SETA OS ITENS DO MENU
        return true;
    }

    //Popular Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //VERIFICA QUAL OPÇÃO DO MENU FOI CLICADA
        switch ( item.getItemId() ) {
            case R.id.perfil:
                //startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
                Toast.makeText(this, "Perfil !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sair:

                /*SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
                LoginActivity.M
                preferencias.getBoolean()*/
                //Altera as preferencias do usuario
                SharedPreferences preferences = getSharedPreferences("Perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /** hfrejiewgbjkgbjkfbg*/

    private  boolean checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        /*int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);*/


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        /*if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }*/
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        Intent i = new Intent(MainActivity.this, ScanActivity.class);
                        startActivity(i);
                        finish();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private void explain(String msg){
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.exampledemo.parsaniahardik.marshmallowpermission")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }
}