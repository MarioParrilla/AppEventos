package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity {


    Button btnVolver;
    TextView acercaDe, soporte, cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnVolver = (Button) findViewById(R.id.btnVolverSettings);
        acercaDe = (TextView) findViewById(R.id.lblAcerdaDe);
        cerrarSesion = (TextView) findViewById(R.id.lblCerrarSesion);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevaPantalla = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(nuevaPantalla);
            }
        });

        acercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"App Creada por: Mario Parrilla Maroto | 2021",Toast.LENGTH_SHORT).show();
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FunctionsDatabase fd = new FunctionsDatabase(getApplicationContext());
                fd.closeSession(getApplicationContext());

                Intent nuevaPantalla = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(nuevaPantalla);

            }
        });

    }

}