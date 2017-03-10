package com.guma.desarrollo.core;

/**
 * Created by alder.hernandez on 03/03/2017.
 */

public class Usuario {
    private String mIdUser;
    private String mUsuario;
    private String mNombre;
    private String mRol;

    public Usuario(String mIdUser, String mUsuario, String mNombre, String mRol) {
        this.mIdUser = mIdUser;
        this.mUsuario = mUsuario;
        this.mNombre = mNombre;
        this.mRol = mRol;
    }

    public String getmIdUser() {
        return mIdUser;
    }

    public String getmUsuario() {
        return mUsuario;
    }

    public String getmNombre() {
        return mNombre;
    }

    public String getmRol() {
        return mRol;
    }

    public void setmUsuario(String mUsuario) {
        this.mUsuario = mUsuario;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public void setmRol(String mRol) {
        this.mRol = mRol;
    }
    public void setmIdUser(String mIdUser) {
        this.mIdUser = mIdUser;
    }
}
