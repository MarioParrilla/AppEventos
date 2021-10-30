package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

import java.util.ArrayList;

public class PopUpModificarEventoPresencial extends AppCompatActivity implements OnMapReadyCallback {

    private Evento e;
    private FloatingActionButton fabModificar, fabBorrar;
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento, fechaInicio;
    private ArrayList<Marker> listaMarcadores = new ArrayList<>();
    private Double latitud, longitud;
    private String coordenadasEvento;
    private LatLng coordenadas;
    private boolean coordenadasCorrectas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_modificar_evento_presencial);

        e = (Evento) getIntent().getExtras().getSerializable("infoEventoP");

        coordenadasEvento = e.getCoordenadas();

        try {
            latitud = Double.parseDouble(coordenadasEvento.substring(0,(coordenadasEvento.indexOf("/"))));
            longitud = Double.parseDouble(coordenadasEvento.substring((coordenadasEvento.indexOf("/")+1), coordenadasEvento.length()));

            System.err.println("Latitud: "+latitud+" Longitud: "+longitud);

            coordenadas = new LatLng(latitud, longitud);
            coordenadasCorrectas = true;
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al recibir las coordenadas", Toast.LENGTH_LONG).show();
        }

        fabModificar = (FloatingActionButton)findViewById(R.id.fabEventoPresencialPM);
        fabBorrar = (FloatingActionButton)findViewById(R.id.fabEventoPresencialPME);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoPresencialPM);
        temaEvento = (EditText)findViewById(R.id.txtTemaPresencialPM);
        fechaInicio = (EditText)findViewById(R.id.dateFechaInicioPresencialPM);
        horaInicio = (EditText)findViewById(R.id.dateHoraInicioPresencialPM);
        horaFinal = (EditText)findViewById(R.id.dateHoraFinalPresencialPM);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapViewPresencialPM);
        mapFragment.getMapAsync(this);

        tituloEvento.setText(e.getNombreEvento());
        temaEvento.setText(e.getTema());
        fechaInicio.setText(e.getFecha());
        horaInicio.setText(e.getHoraInicioParsed());
        horaFinal.setText(e.getHoraFinalParsed());


        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Marker x :listaMarcadores) System.out.println(tituloEvento.getText()+" - Latitud: "+x.getPosition().latitude+" Longitud: "+x.getPosition().longitude);
                finish();
            }
        });

        fabBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Marker x :listaMarcadores) System.out.println(tituloEvento.getText()+" - Latitud: "+x.getPosition().latitude+" Longitud: "+x.getPosition().longitude);
                finish();
            }
        });

        fechaInicio.setInputType(InputType.TYPE_NULL);
        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog x =  new DatePickerDialog(PopUpModificarEventoPresencial.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String anos = String.valueOf(year), meses = String.valueOf(month+1), dias = String.valueOf(dayOfMonth);
                        if ((month+1)<10) meses = "0"+meses;
                        if (dayOfMonth<10) dias = "0"+dias;

                        fechaInicio.setText(anos+"-"+meses+"-"+dias);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                x.show();
            }
        });

        horaInicio.setInputType(InputType.TYPE_NULL);
        horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog x = new TimePickerDialog(PopUpModificarEventoPresencial.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horas = String.valueOf(hourOfDay), minutos = String.valueOf(minute);
                        if (hourOfDay<10) horas = "0"+hourOfDay;
                        if (minute<10) minutos = "0"+minute;

                        horaInicio.setText(horas+":"+minutos);
                    }
                },Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                x.show();
            }
        });

        horaFinal.setInputType(InputType.TYPE_NULL);
        horaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog x = new TimePickerDialog(PopUpModificarEventoPresencial.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horas = String.valueOf(hourOfDay), minutos = String.valueOf(minute);
                        if (hourOfDay<10) horas = "0"+hourOfDay;
                        if (minute<10) minutos = "0"+minute;

                        horaFinal.setText(horas+":"+minutos);
                    }
                },Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                x.show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (coordenadasCorrectas){
            listaMarcadores.add(googleMap.addMarker(new MarkerOptions().position(coordenadas).title("Ubicación evento")));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,10));
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                for (Marker x :listaMarcadores) {
                    x.remove();
                }
                listaMarcadores.add(googleMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación evento")));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }
        });



        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                googleMap.addMarker(new MarkerOptions().position(marker.getPosition()).title("Ubicacón evento"));
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.remove();
                return false;
            }
        });

    }
}