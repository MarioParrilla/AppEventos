package com.marioparrillamaroto.myeventsapp.ui.perfil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

public class PerfilModel{

    private static final String LOGIN_TABLE = "'LoginInfo'";

    public Usuario getLoginUser(Context context){
        Usuario user = null;
        Long userid = 0L;
        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase();
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
}