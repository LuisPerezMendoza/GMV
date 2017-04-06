package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static List<Map<String, Object>> getAgenda(String basedir, Context context) {
        //List<String> lista = new ArrayList<>();
        List<Map<String, Object>> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "VCLIENTES", null, null, null, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("LUNES",cursor.getString(cursor.getColumnIndex("Lunes")));
                    map.put("MARTES",cursor.getString(cursor.getColumnIndex("Martes")));
                    map.put("MIERCOLES",cursor.getString(cursor.getColumnIndex("Miercoles")));
                    map.put("JUEVES",cursor.getString(cursor.getColumnIndex("Jueves")));
                    map.put("VIERNES",cursor.getString(cursor.getColumnIndex("Viernes")));
                    lista.add(map);
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
    public static void  SaveAgenda(Context context, List<Map<String, Object>> mTop,  List<Map<String, Object>> mDetalle){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM AGENDA");
            String mIdpl = "PLN" + SQLiteHelper.getId(ManagerURI.getDirDb(), context, "PLAN");

            ContentValues cntTop = new ContentValues();
            cntTop.put("IdPlan" , mIdpl);
            cntTop.put("Vendedor" , mTop.get(0).get("Vendedor").toString());
            cntTop.put("Ruta" , mTop.get(0).get("mRuta").toString());
            cntTop.put("Inicia" , mTop.get(0).get("mSemanaIni").toString());
            cntTop.put("Termina" , mTop.get(0).get("mSemanaEnd").toString());
            cntTop.put("Zona" , mTop.get(0).get("mZona").toString());
            myDataBase.insert("AGENDA", null, cntTop );

            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM VCLIENTES");
            ContentValues cntDetalle = new ContentValues();
            cntDetalle.put("IdPlan" , mIdpl);
            cntDetalle.put("Lunes" , mDetalle.get(0).get("LUNES").toString());
            cntDetalle.put("Martes" , mDetalle.get(0).get("MARTES").toString());
            cntDetalle.put("Miercoles" , mDetalle.get(0).get("MIERCOLES").toString());
            cntDetalle.put("Jueves" , mDetalle.get(0).get("JUEVES").toString());
            cntDetalle.put("Viernes" , mDetalle.get(0).get("VIERNES").toString());
            myDataBase.insert("VCLIENTES", null, cntDetalle );


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
}
