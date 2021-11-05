package com.marioparrillamaroto.myeventsapp.ui.login;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteDatabase;

import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginModel {

    private Context context;
    private static final String COLUMN_USERID = "'userid'";
    private static final String COLUMN_USERNAME = "'username'";
    private static final String COLUMN_PASSWORD = "'password'";
    private static final String COLUMN_SAVESESSION = "'saveSession'";
    private static final String LOGIN_TABLE = "'LoginInfo'";


    public LoginModel(Context context){
        this.context = context;
    }

    public boolean userExists(String username, String passwordNoSecured){
        boolean exists = false;
        int count = 0;
        String passwordDB = "";
        try{
            SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getDb();
            Cursor mCursor = db.rawQuery("select count(*), password from usuario where username = ?", new String[]{username});


            while(mCursor.moveToNext()){
                count = mCursor.getInt(0);
                passwordDB = mCursor.getString(1);
            }
            if (count==1 && BCrypt.verifyer().verify(passwordNoSecured.toCharArray(), passwordDB).verified) exists = true;

            return exists;
        }catch (Exception e){
            e.printStackTrace();
        }
        return exists;
    }

    public boolean registerUserLogin(String username, boolean guardarSesion){
        boolean allRigth = false;
        long isInserted = 0L, userid = 0;
        String password = "";

        try{
            SQLiteDatabase db = new FunctionsDatabase(context.getApplicationContext()).getDb();
            ContentValues cv = new ContentValues();

            Cursor mCursor = db.rawQuery("select userid, password from usuario where username = ?", new String[]{username});

            while(mCursor.moveToNext()){
                userid = mCursor.getLong(0);
                password = mCursor.getString(1);
            }

            cv.put(COLUMN_USERID,userid);
            cv.put(COLUMN_USERNAME,username);
            cv.put(COLUMN_PASSWORD,password);
            if (guardarSesion) cv.put(COLUMN_SAVESESSION, 1);
            else cv.put(COLUMN_SAVESESSION, 0);

            isInserted = db.insert(LOGIN_TABLE, null, cv);

            if (isInserted==-1L) throw new Exception("¡No se puedo hacer la inserción de datos!");
            allRigth = true;
        }catch (Exception e){
            System.err.println("Error al sincronizar user login: "+e.getMessage());
        }

        return allRigth;
    }

}
