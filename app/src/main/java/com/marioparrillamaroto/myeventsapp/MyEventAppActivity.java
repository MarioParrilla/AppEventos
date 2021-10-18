package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;

public class MyEventAppActivity extends AppCompatActivity {

    private boolean isLogin;
    private Intent nuevaPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_app);

        isLogin = false;

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