package com.marioparrillamaroto.myeventsapp.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HomeModel {

    private static final String LOGIN_TABLE = "'LoginInfo'";

    public ArrayList<Evento> eventsOfUser(Context context, String username){
        ArrayList<Evento> events = new ArrayList<>();

        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase();
        Cursor mCursor = db.rawQuery("select evento.* from evento, usuario where usuario.username = ? and usuario.userid=evento.user_summoner_userid", new String[]{username});

        while(mCursor.moveToNext()){
            events.add(new Evento(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), LocalDateTime.parse(mCursor.getString(3)),
                    LocalDateTime.parse(mCursor.getString(4)), FunctionsDatabase.getValue(mCursor.getInt(6)), FunctionsDatabase.getValue(mCursor.getInt(5)),
                    mCursor.getInt(9), mCursor.getInt(10), mCursor.getString(7), mCursor.getString(8)));
        }

        return events;
    }

    public String getLoginUser(Context context){
        String username = "";
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase();
        Cursor mCursor = db.rawQuery("select username from "+LOGIN_TABLE, null);

        while(mCursor.moveToNext()){
            username = mCursor.getString(0);
        }

        return username;
    }

    public String getUsername(Context context, int userid){
        String username = "";
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase();
        Cursor mCursor = db.rawQuery("select username from usuario where userid = ?", new String[] {String.valueOf(userid)});

        while(mCursor.moveToNext()){
            username = mCursor.getString(0);
        }

        if (username.equals("")) username="null";

        return username;
    }

}