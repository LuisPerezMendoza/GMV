package com.guma.desarrollo.gmv.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.R;

public class RptHoyActivity extends AppCompatActivity {
    TextView mPedido,mTotalPedido,mCobro,mTotalCobro,mOtros;
    float mCobroTotal,mPedidoTotal;
    int countCobro=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_hoy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        setTitle("REPORTE DEL DIA");


        for(Cobros obj : Cobros_model.getCobros(ManagerURI.getDirDb(), RptHoyActivity.this)) {
            mCobroTotal += Float.parseFloat(obj.getmImporte());
            countCobro++;
        }

        mPedido =(TextView) findViewById(R.id.txtRptPedido);
        mTotalPedido =(TextView) findViewById(R.id.txtRptPedidoMonto);

        mCobro =(TextView) findViewById(R.id.txtRptCobro);
        mCobro.setText(String.valueOf(countCobro));
        mTotalCobro =(TextView) findViewById(R.id.txtRptCobroMonto);
        mTotalCobro.setText("Equivalente a C$" + String.valueOf(mCobroTotal));

        mOtros =(TextView) findViewById(R.id.txtRptOtros);



    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
}
