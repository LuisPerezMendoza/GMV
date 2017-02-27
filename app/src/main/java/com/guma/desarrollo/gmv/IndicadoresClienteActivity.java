package com.guma.desarrollo.gmv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


public class IndicadoresClienteActivity extends AppCompatActivity {

    private float[] yData = {90f, 10f};
    private String[] xData = {"Mitch", "Mohammad" };

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PASO 3 [Pedido]");

        findViewById(R.id.btnIndicadores).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndicadoresClienteActivity.this,PedidoActivity.class));
                finish();
            }
        });

        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(true);
        addDataSet();




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
