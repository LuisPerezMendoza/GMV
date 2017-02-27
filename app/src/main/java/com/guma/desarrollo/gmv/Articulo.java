package com.guma.desarrollo.gmv;

import java.util.UUID;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Articulo {
    private String mCodigo;
    private String mName;
    private String mPrecio;



    public Articulo(String mCodigo, String mName, String mPrecio) {
        this.mCodigo = mCodigo;
        this.mName = mName;
        this.mPrecio = mPrecio;
    }


    public String getmCodigo() {
        return mCodigo;
    }


    public void setmCodigo(String mCodigo) {
        this.mCodigo = mCodigo;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPrecio() {
        return mPrecio;
    }

    public void setmPrecio(String mPrecio) {
        this.mPrecio = mPrecio;
    }
}
