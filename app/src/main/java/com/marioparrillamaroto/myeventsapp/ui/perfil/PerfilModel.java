package com.marioparrillamaroto.myeventsapp.ui.perfil;

import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PerfilModel{

    private Context context;

    public PerfilModel(Context context){
        this.context=context;
    }

    private static final String LOGIN_TABLE = "'LoginInfo'";

    public Usuario getLoginUser(){
        Usuario user = null;
        Long userid = 0L;
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase("admin*");
        Cursor mCursor = db.rawQuery("select userid from "+LOGIN_TABLE, null);

        while(mCursor.moveToNext()){
            userid = mCursor.getLong(0);
        }

        mCursor = db.rawQuery("select * from Usuario where userid = ?", new String[]{userid.toString()});

        while(mCursor.moveToNext()){
            user = new Usuario(mCursor.getLong(0),mCursor.getString(1),mCursor.getString(2),mCursor.getString(3),mCursor.getString(4),FunctionsDatabase.getValue(mCursor.getInt(5)), FunctionsDatabase.getValue(mCursor.getInt(6)));
        }

        return user;
    }

    public ArrayList<Evento> getEventsOfUser(Long userID){
        ArrayList<Evento> list = new ArrayList<>();
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase("admin*");
        Cursor mCursor = db.rawQuery("select * from evento where user_owner_userid = ?", new String[]{userID.toString()});

        while(mCursor.moveToNext()){
            list.add(new Evento(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), LocalDateTime.parse(mCursor.getString(3)),
                    LocalDateTime.parse(mCursor.getString(4)), FunctionsDatabase.getValue(mCursor.getInt(6)), FunctionsDatabase.getValue(mCursor.getInt(5)),
                    mCursor.getInt(9), mCursor.getInt(10), mCursor.getString(7), mCursor.getString(8)));
        }

        return  list;
    }

    public String getUsername(int userid){
        String username = "";
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase("admin*");
        Cursor mCursor = db.rawQuery("select username from usuario where userid = ?", new String[] {String.valueOf(userid)});

        while(mCursor.moveToNext()){
            username = mCursor.getString(0);
        }

        if (username.equals("")) username="null";

        return username;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}