package com.guma.desarrollo.gmv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BandejaPedidosActivity extends AppCompatActivity {
    RecyclerView rv;

    List<pedido> lstPedidos;

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


        adapterPedidos = new AdapterPedidos(lstPedidos);
        rv.setAdapter(adapterPedidos);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TextView txt = (TextView) findViewById(R.id.txt);
                //txt.setText(dataSnapshot.getValue(String.class));
                Toast.makeText(BandejaPedidosActivity.this, dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });

        /*FirebaseDatabase.getInstance().getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstPedidos.removeAll(lstPedidos);
                for(DataSnapshot snp: dataSnapshot.getChildren()){
                    pedido pd = snp.getValue(pedido.class);
                    lstPedidos.add(pd);
                }

                adapterPedidos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
}
