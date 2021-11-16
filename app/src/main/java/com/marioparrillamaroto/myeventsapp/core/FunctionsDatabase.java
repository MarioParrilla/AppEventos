package com.marioparrillamaroto.myeventsapp.core;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marioparrillamaroto.myeventsapp.Evento;

import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class FunctionsDatabase extends SQLiteOpenHelper {

    private static final String USUARIO_TABLE = "'usuario'";
    private static final String COLUMN_USERID = "'userid'";
    private static final String COLUMN_USERNAME = "'username'";
    private static final String COLUMN_EMAIL = "'email'";
    private static final String COLUMN_PASSWORD = "'password'";
    private static final String COLUMN_PHONENUMBER = "'phonenumber'";
    private static final String COLUMN_CMS_ADMIN = "'cms_admin'";
    private static final String COLUMN_ENABLED = "'enabled'";

    private static final String EVENTO_TABLE = "'evento'";
    private static final String COLUMN_EVENTID = "'eventid'";
    private static final String COLUMN_EVENT_NAME = "'event_name'";
    private static final String COLUMN_TEMA = "'tema'";
    private static final String COLUMN_START_TIME = "'start_time'";
    private static final String COLUMN_END_TIME = "'end_time'";
    private static final String COLUMN_AVAILABLE = "'available'";
    private static final String COLUMN_EVENT_PREFERENCE = "'event_preference'";
    private static final String COLUMN_COORDINATES = "'coordinates'";
    private static final String COLUMN_VIDEOMEETING = "'videomeeting'";
    private static final String COLUMN_USER_OWNER_USERID = "'user_owner_userid'";
    private static final String COLUMN_USER_SUMMONER_USERID = "'user_summoner_userid'";
    private static final String LOGIN_TABLE = "'LoginInfo'";
    private static final String COLUMN_SAVESESSION = "'saveSession'";
    private SQLiteDatabase db;
    private static final String URLAPI = "http://192.168.1.62:8080/api";
    private Context contextRoot;

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }



    public FunctionsDatabase(@Nullable Context context) {
        super(context, "MyEventsApp.db", null, 1);
        this.contextRoot=context;
        this.db = getWritableDatabase();

        //SQLiteDatabase.loadLibs(context.getApplicationContext());
        //this.db = getWritableDatabase("admin*");
        //db.rawExecSQL("PRAGMA cipher_memory_security = OFF");
    }

    //Se llama la primera vez que se accede a la base de datos, aqui crearemos tablas...
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "";
        createTableStatement = "CREATE TABLE " + USUARIO_TABLE + "(" +
                COLUMN_USERID + "	INTEGER NOT NULL," +
                COLUMN_USERNAME + "	TEXT NOT NULL," +
                COLUMN_EMAIL + "	TEXT NOT NULL," +
                COLUMN_PASSWORD + "	TEXT NOT NULL," +
                COLUMN_PHONENUMBER + "	INTEGER NOT NULL," +
                COLUMN_CMS_ADMIN + "	BOOL NOT NULL," +
                COLUMN_ENABLED + "	BOOL NOT NULL," +
                "PRIMARY KEY(" + COLUMN_USERID + "))";

        db.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE " + EVENTO_TABLE + "(" +
                COLUMN_EVENTID + "	INTEGER NOT NULL," +
                COLUMN_EVENT_NAME + "	TEXT NOT NULL," +
                COLUMN_TEMA + "	TEXT NOT NULL," +
                COLUMN_START_TIME + "	TEXT NOT NULL," +
                COLUMN_END_TIME + "	INTEGER NOT NULL," +
                COLUMN_AVAILABLE + "	BOOL NOT NULL," +
                COLUMN_EVENT_PREFERENCE + "	BOOL NOT NULL," +
                COLUMN_COORDINATES + "	TEXT," +
                COLUMN_VIDEOMEETING + "	TEXT," +
                COLUMN_USER_OWNER_USERID + "	INTEGER NOT NULL," +
                COLUMN_USER_SUMMONER_USERID + "	INTEGER," +
                "PRIMARY KEY(" + COLUMN_EVENTID + "),"+
                "FOREIGN KEY (" + COLUMN_USER_OWNER_USERID + ") REFERENCES "+ USUARIO_TABLE +" ("+COLUMN_USERID+")," +
                "FOREIGN KEY (" + COLUMN_USER_SUMMONER_USERID + ") REFERENCES "+ USUARIO_TABLE +" ("+COLUMN_USERID+"))";

        db.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE " + LOGIN_TABLE + "(" +
                COLUMN_USERID + "	INTEGER NOT NULL," +
                COLUMN_USERNAME + "	TEXT NOT NULL," +
                COLUMN_PASSWORD + "	TEXT NOT NULL," +
                COLUMN_SAVESESSION+" BOOL NOT NULL)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+EVENTO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+USUARIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+LOGIN_TABLE);
        onCreate(db);
    }

    public static Boolean getValue(int x){
        boolean bool = false;
        if (x==1) bool = true;
        return bool;
    }

    public void syncronizingData(){

        if (CoreFuntions.checkConnetionToInternet(contextRoot)){
            try{
                RequestQueue requestQueue = Volley.newRequestQueue(contextRoot.getApplicationContext());
                JsonArrayRequest jAR = new JsonArrayRequest(Request.Method.GET,URLAPI+"/usuario",null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Usuario user;
                        try {
                            db.execSQL("DELETE FROM "+USUARIO_TABLE);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = (JSONObject) response.get(i);

                                user = new Usuario(object.getLong("userID"),object.getString("username"),object.getString("email"),object.getString("password"),
                                        object.getString("phonenumber"),object.getBoolean("cmsAdmin"),object.getBoolean("enabled"));

                                insertUserDatabase(user);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contextRoot.getApplicationContext(), "Error Usuarios: ¡No se puedo sincronizar los datos con el servidor !",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Authorization", "Bearer "+ CoreFuntions.createJWT());
                        return params;
                    }
                };

                requestQueue.add(jAR);
                jAR = new JsonArrayRequest(Request.Method.GET,URLAPI+"/evento",null, new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        Evento event;
                        try {
                            db.execSQL("DELETE FROM "+EVENTO_TABLE);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = (JSONObject) response.get(i);

                                if (object.isNull("userSummoner")){
                                    event = new Evento(object.getInt("eventID"), object.getString("eventName"), object.getString("tema"), LocalDateTime.parse(object.getString("startTime")), LocalDateTime.parse(object.getString("endTime")),
                                            object.getBoolean("eventPreference"), object.getBoolean("available"), object.getJSONObject("userOwner").getInt("userID"),null, object.getString("coordinates"), object.getString("videomeeting"));
                                }else{
                                    event = new Evento(object.getInt("eventID"), object.getString("eventName"), object.getString("tema"), LocalDateTime.parse(object.getString("startTime")), LocalDateTime.parse(object.getString("endTime")),
                                            object.getBoolean("eventPreference"), object.getBoolean("available"), object.getJSONObject("userOwner").getInt("userID"), object.getJSONObject("userSummoner").getInt("userID"), object.getString("coordinates"), object.getString("videomeeting"));
                                }

                                insertEventDatabase(event);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contextRoot.getApplicationContext(), "Error Eventos: ¡No se puedo sincronizar los datos con el servidor!",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Authorization", "Bearer "+ CoreFuntions.createJWT());
                        return params;
                    }
                };

                requestQueue.add(jAR);
            }catch (Exception e){
                Toast.makeText(contextRoot.getApplicationContext(), "Error General: ¡No se puedo sincronizar los datos con el servidor!",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }


    }

    public  boolean insertUserDatabase(Usuario user){

        boolean ended = false;
        ContentValues cv = new ContentValues();
        long isInserted = 0L;

        try {

            cv.put(COLUMN_USERID,user.getUserID());
            cv.put(COLUMN_USERNAME,user.getUsername());
            cv.put(COLUMN_EMAIL,user.getEmail());
            cv.put(COLUMN_PASSWORD,user.getPassword());
            cv.put(COLUMN_PHONENUMBER,user.getPhonenumber());
            cv.put(COLUMN_CMS_ADMIN,user.getCmsAdmin());
            cv.put(COLUMN_ENABLED,user.getEnabled());

            isInserted = db.insert(USUARIO_TABLE, null, cv);

            if (isInserted==-1L) throw new Exception("¡No se puedo hacer la inserción de datos!");

            ended = true;

        }catch (Exception e){
            ended = false;
            System.err.println("Error al sincronizar usuarios: "+e.getMessage());
        }
        return ended;
    }

    public  boolean insertEventDatabase(Evento event){

        boolean ended = false;
        ContentValues cv = new ContentValues();
        long isInserted = 0L;

        try {
            cv.put(COLUMN_EVENTID,event.getEventID());
            cv.put(COLUMN_EVENT_NAME,event.getNombreEvento());
            cv.put(COLUMN_TEMA,event.getTema());
            cv.put(COLUMN_START_TIME,event.getHoraInicio());
            cv.put(COLUMN_END_TIME,event.getHoraFinal());
            cv.put(COLUMN_AVAILABLE,event.getAvailable());
            cv.put(COLUMN_EVENT_PREFERENCE,event.getEventPreference());
            cv.put(COLUMN_COORDINATES,event.getCoordenadas());
            cv.put(COLUMN_VIDEOMEETING,event.getEnlaceVideoMeeting());
            cv.put(COLUMN_USER_OWNER_USERID,event.getUserOwnerID());
            cv.put(COLUMN_USER_SUMMONER_USERID,event.getUserSummonerID());

            isInserted = db.insert(EVENTO_TABLE, null, cv);

            if (isInserted==-1L) throw new Exception("¡No se puedo hacer la inserción de datos!");

            ended = true;

        }catch (Exception e){
            ended = false;
            System.err.println("Error al sincronizar eventos: "+e.getMessage());
        }
        return ended;
    }

    private Usuario getUserByID(Long userid){
        Usuario user = null;
        Cursor mCursor = db.rawQuery("select * from Usuario where userid = ?", new String[]{userid.toString()});

        while(mCursor.moveToNext()){
            user = new Usuario(mCursor.getLong(0),mCursor.getString(1),mCursor.getString(2),mCursor.getString(3),mCursor.getString(4),FunctionsDatabase.getValue(mCursor.getInt(5)), FunctionsDatabase.getValue(mCursor.getInt(6)));
        }

        return user;
    }

    public void createEvent(Evento event){
        JSONObject postData = new JSONObject();
        JSONObject userOJSON = new JSONObject();

        try {

            Usuario userO = getUserByID(Long.valueOf(event.getUserOwnerID()));

            userOJSON.put("userID", userO.getUserID());

            postData.put("eventName", event.getNombreEvento());
            postData.put("tema", event.getTema());
            postData.put("startTime", event.getHoraInicio());
            postData.put("endTime", event.getHoraFinal());
            postData.put("available", event.getAvailable());
            postData.put("eventPreference", event.getEventPreference());
            postData.put("coordinates", event.getCoordenadas());
            postData.put("videomeeting", event.getEnlaceVideoMeeting());
            postData.put("userOwner", userOJSON);
            postData.put("userSummoner", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLAPI+"/evento", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(contextRoot.getApplicationContext(), "Evento Agregado",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextRoot.getApplicationContext(), "No se pudo contactar con el servidor",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+ CoreFuntions.createJWT());
                return params;
            }
        };
        Volley.newRequestQueue(contextRoot.getApplicationContext()).add(jsonObjectRequest);
    }

    public void modifyEvent(Evento event){
        JSONObject postData = new JSONObject();
        JSONObject userOJSON = new JSONObject();

        try {

            Usuario userO = getUserByID(Long.valueOf(event.getUserOwnerID()));

            userOJSON.put("userID", userO.getUserID());

            postData.put("eventID", event.getEventID());
            postData.put("eventName", event.getNombreEvento());
            postData.put("tema", event.getTema());
            postData.put("startTime", event.getHoraInicio());
            postData.put("endTime", event.getHoraFinal());
            postData.put("available", event.getAvailable());
            postData.put("eventPreference", event.getEventPreference());
            postData.put("coordinates", event.getCoordenadas());
            postData.put("videomeeting", event.getEnlaceVideoMeeting());
            postData.put("userOwner", userOJSON);
            postData.put("userSummoner", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URLAPI+"/evento/"+event.getEventID(), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextRoot.getApplicationContext(), "No se pudo contactar con el servidor",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+ CoreFuntions.createJWT());
                return params;
            }
        };
        Volley.newRequestQueue(contextRoot.getApplicationContext()).add(jsonObjectRequest);
    }

    public void deleteEvent(Long eventID){
        StringRequest jsontRequest = new StringRequest(Request.Method.DELETE, URLAPI + "/evento/" + eventID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(contextRoot.getApplicationContext(), "Evento Eliminado",Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextRoot.getApplicationContext(), "No se pudo contactar con el servidor",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+ CoreFuntions.createJWT());
                return params;
            }
        };
        Volley.newRequestQueue(contextRoot.getApplicationContext()).add(jsontRequest);
    }

    public void citeEvent(Evento event){
        JSONObject postData = new JSONObject();
        JSONObject userOJSON = new JSONObject();
        JSONObject userSJSON = new JSONObject();

        try {

            Usuario userO = getUserByID(Long.valueOf(event.getUserOwnerID()));
            Usuario userS = getUserByID(Long.valueOf(event.getUserSummonerID()));

            userOJSON.put("userID", userO.getUserID());
            userSJSON.put("userID", userS.getUserID());

            postData.put("eventID", event.getEventID());
            postData.put("eventName", event.getNombreEvento());
            postData.put("tema", event.getTema());
            postData.put("startTime", event.getHoraInicio());
            postData.put("endTime", event.getHoraFinal());
            postData.put("available", event.getAvailable());
            postData.put("eventPreference", event.getEventPreference());
            postData.put("coordinates", event.getCoordenadas());
            postData.put("videomeeting", event.getEnlaceVideoMeeting());
            postData.put("userOwner", userOJSON);
            postData.put("userSummoner", userSJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URLAPI+"/evento/"+event.getEventID(), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(contextRoot.getApplicationContext(), "Evento Citado",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextRoot.getApplicationContext(), "No se pudo contactar con el servidor",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+ CoreFuntions.createJWT());
                return params;
            }
        };
        Volley.newRequestQueue(contextRoot.getApplicationContext()).add(jsonObjectRequest);
    }

    public Long getIDLoginUser(){
        Long userid = 0L;
        Cursor mCursor = db.rawQuery("select userid from "+LOGIN_TABLE, null);

        while(mCursor.moveToNext()){
            userid = mCursor.getLong(0);
        }

        return userid;
    }

    public String getUsernameLoginUser(){
        String username = "null";
        Cursor mCursor = db.rawQuery("select username from "+LOGIN_TABLE, null);

        while(mCursor.moveToNext()){
            username = mCursor.getString(0);
        }

        return username;
    }

    public void checkUserLoginExists(){
        Cursor mCursor = db.rawQuery("select count(*) from usuario where userid = ?", new String[]{getIDLoginUser().toString()});

        int exists = 0;
        while(mCursor.moveToNext()){
            exists = mCursor.getInt(0);
        }

        if (exists==0){
            closeSession();
            Toast.makeText(contextRoot.getApplicationContext(), "¡El usuario dejó de existir en el servidor!",Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkIsLogin(){
        boolean isLogin = false;
        Cursor mCursor = db.rawQuery("select count(*) from "+LOGIN_TABLE, null);

        int exists = 0;
        while(mCursor.moveToNext()){
            exists = mCursor.getInt(0);
        }

        if (exists==1) isLogin = true;

        return isLogin;
    }

    public boolean checkSession(){
        boolean closeSession = false;
        Cursor mCursor = db.rawQuery("select saveSession from "+LOGIN_TABLE, null);

        int saveSession = 0;
        while(mCursor.moveToNext()){
            saveSession = mCursor.getInt(0);
        }
        if (saveSession!=1) closeSession = true;

        return closeSession;
    }

    public void closeSession(){
        try{
            db.execSQL("DELETE FROM "+LOGIN_TABLE);
            Intent nuevaPantalla = new Intent(contextRoot.getApplicationContext(), LoginActivity.class);
            nuevaPantalla.addFlags(FLAG_ACTIVITY_NEW_TASK);
            contextRoot.getApplicationContext().startActivity(nuevaPantalla);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkCloseSession(){
        try{
            if (checkSession()){
                closeSession();
            }
        }catch (Exception  e){
            e.printStackTrace();
        }
    }
}
