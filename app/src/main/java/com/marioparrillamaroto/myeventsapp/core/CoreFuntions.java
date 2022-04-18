package com.marioparrillamaroto.myeventsapp.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.keychain.KeyChain;
import com.facebook.soloader.SoLoader;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.scottyab.rootbeer.RootBeer;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CoreFuntions {
    private final static String SiteKey = "x";
    private final static String SecretKey = "x";
    private final static String SECRET = "x";

    public static String createJWT(){
        Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Date exp = new Date(new Date().getTime() + 20000L);

        Map<String,Object> header = new HashMap();
        header.put("typ", "JWT");

        String jws = Jwts.builder()
                .setHeaderParams(header)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return jws;
    }

    public static String antiSQL(String line){
        String lineWithOut = line;
        lineWithOut = lineWithOut.replace("\"","").replace("'","");
        return lineWithOut;
    }

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

    public static boolean checkIsRooted(Context context){
        boolean rooted = true;
        RootBeer rootBeer = new RootBeer(context);
        if (rootBeer.isRooted()) rooted = true;
        else rooted = false;
        return rooted;
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

    public static void makeBackupChatCypto(String usuarioSender, String usuarioReceptor, ArrayList<MensajeChat> data, Context context){
        try {
            SoLoader.init(context, false);
            KeyChain keyChain = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
            Crypto crypto = AndroidConceal.get().createDefaultCrypto(keyChain);

            File root = new File(Environment.getDataDirectory()+"/data/com.marioparrillamaroto.myeventsapp/", "Chats");
            if (!root.exists()) {
                root.mkdirs();
            }
            String backupName = "backup_"+usuarioSender+"-"+usuarioReceptor.replace(":","_")+".txt";
            File backupChat = new File(root, backupName);

            OutputStream fileStream = null;
            fileStream = new BufferedOutputStream(new FileOutputStream(backupChat));
            OutputStream outputStream = crypto.getCipherOutputStream(fileStream, Entity.create("myEventsApp1*"));

            for (MensajeChat msg: data) {
                String line = "usuarioSender:"+msg.getUsuarioSender()+"/usuarioReceptor:"+msg.getUsuarioReceptor()+"/fecha:"+msg.getFechaMensaje()+"/msg:"+msg.getMensaje()+"\n";
                outputStream.write(line.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MensajeChat> readBackupChatCypto(String usuarioSender, String usuarioReceptor, Context context){
        ArrayList<MensajeChat> data2 = new ArrayList<>();
        try {
            SoLoader.init(context, false);
            KeyChain keyChain = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
            Crypto crypto = AndroidConceal.get().createDefaultCrypto(keyChain);

            File root = new File(Environment.getDataDirectory()+"/data/com.marioparrillamaroto.myeventsapp/", "Chats");
            String backupName = "backup_"+usuarioSender+"-"+usuarioReceptor.replace(":","_")+".txt";
            File backupChat = new File(root, backupName);

            FileInputStream fileStream = new FileInputStream(backupChat);
            InputStream inputStream = crypto.getCipherInputStream(fileStream, Entity.create("myEventsApp1*"));
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();

            while (linea!=null){
                System.out.println("@@@@@@@@ DATOS: "+linea);
                String msg = linea.substring(linea.indexOf("/msg")+5, linea.length());
                System.out.println(msg);

                String usuario1 = linea.substring(linea.indexOf("usuarioSender:")+14, linea.indexOf("/usuarioReceptor:"));
                System.out.println(usuario1);

                String usuario2 = linea.substring(linea.indexOf("/usuarioReceptor:")+17, linea.indexOf("/fecha:"));
                System.out.println(usuario2);

                String fecha = linea.substring(linea.indexOf("/fecha:")+7, linea.indexOf("/msg:"));
                System.out.println(fecha.replace(" : ","T")+":00");

                System.out.println(LocalDateTime.parse(fecha.replace(" : ","T")+":00").toString());

                data2.add(new MensajeChat(msg, usuario1
                        , usuario2, LocalDateTime.parse(fecha.replace(" : ","T")+":00")));
                linea= br.readLine();
            }
            br.close();
            isr.close();
            inputStream.close();

            return  data2;
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "No has hablado nunca con esta persona", Toast.LENGTH_SHORT).show();
            return data2;
        }
    }

    public static void makeBackupChat(String usuarioSender, String usuarioReceptor, ArrayList<MensajeChat> data, Context context){
        try{
            String backupName = "backup_"+usuarioSender+"-"+usuarioReceptor.replace(":","_")+".txt";
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(backupName, Context.MODE_PRIVATE));

            for (MensajeChat msg: data) {
                String line = "usuarioSender:"+msg.getUsuarioSender()+"/usuarioReceptor:"+msg.getUsuarioReceptor()+"/fecha:"+msg.getFechaMensaje()+"/msg:"+msg.getMensaje()+"\n";
                osw.write(line);
            }
            osw.flush();
            osw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<MensajeChat> readBackupChat(String usuarioSender, String usuarioReceptor, Context context){
        ArrayList<MensajeChat> data = new ArrayList<>();
        try{
            String backupName = "backup_"+usuarioSender+"-"+usuarioReceptor.replace(":","_")+".txt";

            InputStreamReader isr = new InputStreamReader( context.openFileInput(backupName));
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();

            while (linea!=null){
                System.out.println("@@@@@@@@ DATOS: "+linea);
                String msg = linea.substring(linea.indexOf("/msg")+5, linea.length());
                System.out.println(msg);

                String usuario1 = linea.substring(linea.indexOf("usuarioSender:")+14, linea.indexOf("/usuarioReceptor:"));
                System.out.println(usuario1);

                String usuario2 = linea.substring(linea.indexOf("/usuarioReceptor:")+17, linea.indexOf("/fecha:"));
                System.out.println(usuario2);

                String fecha = linea.substring(linea.indexOf("/fecha:")+7, linea.indexOf("/msg:"));
                System.out.println(fecha.replace(" : ","T")+":00");

                System.out.println(LocalDateTime.parse(fecha.replace(" : ","T")+":00").toString());

                data.add(new MensajeChat(msg, usuario1
                        , usuario2, LocalDateTime.parse(fecha.replace(" : ","T")+":00")));
                linea= br.readLine();
            }
            br.close();
            isr.close();

            return  data;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "No has hablado nunca con esta persona", Toast.LENGTH_SHORT).show();
            return  data;
        }
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
