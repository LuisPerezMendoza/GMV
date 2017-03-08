package com.guma.desarrollo.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.File;

/**
 * Created by maryan.espinoza on 29/12/2016.
 */

public class ManagerURI {
    private static Context myContext;
    //private static String SERVER = "192.168.1.211";
    private static String SERVER = "10.0.3.2:8080";
    private static String URL_Base= "http://"+ SERVER + "/gmv_rest/index.php/";
    private static String  DIR_DB = "/data/data/com.guma.desarrollo.gmv/";
  //  private static String  DIR_DB = myContext.getFilesDir().getPath();
    public static String getURL_Base() {
        return URL_Base;
    }

    public static String getDirDb() {
        return DIR_DB;
    }


    public static boolean isOnlinea(Context cxt){
        ConnectivityManager cm = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;

        }
    }

}
