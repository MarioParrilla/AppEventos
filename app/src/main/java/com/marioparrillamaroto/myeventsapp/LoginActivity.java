package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private CheckBox guardarSesion;
    private TextView recordarPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPass);
        guardarSesion = (CheckBox) findViewById(R.id.cbxRecordarSesion);
        recordarPass = (TextView) findViewById(R.id.lblRecordarPass);

    }

    public void login(View view) {
        setContentView(R.layout.activity_main);
    }
}