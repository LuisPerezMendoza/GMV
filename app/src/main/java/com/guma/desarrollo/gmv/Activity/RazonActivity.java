package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.ActividadInfo;
import com.guma.desarrollo.gmv.Adapters.ActividadesAdapter;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.CategoriaInfo;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RazonActivity extends AppCompatActivity {
    private LinkedHashMap<String, CategoriaInfo> subjects = new LinkedHashMap<>();
    private ArrayList<CategoriaInfo> deptList = new ArrayList<>();

    private ActividadesAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    private void loadActividades(){
        for (Actividad obj : Actividades_model.getActividades(ManagerURI.getDirDb(),RazonActivity.this))
        {
            addProduct(obj.getmCategoria(), obj.getmActividad(), obj.getmIdAE().toString());
        }
    }

    private int addProduct(String Categoria, String Actividad, String IdAE){
        int groupPosition = 0;
        CategoriaInfo headerInfo = subjects.get(Categoria);
        if (headerInfo==null)
        {
            headerInfo = new CategoriaInfo();
            headerInfo.setName(Categoria);
            subjects.put(Categoria, headerInfo);
            deptList.add(headerInfo);
        }
        ArrayList<ActividadInfo> productList =headerInfo.getActividadList();
        int listSize = productList.size();
        listSize++;
        ActividadInfo detailInfo = new ActividadInfo();
        detailInfo.setIdAE(IdAE);
        detailInfo.setActividad(Actividad);
        productList.add(detailInfo);
        headerInfo.setActividadesList(productList);
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razon);
        loadActividades();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListViewRazon);
        listAdapter = new ActividadesAdapter(RazonActivity.this,deptList);
        simpleExpandableListView.setAdapter(listAdapter);
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CategoriaInfo headerInfo = deptList.get(groupPosition);
                ActividadInfo detailInfo = headerInfo.getActividadList().get(childPosition);
                return false;
            }
        });
    }
}
