package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
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
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento, fechaInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_modificar_evento_presencial);

        e = (Evento) getIntent().getExtras().getSerializable("infoEventoP");

        fabModificar = (FloatingActionButton)findViewById(R.id.fabEventoPresencialPM);
        fabBorrar = (FloatingActionButton)findViewById(R.id.fabEventoPresencialPME);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoPresencialPM);
        temaEvento = (EditText)findViewById(R.id.txtTemaPresencialPM);
        fechaInicio = (EditText)findViewById(R.id.dateFechaInicioPresencialPM);
        horaInicio = (EditText)findViewById(R.id.dateHoraInicioPresencialPM);
        horaFinal = (EditText)findViewById(R.id.dateHoraFinalPresencialPM);
        mapView = findViewById(R.id.mapViewPresencialPM);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        tituloEvento.setText(e.getNombreEvento());
        temaEvento.setText(e.getTema());
        fechaInicio.setText(e.getFecha());
        horaInicio.setText(e.getHoraInicioParsed());
        horaFinal.setText(e.getHoraFinalParsed());


        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

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