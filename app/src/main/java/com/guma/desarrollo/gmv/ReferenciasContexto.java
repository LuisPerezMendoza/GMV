package com.guma.desarrollo.gmv;

import android.content.Context;

/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public class ReferenciasContexto {
    private static Context ContextArticulo;

    public static Context getContextArticulo() {
        return ContextArticulo;
    }

    public static void setContextArticulo(Context contextArticulo) {
        ContextArticulo = contextArticulo;
    }
}
