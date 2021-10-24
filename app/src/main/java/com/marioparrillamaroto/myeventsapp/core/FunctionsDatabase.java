package com.marioparrillamaroto.myeventsapp.core;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;

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

    public FunctionsDatabase(@Nullable Context context) {
        super(context, "MyEventsApp.db", null, 1);
    }

    //Se llama la primera vez que se accede a la base de datos, aqui crearemos tablas...
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUserStatement = "";
        String createTableEventStatement = "";

        createTableUserStatement = "CREATE TABLE " + USUARIO_TABLE + "(" +
                COLUMN_USERID + "	INTEGER NOT NULL," +
                COLUMN_USERNAME + "	TEXT NOT NULL," +
                COLUMN_EMAIL + "	TEXT NOT NULL," +
                COLUMN_PASSWORD + "	TEXT NOT NULL," +
                COLUMN_PHONENUMBER + "	INTEGER NOT NULL," +
                COLUMN_CMS_ADMIN + "	BOOL NOT NULL," +
                COLUMN_ENABLED + "	BOOL NOT NULL," +
                "PRIMARY KEY(" + COLUMN_USERID + "))";

        db.execSQL(createTableUserStatement);

        createTableEventStatement = "CREATE TABLE " + EVENTO_TABLE + "(" +
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

        db.execSQL(createTableEventStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+EVENTO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+USUARIO_TABLE);
        onCreate(db);
    }

    public void syncronizingData(Context context, String urlAPI){

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        JsonArrayRequest jAR = new JsonArrayRequest(Request.Method.GET,urlAPI+"/usuario",null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Usuario user;
                try {
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
                error.printStackTrace();
            }
        });

        requestQueue.add(jAR);

        jAR = new JsonArrayRequest(Request.Method.GET,urlAPI+"/evento",null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Evento event;
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = (JSONObject) response.get(i);

                        event = new Evento(object.getInt("eventID"), object.getString("eventName"), object.getString("tema"), LocalDateTime.parse(object.getString("startTime")), LocalDateTime.parse(object.getString("endTime")),
                                object.getBoolean("eventPreference"), object.getBoolean("available"), object.getJSONObject("userOwner").getInt("userID"), object.getJSONObject("userSummoner").getInt("userID"), object.getString("coordinates"), object.getString("videomeeting"));

                        insertEventDatabase(event);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jAR);
    }

    public  boolean insertUserDatabase(Usuario user){

        boolean ended = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long isInserted = 0L;

        try {

            Cursor mCursor = db.rawQuery("select count(*) from usuario where userID = ?", new String[]{user.getUserID().toString()});

            int exists = 0;
            while(mCursor.moveToNext()){
                exists = mCursor.getInt(0);
            }
            if (exists!=1){
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
            }

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

            Cursor mCursor = db.rawQuery("select count(*) from evento where eventID = ?", new String[]{event.getEventID().toString()});

            int exists = 0;
            while(mCursor.moveToNext()){
                exists = mCursor.getInt(0);
            }
            if (exists!=1){
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
            }

        }catch (Exception e){
            ended = false;
            System.err.println("Error al sincronizar eventos: "+e.getMessage());
        }
        return ended;
    }
}
