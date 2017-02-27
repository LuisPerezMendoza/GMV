package com.guma.desarrollo.gmv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Articulo_Repository {
    private static Articulo_Repository repository = new Articulo_Repository();
    private HashMap<String, Articulo> articulos = new HashMap<>();

    public static Articulo_Repository getInstance() {
        return repository;
    }

    private Articulo_Repository() {
        saveLead(new Articulo("13705071", "ARTICULO", "57.96"));
    }

    private void saveLead(Articulo articulo) {
        articulos.put(articulo.getmCodigo(), articulo);   }

    public List<Articulo> getArticulos() {
        return new ArrayList<>(articulos.values());
    }
}
