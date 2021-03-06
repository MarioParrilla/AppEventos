package com.marioparrillamaroto.myeventsapp.ui.buscar;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteDatabase;

import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.util.ArrayList;

public class BuscarModel{

    private static final String LOGIN_TABLE = "'LoginInfo'";

    public ArrayList<Usuario> usersToSearch(Context context){
        ArrayList<Usuario> users = new ArrayList<>();

        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getDb();
        Cursor mCursor = db.rawQuery("select * from usuario", null);

        while(mCursor.moveToNext()){
            Usuario user = new Usuario(mCursor.getLong(0),mCursor.getString(1),mCursor.getString(2),mCursor.getString(3),mCursor.getString(4),FunctionsDatabase.getValue(mCursor.getInt(5)), FunctionsDatabase.getValue(mCursor.getInt(6)));
            if (!user.getUsername().equalsIgnoreCase(getLoginUser(context.getApplicationContext()))) users.add(user);
        }

        return users;
    }

    public String getLoginUser(Context context){
        String username = "";
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getDb();
        Cursor mCursor = db.rawQuery("select username from "+LOGIN_TABLE, null);

        while(mCursor.moveToNext()){
            username = mCursor.getString(0);
        }

        return username;
    }

}