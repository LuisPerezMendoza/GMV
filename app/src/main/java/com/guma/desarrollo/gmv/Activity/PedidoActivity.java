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

import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Mora;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.Adapters.Facturas_Leads;
import com.guma.desarrollo.gmv.Adapters.Pedidos_Leads;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoActivity extends AppCompatActivity {
    private ListView listView;
    List<Map<String, Object>> list;
    TextView SubTotal,ivaTotal,Total,txtCount,txtItemName,txtItemCant,txtItemCod,txtItemValor,txtBonificado,txtPrecio;
    ArrayList<Pedidos> fList;
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
                    new Notificaciones().Alert(PedidoActivity.this,"PEDIDO VACIO","INGRESE ARTICULOS AL PEDIDO...").setCancelable(false).setPositiveButton("OK", null).show();
                }
            }
        });

        String IdPedido = preferences.getString("IDPEDIDO", "");
        if (!IdPedido.equals("")){
            setTitle("EDICION DE PEDIDO: "+IdPedido);
            /*List<Pedidos> obj = Pedidos_model.getDetalle(ManagerURI.getDirDb(), PedidoActivity.this,IdPedido);
            if (obj.size()>0){*/
                fList = new ArrayList<>();
                for (Pedidos pedido : Pedidos_model.getDetalle(ManagerURI.getDirDb(), PedidoActivity.this,IdPedido)){
                    fList.add(pedido);
                }
                listView.setAdapter(new Pedidos_Leads(this, fList));

            /*Toast.makeText(this, obj.get(0).getmArticulo(), Toast.LENGTH_SHORT).show();
                txtItemName = (TextView)findViewById(R.id.tvListItemName);
                txtItemCant = (TextView)findViewById(R.id.Item_cant);
                txtItemCod = (TextView)findViewById(R.id.item_codigo);
                txtItemValor = (TextView)findViewById(R.id.Item_valor);
                txtBonificado = (TextView)findViewById(R.id.tbListBonificado);
                txtPrecio = (TextView)findViewById(R.id.tvListItemPrecio);
            }*/

        }


    }
    public void Refresh(){
        float vLine = 0;
        listView.setAdapter(
                new SimpleAdapter(
                        this,
                        list,
                        R.layout.list_item_carrito, new String[] {"ITEMNAME", "ITEMCANTI","ITEMCODIGO","ITEMVALOR","BONIFICADO","PRECIO" },
                        new int[] {R.id.tvListItemName,R.id.Item_cant,R.id.item_codigo,R.id.Item_valor,R.id.tbListBonificado,R.id.tvListItemPrecio}));


        for (Map<String, Object> obj : list){
            vLine     += Float.parseFloat(obj.get("ITEMVALOR").toString());
        }
        Total.setText("TOTAL C$ "+ String.valueOf(vLine));
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
            Map<String, Object> map = new HashMap<>();
            map.put("ITEMNAME", data.getStringArrayListExtra("myItem").get(0));
            map.put("ITEMCODIGO", data.getStringArrayListExtra("myItem").get(1));
            map.put("ITEMCANTI",  data.getStringArrayListExtra("myItem").get(2));
            map.put("ITEMVALOR", data.getStringArrayListExtra("myItem").get(3));
            map.put("ITEMSUBTOTAL", data.getStringArrayListExtra("myItem").get(4));
            map.put("ITEMVALORTOTAL", data.getStringArrayListExtra("myItem").get(5));
            map.put("BONIFICADO", data.getStringArrayListExtra("myItem").get(6));
            map.put("PRECIO", data.getStringArrayListExtra("myItem").get(7));

            list.add(map);
            Refresh();
        }
    }
}
