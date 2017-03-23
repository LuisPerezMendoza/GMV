package com.guma.desarrollo.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryan.espinoza on 23/03/2017.
 */

public class Agenda_model {
    public static List<Visitas> getVisitas(String basedir, Context context) {
        List<Visitas> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "VISITAS", null, "Send"+ "=?", new String[] { "0" }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Visitas tmp = new Visitas();
                    tmp.setmIdPlan(cursor.getString(cursor.getColumnIndex("IdPlan")));
                    tmp.setmIdCliente(cursor.getString(cursor.getColumnIndex("IdCliente")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("Fecha")));
                    tmp.setmLati(cursor.getString(cursor.getColumnIndex("Lati")));
                    tmp.setmLogi(cursor.getString(cursor.getColumnIndex("Logi")));
                    tmp.setmLocal(cursor.getString(cursor.getColumnIndex("Local")));
                    tmp.setmObservacion(cursor.getString(cursor.getColumnIndex("Observacion")));
                    tmp.setmAccion(cursor.getString(cursor.getColumnIndex("Accion")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }
}
