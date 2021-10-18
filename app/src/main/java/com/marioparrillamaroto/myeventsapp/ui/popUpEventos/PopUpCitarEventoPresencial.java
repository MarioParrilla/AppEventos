package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpCitarEventoPresencial extends AppCompatActivity implements OnMapReadyCallback {


    private Evento e;
    private TextView txtTitulo, txtInicio, txtFinal, txtTema;
    private Button btnCitar;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_citar_evento_presencial);

        e = (Evento) getIntent().getExtras().getSerializable("infoEvento");

        txtTitulo = (TextView)findViewById(R.id.lblTituloEventoCitarP);
        txtInicio = (TextView)findViewById(R.id.lblHoraInicioEventoCitarP);
        txtFinal = (TextView)findViewById(R.id.lblHoraFinalEventoCitarP);
        txtTema = (TextView)findViewById(R.id.lblTemaEventoCitarP);
        mapView = (MapView)findViewById(R.id.mapEventoCitarP);
        btnCitar = (Button)findViewById(R.id.btnCitarPresencial);

        txtTitulo.setText(e.getNombreEvento());
        txtInicio.setText(e.getHoraInicio());
        txtFinal.setText(e.getHoraFinal());
        txtTema.setText(e.getTema());
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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

    }

    public void cerrar(View view){
        finish();
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