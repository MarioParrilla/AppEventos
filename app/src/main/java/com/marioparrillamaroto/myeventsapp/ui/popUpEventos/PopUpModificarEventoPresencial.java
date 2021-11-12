package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private boolean titulo = false, tema = false, fecha = false, hInicio = false, hFinal = false, coordenadasB = false;
    private FunctionsDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_modificar_evento_presencial);
        fd = new FunctionsDatabase(getApplicationContext());

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

        if (!e.getAvailable()){
            fabModificar.setEnabled(false);
            tituloEvento.setEnabled(false);
            temaEvento.setEnabled(false);
            fechaInicio.setEnabled(false);
            horaInicio.setEnabled(false);
            horaFinal.setEnabled(false);
        }


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

        horaInicio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    checkHoraInicio();
                    checkHoraFin();
                }else{
                    Toast.makeText(getApplicationContext(), "Introduce una hora de inicio", Toast.LENGTH_SHORT).show();
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
                    if (horaInicio.length()>0){
                        checkHoraFin();
                    }else{
                        Toast.makeText(getApplicationContext(), "Introduce una hora de inicio", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarTodo();
                if(comprobarInputs()){
                    LatLng coord = listaMarcadores.get(0).getPosition();
                    fd.modifyEvent(new Evento(e.getEventID(), tituloEvento.getText().toString(), temaEvento.getText().toString(), LocalDateTime.parse(fechaInicio.getText()+"T"+horaInicio.getText()),LocalDateTime.parse(fechaInicio.getText()+"T"+horaFinal.getText()),
                            false, true, fd.getIDLoginUser().intValue(), null, coord.latitude+"/"+coord.longitude,""));
                    finish();
                    Intent i = new Intent(PopUpModificarEventoPresencial.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        fabBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialogo = new AlertDialog.Builder(PopUpModificarEventoPresencial.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fd.deleteEvent(e.getEventID().longValue());
                                finish();
                                Intent i = new Intent(PopUpModificarEventoPresencial.this, MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar") // El título
                        .setMessage("¿Deseas eliminar el evento "+e.getNombreEvento()+"?")
                        .create();

                dialogo.show();

            }   });

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
                    coordenadasB = false;
                }
                listaMarcadores.add(googleMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación evento")));
                coordenadasB = true;
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
                listaMarcadores.clear();
                return false;
            }
        });

    }

    private void comprobarTodo(){
        checkTitulo();
        checkTema();

        if (fechaInicio.length()>0){
            checkFecha();
        }else{
            Toast.makeText(getApplicationContext(), "Introduce una fecha", Toast.LENGTH_SHORT).show();
        }

        if (horaInicio.length()>0){
            checkHoraInicio();
        }else{
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
        if (titulo&&tema&&fecha&&hInicio&&hFinal&&coordenadasB) allRigth = true;
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
        if (LocalDate.now().isBefore(LocalDate.parse(fechaInicio.getText())) || LocalDate.now().isEqual(LocalDate.parse(fechaInicio.getText()))){
            fechaInicio.setTextColor(Color.BLACK);
            fecha=true;
        }else{
            fechaInicio.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce una fecha que ya haya pasado o que sea de hoy", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkHoraInicio(){
        if(!LocalDate.parse(fechaInicio.getText()).isAfter(LocalDate.now())){
            if (LocalTime.parse(horaInicio.getText()).isAfter(LocalTime.now())){
                horaInicio.setTextColor(Color.BLACK);
                hInicio=true;
            }
            else{
                horaInicio.setTextColor(Color.RED);
                hInicio=false;
                Toast.makeText(getApplicationContext(), "Introduce una hora que sea despues de la hora actual", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkHoraFin(){

        if (LocalTime.parse(horaInicio.getText()).isBefore(LocalTime.parse(horaFinal.getText()))){
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
            coordenadasB = false;
            Toast.makeText(getApplicationContext(), "Marca la ubicacion de evento", Toast.LENGTH_SHORT).show();
        }else coordenadasB = true;
    }
}