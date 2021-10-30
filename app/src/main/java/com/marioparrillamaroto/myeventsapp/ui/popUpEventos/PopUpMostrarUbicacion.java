package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpMostrarUbicacion extends AppCompatActivity implements OnMapReadyCallback {

    private Double latitud, longitud;
    private String coordenadasEvento;
    private LatLng coordenadas;
    private boolean coordenadasCorrectas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_mostrar_ubicacion);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.75),(int)(height*.75));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        coordenadasEvento = (String) getIntent().getExtras().getSerializable("coordenadas");

        try {
            latitud = Double.parseDouble(coordenadasEvento.substring(0,(coordenadasEvento.indexOf("/"))));
            longitud = Double.parseDouble(coordenadasEvento.substring((coordenadasEvento.indexOf("/")+1), coordenadasEvento.length()));

            System.err.println("Latitud: "+latitud+" Longitud: "+longitud);

            coordenadas = new LatLng(latitud, longitud);
            coordenadasCorrectas = true;
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al recibir las coordenadas", Toast.LENGTH_LONG).show();
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapEventoInfoPE);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (coordenadasCorrectas) {
            googleMap.addMarker(new MarkerOptions().position(coordenadas).title("Ubicación evento"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 10));
        }
    }
}