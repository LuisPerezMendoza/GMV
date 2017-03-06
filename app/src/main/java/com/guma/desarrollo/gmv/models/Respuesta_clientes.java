package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Clientes;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Respuesta_clientes {
    private ArrayList<Clientes> results;

    public ArrayList<Clientes> getResults() {
        return results;
    }

    public void setResults(ArrayList<Clientes> results) {
        this.results = results;
    }
}
