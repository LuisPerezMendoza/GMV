package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public class Clientes_model {
    public static void  SaveClienteMora(Context context, ArrayList<ClienteMora> MORAS){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;


        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM CLIENTES_MORA");
            Log.d("", "SaveClienteMora: A Guardar " + MORAS.size());
            for(int i=0;i<MORAS.size();i++){
                ClienteMora a = MORAS.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("VENCIDOS" , a.getmVencidos());
                contentValues.put("D30" , a.getmD30());
                contentValues.put("D60" , a.getmD60());
                contentValues.put("D90" , a.getmD90());
                contentValues.put("D120" , a.getmD120());
                contentValues.put("MD120" , a.getmMd120());
                contentValues.put("SALDO" , a.getmSaldo());
                contentValues.put("LIMITE" , a.getmLimite());
                myDataBase.insert("CLIENTES_MORA", null, contentValues );
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("", "SaveArticulos: Error " +e.getMessage());
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
    public static List<ClienteMora> getInfoMora(String basedir, Context context,String Cliente) {
        List<ClienteMora> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "CLIENTES_MORA", null, "CLIENTE"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    ClienteMora tmp = new ClienteMora();
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmVencidos(cursor.getString(cursor.getColumnIndex("VENCIDOS")));
                    tmp.setmD30(cursor.getString(cursor.getColumnIndex("D30")));
                    tmp.setmD60(cursor.getString(cursor.getColumnIndex("D60")));
                    tmp.setmD90(cursor.getString(cursor.getColumnIndex("D90")));
                    tmp.setmD120(cursor.getString(cursor.getColumnIndex("D120")));
                    tmp.setmMd120(cursor.getString(cursor.getColumnIndex("MD120")));
                    tmp.setmSaldo(cursor.getString(cursor.getColumnIndex("SALDO")));
                    tmp.setmLimite(cursor.getString(cursor.getColumnIndex("LIMITE")));
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
