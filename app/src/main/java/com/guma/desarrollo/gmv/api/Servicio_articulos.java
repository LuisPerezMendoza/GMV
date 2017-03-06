package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.gmv.models.Respuesta_articulos;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public interface Servicio_articulos {
    @GET("ARTICULOS")
    Call<Respuesta_articulos> obtenerListaArticulos();
}
