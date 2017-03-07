package com.guma.desarrollo.gmv.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.Clientes_Leads;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.api.ConnectivityReceiver;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.MyApplication;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Clientes_Repository;
import com.guma.desarrollo.gmv.models.Respuesta_articulos;
import com.guma.desarrollo.gmv.models.Respuesta_indicadores;
import com.guma.desarrollo.gmv.models.Respuesta_mora;
import com.guma.desarrollo.gmv.models.Respuesta_clientes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgendaActivity extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = "AgendaActivity";
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    private Menu menu;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
    private Retrofit retrofit,retrofit_mora,retrofit_indicadores,retrofit_clientes;
    private ListView lstClientes;
    private List<Clientes> objects;
    private Clientes_Leads lbs;

    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadData();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        listAdapter = new CustomAdapter(AgendaActivity.this, deptList);
        simpleExpandableListView.setAdapter(listAdapter);
        ReferenciasContexto.setContextArticulo(AgendaActivity.this);

        objects = Clientes_Repository.getInstance().getArticulos();
        lbs = new Clientes_Leads(this, objects);



        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        checked = preferences.getBoolean("pref",false);
        setTitle("Ultm. Actualizacion: " + preferences.getString("lst","00/00/0000"));
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GroupInfo headerInfo = deptList.get(groupPosition);
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                //Toast.makeText(getBaseContext(), " Clicked on :: " + headerInfo.getName() + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getBaseContext(), detailInfo.getCodigo(), Toast.LENGTH_LONG).show();
                editor.putString("ClsSelected",detailInfo.getCodigo());
                editor.putString("NameClsSelected",detailInfo.getName());
                editor.apply();
                startActivity(new Intent(AgendaActivity.this,MarcarRegistroActivity.class));
                return false;
            }
        });

        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(final ExpandableListView parent, View v, int groupPosition, long id) {
                GroupInfo headerInfo = deptList.get(groupPosition);
                //Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),Toast.LENGTH_LONG).show();
                LayoutInflater li = LayoutInflater.from(AgendaActivity.this);
                View promptsView = li.inflate(R.layout.input_search_cliente, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AgendaActivity.this);
                alertDialogBuilder.setView(promptsView);
                lstClientes = (ListView) promptsView.findViewById(R.id.listViewClientes);
                lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Clientes cls = (Clientes) parent.getItemAtPosition(position);
                        Toast.makeText(AgendaActivity.this, cls.getmCliente(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(AgendaActivity.this, cls.getmNombre(), Toast.LENGTH_SHORT).show();
                    }
                });
                sv = (SearchView) promptsView.findViewById(R.id.svClientes);
                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String text) {
                        text = text.toLowerCase(Locale.getDefault());
                        ArrayList<Clientes> newList = new ArrayList<>();
                        for(Clientes clientes:objects){
                            if (clientes.getmNombre().toLowerCase().contains(text)){
                                newList.add(clientes);
                            }
                        }
                        lstClientes.setAdapter(new Clientes_Leads(AgendaActivity.this, newList));
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String text) {

                        return false;
                    }
                });
                lstClientes.setAdapter(lbs);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AgendaActivity.this, "Guardar", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }}).create().show();
                return false;
            }
        });
        findViewById(R.id.imgCump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgendaActivity.this,CumpleannoActivity.class));
            }
        });
        findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[]items = { "PEDIDO", "COBRO","ENVIAR","RECIBIR","REPORTE DEL DIA","SALIR"};
                new AlertDialog.Builder(v.getContext()).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(items[0])){
                            startActivity(new Intent(AgendaActivity.this,BandejaPedidosActivity.class));
                        }else{
                            if (items[which].equals(items[1])){
                                startActivity(new Intent(AgendaActivity.this,BandejaCobrosActivity.class));
                            }else{
                                if (items[which].equals(items[2])){

                                } else {
                                    if (items[which].equals(items[3])){
                                            if (ManagerURI.isOnlinea(AgendaActivity.this)==true){
                                                new MyTask().execute("");
                                            } else {
                                                Toast.makeText(AgendaActivity.this, "No Posee Cobertura de datos...", Toast.LENGTH_SHORT).show();
                                            }
                                    } else {
                                        if (items[which].equals(items[4])){
                                            startActivity(new Intent(AgendaActivity.this,RptHoyActivity.class));
                                        } else {
                                            if (items[which].equals(items[5])){
                                                checked = false;
                                                editor.putBoolean("pref", false).commit();
                                                editor.apply();
                                                finish();
                                            }else{
                                                Toast.makeText(AgendaActivity.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    }
                }).create().show();

            }
        });
        expandAll();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {
        menu.getItem(0).setIcon(isConnected ? getResources().getDrawable(R.drawable.btngreen) : getResources().getDrawable(R.drawable.btnred));
    }
    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Ultm. Actualizacion: " + preferences.getString("lst","00/00/0000"));
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
        this.menu = menu;
        return true;
    }
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }
    private void loadData(){

        addProduct("LUNES","FARMACIA SAN MARTIN","01006","S");
        addProduct("MARTES","FARMACIA FARMA CENTER","01338","N");
        addProduct("MIERCOLES","VACIO","","N");
        addProduct("JUEVES","VACIO","","N");
        addProduct("VIERNES","VACIO","","N");
    }
    private int addProduct(String department, String product,String Codigo,String Cumple){
        int groupPosition = 0;
        GroupInfo headerInfo = subjects.get(department);

        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        int listSize = productList.size();
        listSize++;

        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        detailInfo.setCodigo(Codigo);
        detailInfo.setCumple(Cumple);
        productList.add(detailInfo);

        headerInfo.setProductList(productList);
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

    private class MyTask extends AsyncTask<String,String,String>{
        public ProgressDialog pdialog;
        @Override
        protected void onPreExecute() {
            pdialog = ProgressDialog.show(AgendaActivity.this, "","Procesando. Porfavor Espere...", true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            retrofit = new Retrofit.Builder().baseUrl(ManagerURI.getURL_Base()).addConverterFactory(GsonConverterFactory.create()).build();
            Servicio service = retrofit.create(Servicio.class);
            final Call<Respuesta_articulos> ArticuloRespuestaCall = service.obtenerListaArticulos();
            ArticuloRespuestaCall.enqueue(new Callback<Respuesta_articulos>() {
                @Override
                public void onResponse(Call<Respuesta_articulos> call, Response<Respuesta_articulos> response) {
                    if(response.isSuccessful()){
                        Respuesta_articulos articuloRespuesta = response.body();
                        Log.d(TAG, "onResponse: Articulos " + articuloRespuesta.getCount());
                        Articulos_model.SaveArticulos(AgendaActivity.this,articuloRespuesta.getResults());
                    }else{
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + response.errorBody() );
                        Toast.makeText(AgendaActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_articulos> call, Throwable t) {
                    pdialog.dismiss();
                }
            });

            retrofit_mora = new Retrofit.Builder().baseUrl(ManagerURI.getURL_Base()).addConverterFactory(GsonConverterFactory.create()).build();
            Servicio service_mora = retrofit_mora.create(Servicio.class);
            final Call<Respuesta_mora> ClienteMoraRespuestaCall = service_mora.obtenerListaClienteMora();
            ClienteMoraRespuestaCall.enqueue(new Callback<Respuesta_mora>() {
                @Override
                public void onResponse(Call<Respuesta_mora> call, Response<Respuesta_mora> response) {
                    if(response.isSuccessful()){

                        Respuesta_mora moraRespuesta = response.body();
                        Log.d(TAG, "onResponse: Mora " + moraRespuesta.getCount());
                        Clientes_model.SaveMora(AgendaActivity.this,moraRespuesta.getResults());
                    }else{
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + response.errorBody() );
                    }
                }

                @Override
                public void onFailure(Call<Respuesta_mora> call, Throwable t) {
                    pdialog.dismiss();
                    Log.d(TAG, "onResponse: " + t.getMessage() );

                }
            });

            retrofit_indicadores = new Retrofit.Builder().baseUrl(ManagerURI.getURL_Base()).addConverterFactory(GsonConverterFactory.create()).build();
            Servicio service_indicadores = retrofit_indicadores.create(Servicio.class);
            final Call<Respuesta_indicadores> ClienteIndicadoresRespuestaCall = service_indicadores.obtenerListaClienteIndicadores();
            ClienteIndicadoresRespuestaCall.enqueue(new Callback<Respuesta_indicadores>() {
                @Override
                public void onResponse(Call<Respuesta_indicadores> call, Response<Respuesta_indicadores> response) {
                    if(response.isSuccessful()){
                        Respuesta_indicadores IndicadorRespuesta = response.body();
                        Log.d(TAG, "onResponse: Indicadores " + IndicadorRespuesta.getCount() );
                        Clientes_model.SaveIndicadores(AgendaActivity.this,IndicadorRespuesta.getResults());
                    }else{
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + response.errorBody() );
                    }
                }

                @Override
                public void onFailure(Call<Respuesta_indicadores> call, Throwable t) {
                    pdialog.dismiss();
                    Log.d(TAG, "onResponse: " + t.getMessage() );

                }
            });
            retrofit_clientes = new Retrofit.Builder().baseUrl(ManagerURI.getURL_Base()).addConverterFactory(GsonConverterFactory.create()).build();
            Servicio service_clientes = retrofit_clientes.create(Servicio.class);
            final Call<Respuesta_clientes> clientesRespuesta = service_clientes.obtenerListaClientes();
            clientesRespuesta.enqueue(new Callback<Respuesta_clientes>() {
                @Override
                public void onResponse(Call<Respuesta_clientes> call, Response<Respuesta_clientes> response) {
                    if(response.isSuccessful()){

                        Respuesta_clientes clRespuesta = response.body();
                        Log.d(TAG, "onResponse: Clientes "  + clRespuesta.getCount());
                        Clientes_model.SaveClientes(AgendaActivity.this,clRespuesta .getResults());
                        Alerta();
                    }else{
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + response.errorBody() );
                    }
                }

                @Override
                public void onFailure(Call<Respuesta_clientes> call, Throwable t) {
                    pdialog.dismiss();
                    Log.d(TAG, "onResponse: " + t.getMessage() );

                }
            });
            editor.putString("lst",Clock.getTimeStamp()).apply();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pdialog.dismiss();
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    private void Alerta() {
        new AlertDialog.Builder(AgendaActivity.this).setTitle("RECIBIDO").setMessage("Informacion Recibida...").setCancelable(false).setPositiveButton("OK",null).show();
    }

}