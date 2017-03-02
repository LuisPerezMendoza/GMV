package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.guma.desarrollo.gmv.R;

import java.util.List;
import java.util.Map;

public class ResumenActivity extends AppCompatActivity {
    TextView lblNombreClliente,lblNombreVendedor,countArti,SubTotal,ivaTotal,Total;;
    private static ListView listView;
    float vLine = 0,subValor=0,vFinal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("RESUMEN");
        Intent ints = getIntent();
        listView = (ListView) findViewById(R.id.ListView1);
        List<Map<String, Object>> list = (List<Map<String, Object>>) ints.getSerializableExtra("LIST");
        listView.setAdapter(new SimpleAdapter(this, list,R.layout.list_item_resumen,
                new String[] {"ITEMNAME", "ITEMCANTI","ITEMPRECIO","ITEMVALOR" }, new int[] { R.id.tvListItemName,R.id.Item_cant,R.id.tvListItemPrecio,R.id.Item_valor }));


        SubTotal = (TextView) findViewById(R.id.SubTotal);
        ivaTotal = (TextView) findViewById(R.id.ivaTotal);
        Total = (TextView) findViewById(R.id.Total);



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
                                finish();
                            }
                        }).show();
            }
        });


    }

}
