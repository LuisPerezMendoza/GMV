package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Clientes {
    String mCliente;
    String mNombre;
    String mDireccion;
    String mRuc;
    String mPuntos;
    String mMoroso;

    public Clientes(String mCliente, String mNombre, String mDireccion, String mRuc, String mPuntos, String mMoroso) {
        this.mCliente = mCliente;
        this.mNombre = mNombre;
        this.mDireccion = mDireccion;
        this.mRuc = mRuc;
        this.mPuntos = mPuntos;
        this.mMoroso = mMoroso;
    }
    public Clientes(){

    }

    public String getmCliente() {
        return mCliente;
    }

    public void setmCliente(String mCliente) {
        this.mCliente = mCliente;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmDireccion() {
        return mDireccion;
    }

    public void setmDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    public String getmRuc() {
        return mRuc;
    }

    public void setmRuc(String mRuc) {
        this.mRuc = mRuc;
    }

    public String getmPuntos() {
        return mPuntos;
    }

    public void setmPuntos(String mPuntos) {
        this.mPuntos = mPuntos;
    }

    public String getmMoroso() {
        return mMoroso;
    }

    public void setmMoroso(String mMoroso) {
        this.mMoroso = mMoroso;
    }
}
