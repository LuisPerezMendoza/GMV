package com.guma.desarrollo.core;

/**
 * Created by alder.hernandez on 03/03/2017.
 */

public class Usuario {
    private String miRuta;
    private String miPassword;
    private String miNombre;

    public Usuario(String miRuta, String miPassword, String miNombre) {
        this.miRuta = miRuta;
        this.miPassword = miPassword;
        this.miNombre = miNombre;
    }
    public Usuario(){

    }
    public String getmiRuta() {
        return miRuta;
    }

    public void setmiRuta(String miRuta) {
        this.miRuta = miRuta;
    }

    public String getmiPassword() {
        return miPassword;
    }

    public void setmiPassword(String mName) {
        this.miPassword = miPassword;
    }

    public String getmiNombre() {
        return miNombre;
    }

    public void setmiNombre(String miNombre) {  this.miNombre = miNombre;  }
}
