package com.guma.desarrollo.core;

/**
 * Created by alder.hernandez on 03/03/2017.
 */

public class Usuario {
    private String mRuta;
    private String mPassword;
    private String mNombre;

    public Usuario(String mRuta, String mPassword, String mNombre) {
        this.mRuta = mRuta;
        this.mPassword = mPassword;
        this.mNombre = mNombre;
    }

    public String getmRuta() {
        return mRuta;
    }

    public void setmRuta(String mRuta) {
        this.mRuta = mRuta;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }
}
