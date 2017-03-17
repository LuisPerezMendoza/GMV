package com.guma.desarrollo.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alder.hernandez on 13/03/2017.
 */

public class Pedidos {
    String mIdPedido,mVendedor,mCliente,mNombre,mFecha,mArticulo,mDescripcion,mCantidad,mPrecio,mBonificado;
    private List<String> detalles = new ArrayList<String>();

    public Pedidos(String mIdPedido, String mVendedor, String mCliente, String mNombre, String mFecha, String mArticulo, String mDescripcion, String mCantidad, String mPrecio, String mBonificado, List<String> detalles) {
        this.mIdPedido = mIdPedido;
        this.mVendedor = mVendedor;
        this.mCliente = mCliente;
        this.mNombre = mNombre;
        this.mFecha = mFecha;
        this.mArticulo = mArticulo;
        this.mDescripcion = mDescripcion;
        this.mCantidad = mCantidad;
        this.mPrecio = mPrecio;
        this.mBonificado = mBonificado;
        this.detalles = detalles;
    }


    public Pedidos(){ }

    public String getmIdPedido() {
        return mIdPedido;
    }

    public void setmIdPedido(String mIdPedido) {
        this.mIdPedido = mIdPedido;
    }

    public String getmVendedor() {
        return mVendedor;
    }

    public void setmVendedor(String mVendedor) {
        this.mVendedor = mVendedor;
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

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public String getmArticulo() {
        return mArticulo;
    }

    public void setmArticulo(String mArticulo) {
        this.mArticulo = mArticulo;
    }

    public String getmDescripcion() {
        return mDescripcion;
    }

    public void setmDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getmCantidad() {
        return mCantidad;
    }

    public void setmCantidad(String mCantidad) {
        this.mCantidad = mCantidad;
    }

    public String getmPrecio() {return mPrecio;}

    public void setmPrecio(String mPrecio) {
        this.mPrecio = mPrecio;
    }

    public String getmBonificado() {
        return mBonificado;
    }

    public void setmBonificado(String mBonificado) {
        this.mBonificado = mBonificado;
    }

    public List<String> getdetalles() {
        return detalles;
    }
    public void setdetalles(List<String> detalles) {
        this.detalles = detalles;
    }
}
