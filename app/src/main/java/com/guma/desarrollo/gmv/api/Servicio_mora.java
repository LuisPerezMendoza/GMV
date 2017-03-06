package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.gmv.models.Respuesta_mora;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public interface Servicio_mora {
    @GET("ClientesMora")
    Call<Respuesta_mora> obtenerListaClienteMora();
}
