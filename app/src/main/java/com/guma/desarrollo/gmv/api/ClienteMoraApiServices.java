package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.gmv.models.ArticuloRespuesta;
import com.guma.desarrollo.gmv.models.ClienteMoraRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public interface ClienteMoraApiServices {
    @GET("ClientesMora")
    Call<ClienteMoraRespuesta> obtenerListaClienteMora();
}
