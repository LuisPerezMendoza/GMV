package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by alder.hernandez on 13/03/2017.
 */

public class Pedidos_model {
    public static void  SavePedido(Context context, ArrayList<Pedidos> ARTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<ARTI.size();i++){

                Pedidos a = ARTI.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("VENDEDOR" , a.getmVendedor());
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("FECHA" , a.getmFecha());

                myDataBase.insert("PEDIDO", null, contentValues );
            }
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
    public static void  SaveDetallePedido(Context context, ArrayList<Pedidos> ARTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<ARTI.size();i++){

                Pedidos a = ARTI.get(i);

                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("ARTICULO" , a.getmArticulo());
                contentValues.put("DESCRIPCION" , a.getmDescripcion());
                contentValues.put("CANTIDAD" , a.getmCantidad());
                contentValues.put("TOTAL" , a.getmPrecio());
                contentValues.put("BONIFICADO" , a.getmBonificado());

                myDataBase.insert("PEDIDO_DETALLE", null, contentValues );
            }
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
