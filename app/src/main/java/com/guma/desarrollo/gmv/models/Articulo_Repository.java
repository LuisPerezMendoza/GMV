package com.guma.desarrollo.gmv.models;

import android.util.Log;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Activity.ReferenciasContexto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        for(Articulo obj : Articulos_model.getArticulos(ManagerURI.getDirDb(), ReferenciasContexto.getContextArticulo())) {
            saveLead(new Articulo(obj.getmCodigo(), obj.getmName(), obj.getmExistencia(),obj.getmUnidad(),obj.getmPrecio(),"",obj.getmReglas()));
        }
    }


    private void saveLead(Articulo articulo) {articulos.put(articulo.getmCodigo(), articulo);   }

    public List<Articulo> getArticulos() {
        List<Articulo> OrderArticulo= new ArrayList<>(articulos.values());
        Collections.sort(OrderArticulo, new Comparator<Articulo>(){
            public int compare(Articulo obj1, Articulo obj2)
            {
                return obj1.getmName().compareToIgnoreCase(obj2.getmName());
            }
        });
        return OrderArticulo;
    }
}
