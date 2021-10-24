package com.marioparrillamaroto.myeventsapp.ui.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

public class LoginModel {

    public int userExists(Context context, String username, String password){

        SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getReadableDatabase();
        Cursor mCursor = db.rawQuery("select count(*) from usuario where username = ? and password = ?", new String[]{username, password});

        int exists = 0;
        while(mCursor.moveToNext()){
            exists = mCursor.getInt(0);
        }

        return exists;
    }

}
