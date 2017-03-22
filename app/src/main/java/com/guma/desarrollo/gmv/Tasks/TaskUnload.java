package com.guma.desarrollo.gmv.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.Activity.AgendaActivity;
import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.Notificaciones;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Respuesta_pedidos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maryan.espinoza on 08/03/2017.
 */

public class TaskUnload extends AsyncTask<Integer,Integer,String> {
    public ProgressDialog pdialog;
    Context cnxt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public TaskUnload(Context cnxt) {
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
    protected String doInBackground(Integer... para) {
        List<Cobros> obj = Cobros_model.getCobros(ManagerURI.getDirDb(), cnxt);
        if (obj.size()>0){
            Class_retrofit.Objfit().create(Servicio.class).InserCorbos(new Gson().toJson(obj)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        if (Boolean.valueOf(response.body())){
                            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),cnxt,"DELETE FROM COBROS");
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {}
            });
        }else {
            pdialog.dismiss();
        }
        pdialog.dismiss();
        editor.putString("lstUnload", Clock.getTimeStamp()).apply();

        List<Pedidos> listPedidos = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(),cnxt);
        Gson gson = new Gson();
        if (listPedidos.size()>0) {
            Class_retrofit.Objfit().create(Servicio.class).enviarPedidos(gson.toJson(listPedidos)).enqueue(new Callback<Respuesta_pedidos>() {
                @Override
                public void onResponse(Call<Respuesta_pedidos> call, Response<Respuesta_pedidos> response) {
                    if(response.isSuccessful()){
                        Respuesta_pedidos pedidoRespuesta = response.body();
                        /*new Notificaciones().Alert(cnxt,"EXITO","PEDIDOS ENVIADOS...")
                                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();*/
                        Toast.makeText(cnxt, "PEDIDOS ENVIADOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(cnxt, "ERROR AL ENVIAR RESPUESTA: "+response.body(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_pedidos> call, Throwable t) {
                    /*new Notificaciones().Alert(AgendaActivity.this,"ERROR",t.getMessage().toString())
                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();*/
                }
            });
        }else{
            /*new Notificaciones().Alert(AgendaActivity.this,"ERROR","NO HAY PEDIDOS...")
                    .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).show();*/
        }

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
    private void Alerta(String Titulo,String Mensaje) {
        new AlertDialog.Builder(cnxt).setTitle(Titulo).setMessage(Mensaje).setCancelable(false).setPositiveButton("OK",null).show();
    }

}