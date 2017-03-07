package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Articulo;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public class Respuesta_articulos {
    private ArrayList<Articulo> results;
    private int count;
    public int getCount() {
        return count = results.size();
    }
    public ArrayList<Articulo> getResults() {
        return results;
    }
}
