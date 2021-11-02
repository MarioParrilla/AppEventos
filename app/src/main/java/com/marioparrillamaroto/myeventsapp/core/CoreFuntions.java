package com.marioparrillamaroto.myeventsapp.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CoreFuntions {
    public static boolean checkConnetionToInternet(Context context){
        boolean haveInternet = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            haveInternet = true;
            if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE) Toast.makeText(context.getApplicationContext(),"¡Estas conectado a los datos moviles!", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context.getApplicationContext(),"¡No tienes conexión a internet!", Toast.LENGTH_SHORT).show();
        return haveInternet;
    }
}
