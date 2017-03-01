package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marangelo
 */
public final class SQLiteHelper extends SQLiteOpenHelper
{
    
    public static final String DATABASE = "dbgmv.db";
    private static final int flag = SQLiteDatabase.NO_LOCALIZED_COLLATORS;//database version
    //private static final int flag = 102;//database version
    //private static final int flag = 1;
    private final String path;
    public SQLiteHelper(String path, Context context) throws IOException
    {
        super(context, path + DATABASE, null, flag);
        this.path = path;
        if (!checkDataBase())
        {
            InputStream myInput = context.getAssets().open(DATABASE);
            OutputStream myOutput = new FileOutputStream(new File(path, DATABASE));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) { myOutput.write(buffer, 0, length); }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB;
        try
        {
            checkDB = SQLiteDatabase.openDatabase(path.concat(DATABASE), null, flag);
        }
        catch (Exception e) { checkDB = null; }
        boolean ok = checkDB != null;
        if (ok) { checkDB.close(); }
        return ok;
    }
    public static void ExecuteSQL(String basedir, Context context, String SQL){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getWritableDatabase();
            myDataBase.execSQL(SQL);

        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
    public static void  SaveArticulos(Context context, ArrayList<Articulo> ARTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;


        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM ARTICULOS");
            Log.d("", "SaveArticulos: A Guardar " + ARTI.size());
            for(int i=0;i<ARTI.size();i++){
                Articulo a = ARTI.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("ARTICULO" , a.getmCodigo());
                contentValues.put("DESCRIPCION" , a.getmName());
                contentValues.put("EXISTENCIA" , a.getmExistencia());
                contentValues.put("UNIDAD" , a.getmUnidad());
                contentValues.put("PRECIO" , a.getmPrecio());
                contentValues.put("PUNTOS" , a.getmPuntos());
                contentValues.put("REGLAS" , a.getmReglas());
                myDataBase.insert("ARTICULOS", null, contentValues );
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
    public static List<Articulo> getArticulos(String basedir, Context context) {
        List<Articulo> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        Log.d("", "SaveArticulos: A leer " );
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "ARTICULOS", null, null, null, null, null, null, null);
            Log.d("", "SaveArticulos: Guardados " + cursor.getCount());
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Articulo tmp = new Articulo();
                    tmp.setmCodigo(cursor.getString(cursor.getColumnIndex("ARTICULO")));
                    tmp.setmName(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    tmp.setmExistencia(cursor.getString(cursor.getColumnIndex("EXISTENCIA")));
                    tmp.setmUnidad(cursor.getString(cursor.getColumnIndex("UNIDAD")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("PRECIO")));
                    tmp.setmPuntos(cursor.getString(cursor.getColumnIndex("PUNTOS")));
                    tmp.setmReglas(cursor.getString(cursor.getColumnIndex("REGLAS")));
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
    @Override
    public void onCreate(SQLiteDatabase db) { }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

