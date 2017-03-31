package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Calendario;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrearAgendaActivity extends AppCompatActivity{
    private ExpandableListView simpleExpandableListView;
    private CustomAdapter listAdapter;
    private ArrayList<GroupInfo> deptList = new ArrayList<>();
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<>();
    List<Map<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        setTitle("PLAN DE TRABAJO");

        list = new ArrayList<>();
        final Calendario Cld1 = new Calendario();
        final Calendario Cld2 = new Calendario();
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAgenda();
            }
        });
        findViewById(R.id.startPlan).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Cld1.show(getSupportFragmentManager(), "datePicker");
            }
        });
        findViewById(R.id.endPlan).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Cld2.show(getSupportFragmentManager(), "datePicker");
            }
        });

        simpleExpandableListView = (ExpandableListView) findViewById(R.id.ExpListDias);
        listAdapter = new CustomAdapter(CrearAgendaActivity.this, deptList);
        simpleExpandableListView.setAdapter(listAdapter);

        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                startActivityForResult(new Intent(CrearAgendaActivity.this,ClientesActivity.class).putExtra("DIA",deptList.get(i).getName()),0);
                return false;
            }
        });

        loadData();
        expandAll();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            Map<String, Object> map = new HashMap<>();
            map.put("ITEMDIA", data.getStringArrayListExtra("myCliente").get(0));
            map.put("ITEMNAME", data.getStringArrayListExtra("myCliente").get(1));
            map.put("ITEMCODIGO", data.getStringArrayListExtra("myCliente").get(2));
            list.add(map);
            Refresh();
        }
    }

    private void Refresh() {
       // simpleExpandableListView.setAdapter(new CustomAdapter(CrearAgendaActivity.this, new ArrayList<GroupInfo>()));
        for (Map<String, Object> obj : list){
            addProduct(obj.get("ITEMDIA").toString(),obj.get("ITEMNAME").toString(),obj.get("ITEMCODIGO").toString(),"N");
        }
        /*for (int i = 0; i < listAdapter.getGroupCount(); i++){
            for (int s=0;s<deptList.get(i).getCount();s++){
                deptList.get(i).getName();
                deptList.get(i).getProductList().get(s).getName();
                deptList.get(i).getProductList().get(s).getCodigo();
                //Log.d("", "Refresh: " + deptList.get(i).getProductList().get(s).getName());
                //deptList.get(i).getProductList().get(s);

            }

            //ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
        }*/
        //expandAll();
    }
    private void SaveAgenda(){
        for (Map<String, Object> obj : list){

            Log.d("", "SaveAgenda: " + obj.get("ITEMDIA").toString() + " " + obj.get("ITEMNAME").toString()+ " " +obj.get("ITEMCODIGO").toString());

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
    private void expandAll() {
        for (int i = 0; i < listAdapter.getGroupCount(); i++){
            simpleExpandableListView.expandGroup(i);
        }
    }
    private void loadData(){
        String[] strDias = getResources().getStringArray(R.array.dias);
        addProduct(strDias[0],"","","S");
        addProduct(strDias[1],"","","N");
        addProduct(strDias[2],"","","N");
        addProduct(strDias[3],"","","N");
        addProduct(strDias[4],"","","N");

        /*for(Clientes obj : Clientes_model.getClientes(ManagerURI.getDirDb(), CrearAgendaActivity.this)) {
           /* if (obj.getmNombre().substring(0,1).equals("F")){
                addProduct(obj.getmNombre().replace("FARMACIA","").trim().substring(0,1),obj.getmNombre(),obj.getmCliente(),"N");
            }else{
                addProduct(obj.getmNombre().trim().substring(0,1),obj.getmNombre(),obj.getmCliente(),"N");
            }
            addProduct("CLIENTES",obj.getmNombre(),obj.getmCliente(),"N");

        }*/
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

}
