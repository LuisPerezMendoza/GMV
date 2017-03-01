package com.guma.desarrollo.gmv;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.SQLiteHelper;

import java.io.File;
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
        for(Articulo obj : SQLiteHelper.getArticulos(ManagerURI.getDirDb(), ReferenciasContexto.getContextArticulo())) {
            saveLead(new Articulo(obj.getmCodigo(), obj.getmName(), "","",obj.getmPrecio(),"",""));
            Log.d("", "Articulo_Repository: " +obj.getmCodigo());
        }
       // saveLead(new Articulo("12346", "ARTICULO", "","","52.3","",""));
       // saveLead(new Articulo("12346", "ARTICULO", "","","52.3","",""));

    }


    private void saveLead(Articulo articulo) {articulos.put(articulo.getmCodigo(), articulo);   }

    public List<Articulo> getArticulos() {
        return new ArrayList<>(articulos.values());
    }
}
