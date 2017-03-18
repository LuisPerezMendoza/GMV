package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alder.hernandez on 13/03/2017.
 */

public class Pedidos_model {
    public static void  SavePedido(Context context, ArrayList<Pedidos> PEDIDO){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            for(int i=0;i<PEDIDO.size();i++){
                Pedidos a = PEDIDO.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("VENDEDOR" , a.getmVendedor());
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("FECHA" , a.getmFecha());
                contentValues.put("MONTO" , a.getmPrecio());
                contentValues.put("ESTADO" , a.getmEstado());

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
    public static void  SaveDetallePedido(Context context, ArrayList<Pedidos> DETPEDIDO){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<DETPEDIDO.size();i++){

                Pedidos a = DETPEDIDO.get(i);

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
    public static List<Pedidos> getInfoPedidos(String basedir, Context context) {
        List<Pedidos> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            Integer i =0;
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "PEDIDO", null, null, null, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {

                    Pedidos tmp = new Pedidos();
                    tmp.setmIdPedido(cursor.getString(cursor.getColumnIndex("IDPEDIDO")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("VENDEDOR")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("MONTO")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
                    tmp.setmEstado(cursor.getString(cursor.getColumnIndex("ESTADO")));
                    Cursor cursor2 = myDataBase.query(true, "PEDIDO_DETALLE", null, "IDPEDIDO"+ "=?", new String[] { cursor.getString(cursor.getColumnIndex("IDPEDIDO")) }, null, null, null, null);
                    cursor2.moveToFirst();
                    while(!cursor2.isAfterLast()) {//DETALLES DE PEDIDO
                        tmp.getdetalles().put("ID"+i,cursor2.getString(cursor2.getColumnIndex("IDPEDIDO")));
                        tmp.getdetalles().put("ARTICULO"+i,cursor2.getString(cursor2.getColumnIndex("ARTICULO")));
                        tmp.getdetalles().put("DESC"+i,cursor2.getString(cursor2.getColumnIndex("DESCRIPCION")));
                        tmp.getdetalles().put("CANT"+i,cursor2.getString(cursor2.getColumnIndex("CANTIDAD")));
                        tmp.getdetalles().put("TOTAL"+i,cursor2.getString(cursor2.getColumnIndex("TOTAL")));
                        tmp.getdetalles().put("BONI"+i,cursor2.getString(cursor2.getColumnIndex("BONIFICADO")));
                        cursor2.moveToNext();
                        i++;
                    }
                    i=0;
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
