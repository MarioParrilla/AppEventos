package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyEventAppActivity extends AppCompatActivity {

    private boolean isLogin;
    private Intent nuevaPantalla;
    private static final String URLAPI = "http://192.168.1.62:8080/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_app);

        FunctionsDatabase fd = new FunctionsDatabase(getApplicationContext());


        fd.syncronizingData(getApplicationContext(),URLAPI);

        isLogin = fd.checkIsLogin(getApplicationContext());

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