package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
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
        soporte = (TextView) findViewById(R.id.lblSoporte);
        cerrarSesion = (TextView) findViewById(R.id.lblCerrarSesion);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevaPantalla = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(nuevaPantalla);
            }
        });

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link = Uri.parse("https://192.168.1.62:8080/soporte");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(_link);
                startActivity(i);
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
                fd.closeSession();

                Intent nuevaPantalla = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(nuevaPantalla);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

