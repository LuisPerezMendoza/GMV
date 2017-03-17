package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ResumenActivity extends AppCompatActivity {
    TextView lblNombreClliente,lblNombreVendedor,countArti,SubTotal,ivaTotal,Total,Atendio,txtidPedido;
    private static ListView listView;
    float vLine = 0,subValor=0,vFinal=0;
    ArrayList<Pedidos> mPedido = new ArrayList<>();
    ArrayList<Pedidos> mDetallePedido = new ArrayList<>();
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    String CodCls,idPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("RESUMEN");
        Intent ints = getIntent();
        listView = (ListView) findViewById(R.id.ListView1);


        TextView prueba = (TextView)findViewById(R.id.tvListItemName);

        //Toast.makeText(this, "el texto es ->"+ prueba.getText().toString(), Toast.LENGTH_SHORT).show();
        final List<Map<String, Object>> list = (List<Map<String, Object>>) ints.getSerializableExtra("LIST");
        listView.setAdapter(new SimpleAdapter(this, list,R.layout.list_item_resumen,
                new String[] {"ITEMNAME", "ITEMCANTI","ITEMPRECIO","ITEMVALOR","BONIFICADO" },
                new int[] { R.id.tvListItemName,R.id.Item_cant,R.id.tvListItemPrecio,R.id.Item_valor,R.id.tvListBonificado }));
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        CodCls =  preferences.getString("ClsSelected","");

        int key = SQLiteHelper.getIdTemporal(ManagerURI.getDirDb(),ResumenActivity.this,"PEDIDOS");
        idPedido = "F09-" + "P"+ Clock.getIdDate()+String.valueOf(key);

        SubTotal = (TextView) findViewById(R.id.SubTotal);
        ivaTotal = (TextView) findViewById(R.id.ivaTotal);
        Total = (TextView) findViewById(R.id.Total);
        Atendio = (TextView) findViewById(R.id.NombreVendedor);
        txtidPedido = (TextView) findViewById(R.id.IdPedido);

        txtidPedido.setText(idPedido.toString());
        Atendio.setText(preferences.getString("NOMBRE","").toString());
        Toast.makeText(this, idPedido.toString(), Toast.LENGTH_SHORT).show();

        lblNombreClliente = (TextView) findViewById(R.id.NombreCliente);
        lblNombreVendedor = (TextView) findViewById(R.id.NombreVendedor);
        lblNombreVendedor.setText("");
        lblNombreClliente.setText(ints.getStringExtra("NombreCliente"));

        countArti = (TextView) findViewById(R.id.txtCountArti);
        countArti.setText(listView.getCount() +" Articulo");

        for (Map<String, Object> obj : list){
            vLine     += Float.parseFloat(obj.get("ITEMVALOR").toString());
            subValor  += Float.parseFloat(obj.get("ITEMSUBTOTAL").toString());
            vFinal    += Float.parseFloat(obj.get("ITEMVALORTOTAL").toString());
        }
        SubTotal.setText("SubTotal C$ " + String.valueOf(vLine));
        ivaTotal.setText("IVA C$ " + String.valueOf(subValor));
        Total.setText("TOTAL C$ "+ String.valueOf(vFinal));

        findViewById(R.id.btnSaveSale).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ResumenActivity.this)
                        .setTitle("CONFIRMACION")
                        .setMessage("Informacion Guardada")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (CodCls!="") {
                                    int key = SQLiteHelper.getId(ManagerURI.getDirDb(),ResumenActivity.this,"PEDIDOS");
                                    idPedido = "F09-" + "P"+ Clock.getIdDate()+String.valueOf(key);
                                    Float nTotal= 0.0f;
                                    for (Map<String, Object> obj : list) {
                                        nTotal += Float.parseFloat(obj.get("ITEMVALOR").toString());
                                    }
                                    preferences.getString("NameClsSelected", " CLIENTE NO ENCONTRADO");
                                    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy ");
                                    String strDate = mdformat.format(Calendar.getInstance().getTime());
                                    Pedidos tmp = new Pedidos();
                                    tmp.setmIdPedido(idPedido);
                                    tmp.setmVendedor("F09");
                                    tmp.setmCliente(CodCls);
                                    tmp.setmNombre(preferences.getString("NameClsSelected", " CLIENTE NO ENCONTRADO"));
                                    tmp.setmFecha(strDate.toString());
                                    tmp.setmPrecio(String.valueOf(nTotal));
                                    mPedido.add(tmp);
                                    Pedidos_model.SavePedido(ResumenActivity.this, mPedido);
                                    for (Map<String, Object> obj : list) {
                                        Pedidos tmpDetalle = new Pedidos();
                                        tmpDetalle.setmIdPedido(idPedido);
                                        tmpDetalle.setmArticulo(obj.get("ITEMPRECIO").toString());
                                        tmpDetalle.setmDescripcion(obj.get("ITEMNAME").toString());
                                        tmpDetalle.setmCantidad(obj.get("ITEMCANTI").toString());
                                        tmpDetalle.setmPrecio(obj.get("ITEMVALOR").toString());
                                        tmpDetalle.setmBonificado(obj.get("BONIFICADO").toString());
                                        mDetallePedido.add(tmpDetalle);
                                        Pedidos_model.SaveDetallePedido(ResumenActivity.this, mDetallePedido);
                                    }
                                    finish();
                                }else {
                                    Toast.makeText(ResumenActivity.this, "ERROR AL GUARDAR PEDIDO, INTENTELO MAS TARDE", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        });
    }
}
