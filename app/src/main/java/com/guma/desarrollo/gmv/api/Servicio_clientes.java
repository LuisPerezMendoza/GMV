package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.gmv.models.Respuesta_clientes;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public interface Servicio_clientes {
    @GET("Clientes")
    Call<Respuesta_clientes> obtenerListaClientes();
}
