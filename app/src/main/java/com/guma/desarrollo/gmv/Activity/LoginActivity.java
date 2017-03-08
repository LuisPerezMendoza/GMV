package com.guma.desarrollo.gmv.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.core.Usuario;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.UsuarioRespuesta;


import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
    private Retrofit retrofit;
    public ProgressDialog pdialog;

    public AutoCompleteTextView usuario;
    public EditText pass;
    public String useri;
    public String passw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        checked = preferences.getBoolean("pref", false);

        try {
            new SQLiteHelper(ManagerURI.getDirDb(),LoginActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = (AutoCompleteTextView)findViewById(R.id.txtUsuerio);
                pass = (EditText)findViewById(R.id.password);
                passw = pass.getText().toString();
                useri = usuario.getText().toString();
                //Toast.makeText(LoginActivity.this, ManagerURI.getURL_Base(), Toast.LENGTH_SHORT).show();
                pdialog = ProgressDialog.show(LoginActivity.this, "","Procesando. Porfavor Espere...", true);
                myTarea d = new myTarea();
                d.execute();
            }
        });

        if (checked == true){
            startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
            finish();
        }
    }


    private class myTarea extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            retrofit = new Retrofit.Builder().baseUrl(ManagerURI.getURL_Base()).addConverterFactory(GsonConverterFactory.create()).build();
            Servicio service = retrofit.create(Servicio.class);

            final Call<UsuarioRespuesta> ArticuloRespuestaCall = service.obtenerListaUsuario(useri,passw);
            ArticuloRespuestaCall.enqueue(new Callback<UsuarioRespuesta>() {
                @Override
                public void onResponse(Call<UsuarioRespuesta> call, Response<UsuarioRespuesta> response) {
                    if(response.isSuccessful()){
                        UsuarioRespuesta usuarioRespuesta = response.body();
                        //Toast.makeText(LoginActivity.this, String.valueOf(usuarioRespuesta.getCount()), Toast.LENGTH_SHORT).show();
                        editor.putBoolean("pref", checked);
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "ERROR AL AUTENTICARSE", Toast.LENGTH_SHORT).show();
                        pdialog.dismiss();
                        checked = !checked;
                    }
                }
                @Override
                public void onFailure(Call<UsuarioRespuesta> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(LoginActivity.this, "NO SE ENCONTRO USUARIO", Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                    checked = !checked;
                }
            });
            return null;
        }
    }

}










