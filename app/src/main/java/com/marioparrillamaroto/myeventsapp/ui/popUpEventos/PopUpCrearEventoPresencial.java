package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.marioparrillamaroto.myeventsapp.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PopUpCrearEventoPresencial extends AppCompatActivity implements OnMapReadyCallback {

    private FloatingActionButton fab;
    private EditText horaInicio, horaFinal, tituloEvento, temaEvento, fechaInicio;
    private FusedLocationProviderClient clientLocation;
    private ArrayList<Marker> listaMarcadores = new ArrayList<>();
    private boolean titulo = false, tema = false, fecha = false, hInicio = false, hFinal = false, coordenadas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_crear_evento_presencial);

        fab = (FloatingActionButton) findViewById(R.id.fabEventoPresencial);
        tituloEvento = (EditText) findViewById(R.id.txtTituloEventoPresencial);
        temaEvento = (EditText) findViewById(R.id.txtTemaPresencial);
        fechaInicio = (EditText) findViewById(R.id.dateInicioCrearEventoPresencial);
        horaInicio = (EditText) findViewById(R.id.dateHoraInicioPresencial);
        horaFinal = (EditText) findViewById(R.id.dateHoraFinalPresencial);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapEventoInfoPE);
        mapFragment.getMapAsync(this);

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

        tituloEvento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    checkTitulo();
                }
            }
        });

        temaEvento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    checkTema();
                }
            }
        });

        fechaInicio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    checkFecha();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        horaFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    checkHoraFin();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarTodo();
                if(comprobarInputs()){
                    System.out.println("@@@@@@@@ --> "+tituloEvento.getText()+" "+temaEvento.getText()+" "+fechaInicio.getText()+" "+horaInicio.getText()+" "+horaFinal.getText());
                    finish();
                }
            }
        });

        fechaInicio.setInputType(InputType.TYPE_NULL);
        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog x =  new DatePickerDialog(PopUpCrearEventoPresencial.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker  view, int year, int month, int dayOfMonth) {
                        String anos = String.valueOf(year), meses = String.valueOf(month+1), dias = String.valueOf(dayOfMonth);
                        if ((month+1)<10) meses = "0"+meses;
                        if (dayOfMonth<10) dias = "0"+dias;

                        fechaInicio.setText(anos+"-"+meses+"-"+dias);
                    }
                },Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                x.show();
            }
        });

        horaInicio.setInputType(InputType.TYPE_NULL);
        horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog x = new TimePickerDialog(PopUpCrearEventoPresencial.this, new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog x = new TimePickerDialog(PopUpCrearEventoPresencial.this, new TimePickerDialog.OnTimeSetListener() {
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
        clientLocation = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            clientLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                                listaMarcadores.add(googleMap.addMarker(new MarkerOptions().position(latLng).title("Tu ubicación")));
                                coordenadas = true;
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            }else{
                                Toast.makeText(getApplicationContext(), "No se pudo obtener su ubicación",Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });


        }else{
            ActivityCompat.requestPermissions(PopUpCrearEventoPresencial.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                for (Marker x :listaMarcadores) {
                    x.remove();
                    coordenadas = false;
                }
                listaMarcadores.add(googleMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación evento")));
                coordenadas = true;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "¡No se acepto los permisos necesarios!",Toast.LENGTH_LONG).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void comprobarTodo(){
        checkTitulo();
        checkTema();

        if (fechaInicio.length()>0){
            checkFecha();
        }else{
            Toast.makeText(getApplicationContext(), "Introduce una fecha", Toast.LENGTH_SHORT).show();
        }

        if (horaInicio.length()==0){
            Toast.makeText(getApplicationContext(), "Introduce una hora de inicio", Toast.LENGTH_SHORT).show();
        }

        if (horaFinal.length()>0){
            checkHoraFin();
        }else{
            Toast.makeText(getApplicationContext(), "Introduce una hora de fin", Toast.LENGTH_SHORT).show();
        }

        checkMarkers();
    }

    private boolean comprobarInputs(){
        boolean allRigth = false;
        if (titulo&&tema&&fecha&&hInicio&&hFinal&&coordenadas) allRigth = true;
        return allRigth;
    }

    private void checkTitulo(){
        if (tituloEvento.getText().length()>4 && tituloEvento.getText().length()<=15){
            tituloEvento.setTextColor(Color.BLACK);
            titulo=true;
        }
        else if(tituloEvento.getText().length()<4){
            tituloEvento.setTextColor(Color.RED);
            titulo=false;
            Toast.makeText(getApplicationContext(), "Introduce un nombre mayor a 4 digitos", Toast.LENGTH_SHORT).show();
        }
        else{
            tituloEvento.setTextColor(Color.RED);
            titulo=false;
            Toast.makeText(getApplicationContext(), "Introduce un nombre menor a 15 digitos", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkTema(){
        if (temaEvento.getText().length()>4 && temaEvento.getText().length()<=15){
            temaEvento.setTextColor(Color.BLACK);
            tema=true;
        }
        else if(temaEvento.getText().length()<4){
            temaEvento.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce un tema mayor a 4 digitos", Toast.LENGTH_SHORT).show();
        }
        else{
            temaEvento.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce un tema menor a 15 digitos", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkFecha(){
        if (LocalDate.now().isBefore(LocalDate.parse(fechaInicio.getText()))){
            fechaInicio.setTextColor(Color.BLACK);
            fecha=true;
        }else{
            fechaInicio.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce una fecha que ya haya pasado o que sea de hoy", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkHoraFin(){
        if (LocalTime.now().isBefore(LocalTime.parse(horaFinal.getText()))){
            horaFinal.setTextColor(Color.BLACK);
            hInicio=true;
            hFinal=true;
        }else{
            horaFinal.setTextColor(Color.RED);
            hInicio=false;
            hFinal=false;
            Toast.makeText(getApplicationContext(), "Introduce una hora que sea despues de la hora de inicio", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkMarkers(){
        if (!(listaMarcadores.size()>0)){
            coordenadas = false;
            Toast.makeText(getApplicationContext(), "Marca la ubicacion de evento", Toast.LENGTH_SHORT).show();
        }else coordenadas = true;
    }
}