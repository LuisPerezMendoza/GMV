package com.guma.desarrollo.gmv.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Respuesta_articulos;
import com.guma.desarrollo.gmv.models.Respuesta_clientes;
import com.guma.desarrollo.gmv.models.Respuesta_indicadores;
import com.guma.desarrollo.gmv.models.Respuesta_mora;
import com.guma.desarrollo.gmv.models.Respuesta_puntos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maryan.espinoza on 08/03/2017.
 */

public class TaskDownload extends AsyncTask<Integer,Integer,String> {
    public ProgressDialog pdialog;
    Context cnxt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String TAG = "AgendaActivity";
    public TaskDownload(Context cnxt) {
        this.cnxt = cnxt;
        preferences = PreferenceManager.getDefaultSharedPreferences(cnxt);
        editor = preferences.edit();
    }

    @Override
    protected void onPreExecute() {
        pdialog = ProgressDialog.show(cnxt, "","Iniciando...", true);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params) {
        Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaArticulos()
                .enqueue(new Callback<Respuesta_articulos>() {
                    @Override
                    public void onResponse(Call<Respuesta_articulos> call, Response<Respuesta_articulos> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Articulos.... ");
                            Respuesta_articulos articuloRespuesta = response.body();
                            Log.d(TAG, "onResponse: Articulos " + articuloRespuesta.getCount());
                            Articulos_model.SaveArticulos(cnxt,articuloRespuesta.getResults());

                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: " + response.errorBody() );
                            Toast.makeText(cnxt, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Respuesta_articulos> call, Throwable t) {
                        pdialog.dismiss();
                    }
                });



        Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaClienteMora()
                .enqueue(new Callback<Respuesta_mora>() {
                    @Override
                    public void onResponse(Call<Respuesta_mora> call, Response<Respuesta_mora> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Puntos.... ");
                            Respuesta_mora moraRespuesta = response.body();
                            Log.d(TAG, "onResponse: Mora " + moraRespuesta.getCount());
                            Clientes_model.SaveMora(cnxt,moraRespuesta.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: " + response.errorBody() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta_mora> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + t.getMessage() );

                    }
                });


        Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaClienteIndicadores()
                .enqueue(new Callback<Respuesta_indicadores>() {
                    @Override
                    public void onResponse(Call<Respuesta_indicadores> call, Response<Respuesta_indicadores> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Informacion de Clientes.... ");
                            Respuesta_indicadores IndicadorRespuesta = response.body();
                            Log.d(TAG, "onResponse: Indicadores " + IndicadorRespuesta.getCount() );
                            Clientes_model.SaveIndicadores(cnxt,IndicadorRespuesta.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: " + response.errorBody() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta_indicadores> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + t.getMessage() );

                    }
                });



        Class_retrofit.Objfit().
                create(Servicio.class).
                obtenerListaClientes().
                enqueue(new Callback<Respuesta_clientes>() {
                    @Override
                    public void onResponse(Call<Respuesta_clientes> call, Response<Respuesta_clientes> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Cargado Clientes.... ");
                            Respuesta_clientes clRespuesta = response.body();
                            Log.d(TAG, "onResponse: Clientes "  + clRespuesta.getCount());
                            Clientes_model.SaveClientes(cnxt,clRespuesta .getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: " + response.errorBody() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta_clientes> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + t.getMessage() );

                    }
                });


        Class_retrofit.Objfit().create(Servicio.class)
                .obtenerFacturasPuntos()
                .enqueue(new Callback<Respuesta_puntos>() {
                    @Override
                    public void onResponse(Call<Respuesta_puntos> call, Response<Respuesta_puntos> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Cargado Puntos.... ");
                            Respuesta_puntos clpuntos = response.body();
                            Log.d(TAG, "onResponse: Facturas "  + clpuntos.getCount());
                            Clientes_model.SaveFacturas(cnxt,clpuntos.getResults());
                            Alerta();
                            pdialog.dismiss();
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: " + response.errorBody() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta_puntos> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: " + t.getMessage() );
                    }
                });
        Log.d(TAG, "onResponse: " + Clock.getTimeStamp() );
        editor.putString("lstDownload", Clock.getTimeStamp());
        editor.apply();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {


        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
    }
    private void Alerta() {
        new AlertDialog.Builder(cnxt).setTitle("RECIBIDO").setMessage("Informacion Recibida...").setCancelable(false).setPositiveButton("OK",null).show();
    }
}