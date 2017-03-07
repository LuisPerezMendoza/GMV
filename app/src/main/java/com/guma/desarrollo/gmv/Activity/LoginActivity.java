package com.guma.desarrollo.gmv.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.R;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity  {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;

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
                checked = !checked;
                editor.putBoolean("pref", checked);
                //editor.putString("User",vUsuario);
                //editor.putString("Password",vPassword);
                editor.apply();
                startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
                finish();
            }
        });

        if (checked == true){
            startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
            finish();
        }
    }

}










