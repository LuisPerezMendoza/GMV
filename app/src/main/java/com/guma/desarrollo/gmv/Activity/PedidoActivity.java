package com.guma.desarrollo.gmv.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.gmv.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoActivity extends AppCompatActivity {
    private ListView listView;
    List<Map<String, Object>> list;
    TextView SubTotal,ivaTotal,Total,txtCount;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listViewSettingConnect);
        list = new ArrayList<>();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setTitle(preferences.getString("NameClsSelected"," --ERROR--"));

        SubTotal = (TextView) findViewById(R.id.SubTotal);
        ivaTotal = (TextView) findViewById(R.id.ivaTotal);
        Total = (TextView) findViewById(R.id.Total);
        txtCount= (TextView) findViewById(R.id.txtCountArti);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity.this);
                builder.setMessage("¿Desea Eliminar el Registro?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                list.remove(i);
                                Refresh();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create().show();
            }
        });
        findViewById(R.id.txtSendPedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size()!=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity.this);
                    builder.setMessage("¿Confirma la transaccion?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent send = new Intent(PedidoActivity.this,ResumenActivity.class);
                                    send.putExtra("LIST", (Serializable) list);
                                    //send.putExtra("NombreCliente",getIntent().getStringExtra("NombreCliente"));
                                    startActivity(send);
                                    finish();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).create().show();

                }else{
                    Toast.makeText(PedidoActivity.this, "VACIO", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void Refresh(){
        float vLine = 0,subValor=0,vFinal=0;
        listView.setAdapter(
                new SimpleAdapter(
                        this,
                        list,
                        R.layout.list_item_carrito, new String[] {"ITEMNAME", "ITEMCANTI","ITEMPRECIO","ITEMVALOR","BONIFICADO" },
                        new int[] {R.id.tvListItemName,R.id.Item_cant,R.id.tvListItemPrecio,R.id.Item_valor,R.id.tbListBonificado}));


        for (Map<String, Object> obj : list){
            vLine     += Float.parseFloat(obj.get("ITEMVALOR").toString());
            subValor  += Float.parseFloat(obj.get("ITEMSUBTOTAL").toString());
            vFinal    += Float.parseFloat(obj.get("ITEMVALORTOTAL").toString());
        }
        SubTotal.setText("SubTotal C$ " + String.valueOf(vLine));
        ivaTotal.setText("IVA C$ " + String.valueOf(subValor));
        Total.setText("TOTAL C$ "+ String.valueOf(vFinal));
        txtCount.setText(listView.getCount() +" Articulo");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedidos, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.accion_new) {
            startActivityForResult(new Intent(this,ArticulosActivity.class),0);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ITEMNAME", data.getStringArrayListExtra("myItem").get(0));
            map.put("ITEMPRECIO", data.getStringArrayListExtra("myItem").get(1));
            map.put("ITEMCANTI",  data.getStringArrayListExtra("myItem").get(2));
            map.put("ITEMVALOR", data.getStringArrayListExtra("myItem").get(3));
            map.put("ITEMSUBTOTAL", data.getStringArrayListExtra("myItem").get(4));
            map.put("ITEMVALORTOTAL", data.getStringArrayListExtra("myItem").get(5));
            map.put("BONIFICADO", data.getStringArrayListExtra("myItem").get(6));

            list.add(map);
            Refresh();
        }
    }
}
