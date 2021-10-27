package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;

public class MyEventAppActivity extends AppCompatActivity {

    private boolean isLogin;
    private Intent nuevaPantalla;
    private static final String URLAPI = "http://192.168.1.62:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_app);

        FunctionsDatabase fd = new FunctionsDatabase(getApplicationContext());


        fd.syncronizingData();

        fd.checkCloseSession();

        isLogin = fd.checkIsLogin();

        if (isLogin){
            nuevaPantalla = new Intent(MyEventAppActivity.this, MainActivity.class);
            startActivity(nuevaPantalla);
        }
        else {
            nuevaPantalla = new Intent(MyEventAppActivity.this, LoginActivity.class);
            startActivity(nuevaPantalla);
        }
    }
}