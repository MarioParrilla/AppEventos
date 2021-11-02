package com.marioparrillamaroto.myeventsapp.ui.ExternalProfile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ExternalProfileModel {

    private Context context;

    public ExternalProfileModel(Context context){
        this.context=context;
    }

    public ArrayList<Evento> getEventsOfUser(Long userID){
        ArrayList<Evento> list = new ArrayList<>();
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from evento where user_owner_userid = ? and available = 1", new String[]{userID.toString()});

        while(mCursor.moveToNext()){
            list.add(new Evento(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), LocalDateTime.parse(mCursor.getString(3)),
                    LocalDateTime.parse(mCursor.getString(4)), FunctionsDatabase.getValue(mCursor.getInt(6)), FunctionsDatabase.getValue(mCursor.getInt(5)),
                    mCursor.getInt(9), mCursor.getInt(10), mCursor.getString(7), mCursor.getString(8)));
        }

        return  list;
    }

    public String getUsername(int userid){
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
