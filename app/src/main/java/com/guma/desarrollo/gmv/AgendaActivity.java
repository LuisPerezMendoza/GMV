package com.guma.desarrollo.gmv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.api.ArticuloApiService;
import com.guma.desarrollo.gmv.models.ArticuloRespuesta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgendaActivity extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = "AgendaActivity";
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    private Menu menu;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Ultm. Actualizacion:");
        loadData();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        listAdapter = new CustomAdapter(AgendaActivity.this, deptList);
        simpleExpandableListView.setAdapter(listAdapter);
        expandAll();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checked = preferences.getBoolean("pref",false);



        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               /* GroupInfo headerInfo = deptList.get(groupPosition);
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                Toast.makeText(getBaseContext(), " Clicked on :: " + headerInfo.getName()
                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();*/
                startActivity(new Intent(AgendaActivity.this,MarcarRegistroActivity.class));
                return false;
            }
        });

       /* simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                GroupInfo headerInfo = deptList.get(groupPosition);
                Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });*/
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
                                    retrofit = new Retrofit.Builder()
                                            .baseUrl(ManagerURI.getURL_Base())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    obtenerDatos();
                                } else {
                                    if (items[which].equals(items[3])){
                                        Toast.makeText(AgendaActivity.this, "EN DESARROLLO", Toast.LENGTH_SHORT).show();
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
    }

    private void obtenerDatos() {

        ArticuloApiService service = retrofit.create(ArticuloApiService.class);
        final Call<ArticuloRespuesta> ArticuloRespuestaCall = service.obtenerListaArticulos();
        ArticuloRespuestaCall.enqueue(new Callback<ArticuloRespuesta>() {
            @Override
            public void onResponse(Call<ArticuloRespuesta> call, Response<ArticuloRespuesta> response) {
                if(response.isSuccessful()){
                    ArticuloRespuesta articuloRespuesta = response.body();

                    SQLiteHelper.SaveArticulos(AgendaActivity.this,articuloRespuesta.getResults());
                }else{
                    Log.d(TAG, "onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ArticuloRespuesta> call, Throwable t) {
                //Toast.makeText(AgendaActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
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
        addProduct("LUNES","CLIENTE");
    }
    private int addProduct(String department, String product){
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
        productList.add(detailInfo);
        headerInfo.setProductList(productList);
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }


}
