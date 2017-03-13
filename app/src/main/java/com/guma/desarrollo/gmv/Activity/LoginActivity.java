package com.guma.desarrollo.gmv.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.core.Usuario;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Respuesta_usuario;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity  {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
    private Retrofit retrofit;
    public ProgressDialog pdialog;

    public EditText usuario;
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


        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = (EditText) findViewById(R.id.txtUsuerio);
                pass = (EditText)findViewById(R.id.password);
                if (TextUtils.isEmpty(usuario.getText())){
                    usuario.setError("CAMPO REQUERIDO");
                }else if(TextUtils.isEmpty(usuario.getText())){
                    pass.setError("CAMPO REQUERIDO");
                } else {

                    passw = pass.getText().toString();
                    useri = usuario.getText().toString();

                    pdialog = ProgressDialog.show(LoginActivity.this, "", "Procesando. Porfavor Espere...", true);
                    new TaskLogin().execute();
                }

            }
        });

        if (checked == true){
            startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
            finish();
        }
    }


    private class TaskLogin extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            Class_retrofit.Objfit().create(Servicio.class).obtenerListaUsuario(useri,passw).enqueue(new Callback<Respuesta_usuario>() {
                @Override
                public void onResponse(Call<Respuesta_usuario> call, Response<Respuesta_usuario> response) {
                    if(response.isSuccessful()){
                        Respuesta_usuario usuarioRespuesta = response.body();

                        editor.putString("NOMBRE",usuarioRespuesta.getResults().get(0).getmNombre());
                        editor.putString("USUARIO",usuarioRespuesta.getResults().get(0).getmIdUser());
                        editor.putString("ROL",usuarioRespuesta.getResults().get(0).getmRol());

                        editor.putBoolean("pref", !checked);
                        editor.apply();
                        pdialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "ERROR AL AUTENTICARSE", Toast.LENGTH_SHORT).show();
                        pdialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_usuario> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                    //checked = !checked;
                }
            });
            return null;
        }
    }

}










