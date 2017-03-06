package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.guma.desarrollo.core.Indicadores;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.List;


public class IndicadoresClienteActivity extends AppCompatActivity {

    private float[] yData = {90f, 10f};
    private String[] xData = {"Mitch", "Mohammad" };

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    PieChart pieChart;

    TextView mpVenta,mItemFact,mLimite,mCredito,mNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        mpVenta = (TextView) findViewById(R.id.txtPromedio);
        mItemFact = (TextView) findViewById(R.id.txtCantItem);
        mLimite = (TextView) findViewById(R.id.txtLimite);
        mCredito = (TextView) findViewById(R.id.txtCredito);
        mNombre = (TextView) findViewById(R.id.txtIdNombre);
        List<Indicadores> obj = Clientes_model.getIndicadores(ManagerURI.getDirDb(), IndicadoresClienteActivity.this,preferences.getString("ClsSelected"," --ERROR--"));
        setTitle("PASO 3 [ Pedido ] ");

        mNombre.setText(obj.get(0).getmNombre());
        mpVenta.setText(obj.get(0).getmPromedioVenta3M());
        mItemFact.setText(obj.get(0).getmCantidadItems3M());
        mLimite.setText(obj.get(0).getmLimite());
        mCredito.setText(obj.get(0).getmCredito());

        Log.d("", "mMetas: " + obj.get(0).getmMetas() + " > " + obj.get(0).getmVentasActual());

        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(true);
        addDataSet();

        findViewById(R.id.btnIndicadores).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndicadoresClienteActivity.this,PedidoActivity.class));
                finish();
            }
        });
    }
    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }
        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.grfGris))));
        colors.add(Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.grfCeleste))));
        colors.add(Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.grfNaranja))));
        pieDataSet.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.invalidate();
    }

}
