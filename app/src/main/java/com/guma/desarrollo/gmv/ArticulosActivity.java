package com.guma.desarrollo.gmv;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ArticulosActivity extends AppCompatActivity {
    private ListView listView;
    EditText Inputcant;
    Float vLinea,SubTotalLinea,TotalFinalLinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new Articulo_Leads(this, Articulo_Repository.getInstance().getArticulos()));
        setTitle("ARTICULOS");
        final ArrayList<String> strings = new ArrayList<String>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
                LayoutInflater li = LayoutInflater.from(ArticulosActivity.this);
                View promptsView = li.inflate(R.layout.input_cant, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArticulosActivity.this);
                alertDialogBuilder.setView(promptsView);
                Inputcant = (EditText) promptsView.findViewById(R.id.txtFrmCantidad);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Articulo mnotes = (Articulo) parent.getItemAtPosition(position);

                                        vLinea = Float.parseFloat(mnotes.getmPrecio()) * Float.parseFloat(Inputcant.getText().toString());
                                        SubTotalLinea = Float.parseFloat(String.valueOf(vLinea * 0.15));
                                        TotalFinalLinea = vLinea + SubTotalLinea;


                                        Toast.makeText(ArticulosActivity.this, mnotes.getmName(),Toast.LENGTH_SHORT).show();
                                        strings.add(mnotes.getmName());
                                        strings.add(mnotes.getmCodigo());
                                        strings.add(Inputcant.getText().toString());
                                        strings.add(vLinea.toString());
                                        strings.add(SubTotalLinea.toString());
                                        strings.add(TotalFinalLinea.toString());
                                        getIntent().putStringArrayListExtra("myItem",strings);
                                        setResult(RESULT_OK,getIntent());
                                        finish();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }}).create().show();


                /*new AlertDialog.Builder(ArticulosActivity.this)
                        .setTitle("CONFIRMACION")
                        .setMessage("builder form")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listView.setFocusable(true);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();*/
                //Lead currentLead = listView.getItem(position);
                //Toast.makeText(ArticulosActivity.this,"Iniciar screen de detalle para: \n" + currentLead.getName(),Toast.LENGTH_SHORT).show();

            }
        });

    }

}
