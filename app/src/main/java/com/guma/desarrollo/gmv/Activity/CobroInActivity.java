package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.guma.desarrollo.core.Mora;
import com.guma.desarrollo.core.Usuario;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Usuario_model;
import com.guma.desarrollo.gmv.R;

import java.util.List;

public class CobroInActivity extends AppCompatActivity {
    TextView mSaldo,mLimite,m30,m60,m90,m120,md120,mTotal;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        mSaldo = (TextView) findViewById(R.id.txtmSaldo);
        mLimite = (TextView) findViewById(R.id.txtmLimite);
        m30 = (TextView) findViewById(R.id.txtm30);
        m60= (TextView) findViewById(R.id.txtm60);
        m90= (TextView) findViewById(R.id.txtm90);
        m120= (TextView) findViewById(R.id.txtm120);
        md120 = (TextView) findViewById(R.id.txtmd120);
        mTotal = (TextView) findViewById(R.id.txtmTotal);



        Spinner spinner = (Spinner) findViewById(R.id.sp_transac);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"EFECTIVO","CHEQUE","TRANSFERENCIA"}));
        findViewById(R.id.btnSave_Cobro_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CobroInActivity.this)
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

        List<Mora> obj = Clientes_model.getMora(ManagerURI.getDirDb(), CobroInActivity.this,preferences.getString("ClsSelected"," --ERROR--"));
        setTitle("PASO 2 [ Cobro ] - " + obj.get(0).getmNombre());
        mSaldo.setText("C$ " + obj.get(0).getmSaldo());
        mLimite.setText("C$ " + obj.get(0).getmLimite());
        m30.setText("C$ " + obj.get(0).getmD30());
        m60.setText("C$ " + obj.get(0).getmD60());
        m90.setText("C$ " + obj.get(0).getmD90());
        m120.setText("C$ " + obj.get(0).getmD120());
        md120.setText("C$ " + obj.get(0).getmMd120());
        String Final= String.valueOf(Float.parseFloat(obj.get(0).getmD30()) + Float.parseFloat(obj.get(0).getmD60()) + Float.parseFloat(obj.get(0).getmD90()) + Float.parseFloat(obj.get(0).getmD120())+Float.parseFloat(obj.get(0).getmMd120()));
        mTotal.setText("C$ " + Final);









    }


}
