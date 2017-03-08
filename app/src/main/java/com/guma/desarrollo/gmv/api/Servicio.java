package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.gmv.models.Respuesta_articulos;
import com.guma.desarrollo.gmv.models.Respuesta_clientes;
import com.guma.desarrollo.gmv.models.Respuesta_indicadores;
import com.guma.desarrollo.gmv.models.Respuesta_mora;

import com.guma.desarrollo.gmv.models.Respuesta_usuario;

import com.guma.desarrollo.gmv.models.Respuesta_puntos;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public interface Servicio {
    @GET("ARTICULOS")
    Call<Respuesta_articulos> obtenerListaArticulos();

    @GET("Clientes")
    Call<Respuesta_clientes> obtenerListaClientes();

    @GET("ClientesIndicadores")
    Call<Respuesta_indicadores> obtenerListaClienteIndicadores();

    @GET("ClientesMora")
    Call<Respuesta_mora> obtenerListaClienteMora();

    @FormUrlEncoded
    @POST("Login")
    Call<Respuesta_usuario> obtenerListaUsuario(@Field("usuario") String apiKey, @Field("pass") String apiKey2);

    @GET("Puntos")
    Call<Respuesta_puntos> obtenerFacturasPuntos();

}
