package com.guma.desarrollo.core;

import android.view.animation.AccelerateInterpolator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luis.perez on 28/03/2017.
 */

public class Razon {

    String mIdRazon, mVendedor, mCliente, mFecha, mIdAE, mActividad, mCategoria;

    JSONObject detalles = new JSONObject();
    ArrayList<HashMap<String, String>> contactList = null;

    public Razon(){}

    public Razon(String Vendedor, String Cliente, String Fecha, String IdAE, String Actividad, String Categoria){
        mVendedor=Vendedor;
        mCliente=Cliente;
        mFecha=Fecha;
        mIdAE=IdAE;
        mActividad= Actividad;
        mCategoria=Categoria;
    }

    public String getmIdRazon() {
        return mIdRazon;
    }

    public void setmIdRazon(String idRazon) {
        mIdRazon = idRazon;
    }

    public String getmIdAE() {
        return mIdAE;
    }

    public void setmIdAE(String mIdAE) {
        this.mIdAE = mIdAE;
    }

    public String getmActividad() {
        return mActividad;
    }

    public void setmActividad(String mActividad) {
        this.mActividad = mActividad;
    }

    public String getmCategoria() {
        return mCategoria;
    }

    public void setmCategoria(String mCategoria) {
        this.mCategoria = mCategoria;
    }

    public String getmVendedor() {
        return mVendedor;
    }

    public void setmVendedor(String vendedor) {
        mVendedor = vendedor;
    }

    public String getmCliente() {
        return mCliente;
    }

    public void setmCliente(String cliente) {
        mCliente = cliente;
    }

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String fecha) {
        mFecha = fecha;
    }

    public JSONObject getDetalles(){
        return detalles;
    }

    public void setDetalles(JSONObject detalles){
        this.detalles=detalles;
    }
}
