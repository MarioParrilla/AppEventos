package com.marioparrillamaroto.myeventsapp.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

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

    private static final String URLAPI = "http://192.168.1.62:8080/api/";


    private Context contextRoot;

    public FunctionsDatabase(@Nullable Context context) {
        super(context, "MyEventsApp.db", null, 1);
        this.contextRoot=context;
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

        try{
            RequestQueue requestQueue = Volley.newRequestQueue(contextRoot.getApplicationContext());

            JsonArrayRequest jAR = new JsonArrayRequest(Request.Method.GET,URLAPI+"/usuario",null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Usuario user;
                    SQLiteDatabase db = getWritableDatabase();
                    try {
                        db.execSQL("DELETE FROM "+USUARIO_TABLE);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = (JSONObject) response.get(i);

                            user = new Usuario(object.getLong("userID"),object.getString("username"),object.getString("email"),object.getString("password"),
                                    object.getString("phonenumber"),object.getBoolean("cmsAdmin"),object.getBoolean("enabled"));

                            insertUserDatabase(user);
                        }

                    } catch (JSONException e) {
                        Toast.makeText(contextRoot.getApplicationContext(), "Error Usuarios: ¡No se puedo sincronizar los datos con el servidor!",Toast.LENGTH_LONG).show();
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
            });

            requestQueue.add(jAR);
            jAR = new JsonArrayRequest(Request.Method.GET,URLAPI+"/evento",null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Evento event;
                    SQLiteDatabase db = getWritableDatabase();
                    try {
                        db.execSQL("DELETE FROM "+EVENTO_TABLE);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = (JSONObject) response.get(i);

                            event = new Evento(object.getInt("eventID"), object.getString("eventName"), object.getString("tema"), LocalDateTime.parse(object.getString("startTime")), LocalDateTime.parse(object.getString("endTime")),
                                    object.getBoolean("eventPreference"), object.getBoolean("available"), object.getJSONObject("userOwner").getInt("userID"), object.getJSONObject("userSummoner").getInt("userID"), object.getString("coordinates"), object.getString("videomeeting"));

                            insertEventDatabase(event);
                        }

                    } catch (JSONException e) {
                        Toast.makeText(contextRoot.getApplicationContext(), "Error Eventos: ¡No se puedo sincronizar los datos con el servidor!",Toast.LENGTH_LONG).show();
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
            });

            requestQueue.add(jAR);
        }catch (Exception e){
            Toast.makeText(contextRoot.getApplicationContext(), "Error General: ¡No se puedo sincronizar los datos con el servidor!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public  boolean insertUserDatabase(Usuario user){

        boolean ended = false;
        SQLiteDatabase db = this.getWritableDatabase();
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
        SQLiteDatabase db = this.getWritableDatabase();
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

    public boolean checkIsLogin(){
        boolean isLogin = false;

        SQLiteDatabase db = new FunctionsDatabase(contextRoot.getApplicationContext()).getReadableDatabase();
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

        SQLiteDatabase db = new FunctionsDatabase(contextRoot.getApplicationContext()).getReadableDatabase();
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
            SQLiteDatabase db = new FunctionsDatabase(contextRoot.getApplicationContext()).getWritableDatabase();
            db.execSQL("DELETE FROM "+LOGIN_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkCloseSession(){
        try{
            boolean cerrarSesion = checkSession();
            if (cerrarSesion){
                closeSession();
            }
        }catch (Exception  e){
            e.printStackTrace();
        }
    }
}
