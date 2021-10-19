package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpModificarEventoPresencial extends AppCompatActivity implements OnMapReadyCallback {

    private Evento e;
    private MapView mapView;
    private FloatingActionButton fabModificar, fabBorrar;
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_modificar_evento_presencial);

        e = (Evento) getIntent().getExtras().getSerializable("infoEventoP");

        fabModificar = (FloatingActionButton)findViewById(R.id.fabEventoPresencialPM);
        fabBorrar = (FloatingActionButton)findViewById(R.id.fabEventoPresencialPME);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoPresencialPM);
        temaEvento = (EditText)findViewById(R.id.txtTemaPresencialPM);
        horaInicio = (EditText)findViewById(R.id.dateHoraInicioPresencialPM);
        horaFinal = (EditText)findViewById(R.id.dateHoraFinalPresencialPM);
        mapView = findViewById(R.id.mapViewPresencialPM);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        tituloEvento.setText(e.getNombreEvento());
        temaEvento.setText(e.getTema());
        horaInicio.setText(e.getHoraInicio());
        horaFinal.setText(e.getHoraFinal());


        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("MapDebug", "onMapReady: map is showing on the screen");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}