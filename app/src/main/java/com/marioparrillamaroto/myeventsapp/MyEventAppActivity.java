package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.marioparrillamaroto.myeventsapp.core.CoreFuntions;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class MyEventAppActivity extends AppCompatActivity {

    private boolean isLogin;
    private Intent nuevaPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_app);

        FunctionsDatabase fd = new FunctionsDatabase(getApplicationContext());

        if (CoreFuntions.checkIsRooted(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"¡Tienes el movil rooteado, no puedes utilizar la app!", Toast.LENGTH_SHORT).show();
        }else{
            fd.syncronizingData();
            fd.checkCloseSession();
            isLogin = fd.checkIsLogin();
            if (isLogin){
                nuevaPantalla = new Intent(MyEventAppActivity.this, MainActivity.class);
                startActivity(nuevaPantalla);
            }
        }

    }
}