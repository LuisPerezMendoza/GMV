package com.guma.desarrollo.gmv.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import com.guma.desarrollo.core.Clock;

/**
 * Created by maryan.espinoza on 08/03/2017.
 */

public class TaskUnload extends AsyncTask<Integer,Integer,String> {
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
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... para) {
        editor.putString("lstUnload", Clock.getTimeStamp()).apply();
        //Alerta();
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