package com.marioparrillamaroto.myeventsapp.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.scottyab.rootbeer.RootBeer;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoreFuntions {
    private static String SiteKey = "6LfdeBMdAAAAAPk5TRWlW-ySQn1qaprU187xsq1r";
    private static String SecretKey = "6LfdeBMdAAAAAAEnLQKF4X9fOjBuFcoTB7Q_Vkcu";
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

    public static void checkIsRooted(Context context){
        RootBeer rootBeer = new RootBeer(context);
        if (rootBeer.isRooted()) Toast.makeText(context.getApplicationContext(),"¡Tienes el movil rooteado!", Toast.LENGTH_SHORT).show();

    }

    public static void checkGatcha(Context context, CheckBox cbx){
        cbx.setChecked(false);
        SafetyNet.getClient(context.getApplicationContext()).verifyWithRecaptcha(SiteKey)
                .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(@NonNull SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                        String userResponseToken = recaptchaTokenResponse.getTokenResult();
                        if (!userResponseToken.isEmpty()){
                            handleCaptchaResult(context,userResponseToken, cbx);
                        }else {
                            Toast.makeText(context.getApplicationContext(), "Catcha MAL", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context.getApplicationContext(), "Error al realizar el Captcha", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void handleCaptchaResult(Context context, final String responseToken, CheckBox cbx) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")){
                                cbx.setChecked(true);
                            }else{
                                cbx.setChecked(false);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            cbx.setChecked(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), "Error al realizar el Captcha", Toast.LENGTH_SHORT).show();
                        cbx.setChecked(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SecretKey);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context.getApplicationContext()).add(request);
    }
}
