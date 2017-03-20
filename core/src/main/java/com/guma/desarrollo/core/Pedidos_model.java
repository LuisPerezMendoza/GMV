package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        ArrayList<HashMap<String, String>> contactList;
        Integer i = 0;
        contactList = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
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

                    HashMap<String, String> contact = new HashMap<>();
                    while(!cursor2.isAfterLast()) {
                        /*contact.put("IDPEDIDO", cursor2.getString(cursor2.getColumnIndex("IDPEDIDO")));
                        contact.put("ARTICULO", cursor2.getString(cursor2.getColumnIndex("ARTICULO")));
                        contact.put("DESCRIPCION", cursor2.getString(cursor2.getColumnIndex("DESCRIPCION")));
                        contact.put("CANTIDAD", cursor2.getString(cursor2.getColumnIndex("CANTIDAD")));
                        contact.put("TOTAL", cursor2.getString(cursor2.getColumnIndex("TOTAL")));
                        contact.put("BONIFICADO", cursor2.getString(cursor2.getColumnIndex("BONIFICADO")));
                        contactList.add(contact);
                        tmp.setContactList(contactList);
                        lista.add(tmp);
                        cursor2.moveToNext();*/

                        tmp.getDetalles().put("ID"+i,cursor2.getString(cursor2.getColumnIndex("IDPEDIDO")));
                        tmp.getDetalles().put("ARTICULO"+i,cursor2.getString(cursor2.getColumnIndex("ARTICULO")));
                        tmp.getDetalles().put("DESC"+i,cursor2.getString(cursor2.getColumnIndex("DESCRIPCION")));
                        tmp.getDetalles().put("CANT"+i,cursor2.getString(cursor2.getColumnIndex("CANTIDAD")));
                        tmp.getDetalles().put("TOTAL"+i,cursor2.getString(cursor2.getColumnIndex("TOTAL")));
                        tmp.getDetalles().put("BONI"+i,cursor2.getString(cursor2.getColumnIndex("BONIFICADO")));
                        i++;

                        cursor2.moveToNext();
                    }
                    //lista.add(tmp);
                    lista.add(tmp);
                    contact.clear();
                    contactList.clear();

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
