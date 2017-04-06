package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
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
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.Adapters.Clientes_Leads;
import com.guma.desarrollo.gmv.CategoriaInfo;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.Tasks.TaskDownload;
import com.guma.desarrollo.gmv.Tasks.TaskUnload;
import com.guma.desarrollo.gmv.api.Calendario;
import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.ConnectivityReceiver;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.MyApplication;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Clientes_Repository;

import com.guma.desarrollo.gmv.models.Respuesta_pedidos;
import com.guma.desarrollo.gmv.models.Respuesta_articulos;
import com.guma.desarrollo.gmv.models.Respuesta_indicadores;
import com.guma.desarrollo.gmv.models.Respuesta_mora;
import com.guma.desarrollo.gmv.models.Respuesta_clientes;
import com.guma.desarrollo.gmv.models.Respuesta_puntos;
import com.guma.desarrollo.gmv.models.Respuesta_usuario;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaActivity extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener {


    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<>();
    private ArrayList<GroupInfo> deptList = new ArrayList<>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    private Menu menu;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
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
        /*preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        String userr = preferences.getString("NOMBRE"," --ERROR--");

        Toast.makeText(this, "EL USUARIO ES->>"+userr.toString(), Toast.LENGTH_SHORT).show();*/
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
        setTitle("Ultm. Actualizacion: " + preferences.getString("lstDownload","00/00/0000"));
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GroupInfo headerInfo = deptList.get(groupPosition);
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                editor.putString("ClsSelected",detailInfo.getCodigo());
                editor.putString("NameClsSelected",detailInfo.getName());
                editor.apply();
                startActivity(new Intent(AgendaActivity.this,MarcarRegistroActivity.class));
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

                final CharSequence[]items = { "CREAR AGENDA","PEDIDO", "COBRO","ENVIAR","RECIBIR","REPORTE DEL DIA","SALIR"};
                new AlertDialog.Builder(v.getContext()).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (items[which].equals(items[0])){
                                startActivity(new Intent(AgendaActivity.this,CrearAgendaActivity.class));
                        }else{
                            if (items[which].equals(items[1])){
                                startActivity(new Intent(AgendaActivity.this,BandejaPedidosActivity.class));
                            }else{
                                if (items[which].equals(items[2])){
                                    startActivity(new Intent(AgendaActivity.this,BandejaCobrosActivity.class));
                                }else{
                                    if (items[which].equals(items[3])){
                                        //new Notificaciones().Alert(AgendaActivity.this,"ERROR","NO HAY PEDIDOS...").setCancelable(false).setPositiveButton("OK", null).show();

                                        // new TaskUnload(AgendaActivity.this).execute();

                                        new TaskUnload(AgendaActivity.this).execute();
                                        //new Calendario().show(getSupportFragmentManager(), "datePicker");

                                    } else {
                                        if (items[which].equals(items[4])){
                                            if (ManagerURI.isOnlinea(AgendaActivity.this)==true){
                                                new TaskDownload(AgendaActivity.this).execute(0);
                                            } else {
                                                Toast.makeText(AgendaActivity.this, "No Posee Cobertura de datos...", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            if (items[which].equals(items[5])){
                                                startActivity(new Intent(AgendaActivity.this,RptHoyActivity.class));
                                            } else {
                                                if (items[which].equals(items[6])){
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



                    }
                }).create().show();

            }
        });
        expandAll();


       // AutoTask();
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
        setTitle("Ultm. Actualizacion: " + preferences.getString("lstDownload","00/00/0000"));


        expandAll();
       // AutoTask();
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        //boolean isConnected = ;
       // showSnack(ConnectivityReceiver.isConnected());
        checkConnection();
    }
    private void blankAgenda(){
        String[] strDias = getResources().getStringArray(R.array.dias);
        for (int i=0;i<strDias.length;i++){
            initTabbla(strDias[i]);
        }
    }
    private int initTabbla(String Grupo){
        int groupPosition = 0;
        GroupInfo headerInfo = subjects.get(Grupo);
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(Grupo);
            subjects.put(Grupo, headerInfo);
            deptList.add(headerInfo);
        }
        headerInfo.setProductList(headerInfo.getProductList());
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;


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


        List<Map<String, Object>> lista = Agenda_model.getAgenda(ManagerURI.getDirDb(), AgendaActivity.this);
        if (lista.size()>0){
            //deptList.clear();
            String[] strDias = getResources().getStringArray(R.array.dias);
            for (int i=0;i<strDias.length;i++){
                String[] mD = lista.get(0).get(strDias[i]).toString().split("-");
                for (int d=0;d<mD.length;d++){
                    addProduct(strDias[i],mD[d],"","N");
                }
            }

        }else{
            blankAgenda();
        }




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
    private void AutoTask(){
        if (Integer.parseInt(Clock.getDiferencia(Clock.StringToDate(Clock.getNow()),Clock.StringToDate(preferences.getString("lstDownload","00/00/0000")))) >= 6){
            new TaskDownload(AgendaActivity.this).execute(0);
        }

        if (Integer.parseInt(Clock.getDiferencia(Clock.StringToDate(Clock.getNow()),Clock.StringToDate(preferences.getString("lstUnload","00/00/0000")))) >= 3){
            new TaskUnload(AgendaActivity.this).execute(0);

        }
    }
}