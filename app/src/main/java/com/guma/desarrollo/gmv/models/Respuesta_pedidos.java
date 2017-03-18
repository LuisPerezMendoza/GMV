package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Pedidos;

import java.util.ArrayList;

/**
 * Created by alder.hernandez on 17/03/2017.
 */

public class Respuesta_pedidos {
    private static int count;
    private ArrayList<Pedidos> results;

    public ArrayList<Pedidos> getResults() {
        return results;
    }
    public  int getCount() {
        return count = results.size();
    }
    public void setResults(ArrayList<Pedidos> results) {
        this.results = results;
    }
}
