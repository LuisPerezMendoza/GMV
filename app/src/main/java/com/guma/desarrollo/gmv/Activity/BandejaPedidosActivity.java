package com.guma.desarrollo.gmv.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.Adapters.AdapterPedidos;
import com.guma.desarrollo.gmv.Pedido;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.ReferenciaFireBase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BandejaPedidosActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Pedidos> fList = null;
    List<Pedido> lstPedidos;
    TextView mTotal;
    float mCalTotal;

    AdapterPedidos adapterPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PEDIDOS DEL DIA");
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        mTotal = (TextView) findViewById(R.id.txtTotalPedidos);

        List<Pedidos> lstObj = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(), BandejaPedidosActivity.this);
        Log.d("", "LISTAPEDIDOS: " + lstObj.get(0).toString());
        if (lstObj.size() == 0){
            Toast.makeText(this, "ERROR LISTA VACIA", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "tiene el cabron", Toast.LENGTH_SHORT).show();
        }
        for(Pedidos obj : lstObj) {

            //fList.add(obj);
            //Toast.makeText(this, obj.getmPrecio().toString(), Toast.LENGTH_SHORT).show();
            //mCalTotal += Float.parseFloat(obj.getmPrecio());
        }
        //Toast.makeText(this, "el total->>"+String.valueOf(mCalTotal), Toast.LENGTH_SHORT).show();
    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }

}
