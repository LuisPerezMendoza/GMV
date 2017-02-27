package com.guma.desarrollo.gmv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BandejaPedidosActivity extends AppCompatActivity {
    RecyclerView rv;
    private DatabaseReference mDatabase;
    List<Pedido> lstPedidos;


    AdapterPedidos adapterPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PEDIDOS DEL DIA");
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        rv = (RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        lstPedidos = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        adapterPedidos = new AdapterPedidos(lstPedidos);
        rv.setAdapter(adapterPedidos);
        mDatabase.child(ReferenciaFireBase.PEDIDOS_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstPedidos.removeAll(lstPedidos);
                for(DataSnapshot snp: dataSnapshot.getChildren()){
                    lstPedidos.add(snp.getValue(Pedido.class));
                }
                adapterPedidos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.btAddFire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(ReferenciaFireBase.PEDIDOS_REFERENCE).child(UUID.randomUUID().toString()).setValue(new Pedido("01636", "0","F06"));
            }
        });





    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }

}
