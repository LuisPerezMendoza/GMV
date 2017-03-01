package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.gmv.models.ArticuloRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public interface ArticuloApiService {
    @GET("ARTICULOS")
    Call<ArticuloRespuesta> obtenerListaArticulos();
}
