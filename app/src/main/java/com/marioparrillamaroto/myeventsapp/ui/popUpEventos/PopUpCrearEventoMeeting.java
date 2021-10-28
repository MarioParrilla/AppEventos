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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.gms.maps.MapView;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpCrearEventoMeeting extends AppCompatActivity{

    private FloatingActionButton fab;
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento, fechaInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_crear_evento_meeting);

        fab = (FloatingActionButton)findViewById(R.id.fabEventoMeeting);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoMeeting);
        temaEvento = (EditText)findViewById(R.id.txtTemaMeeting);
        fechaInicio = (EditText)findViewById(R.id.dateFechaInicioCrearEventoMeeting);
        horaInicio = (EditText)findViewById(R.id.dateHoraInicioMeeting);
        horaFinal = (EditText)findViewById(R.id.dateHoraFinalMeeting);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        fechaInicio.setInputType(InputType.TYPE_NULL);
        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog x =  new DatePickerDialog(PopUpCrearEventoMeeting.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog x = new TimePickerDialog(PopUpCrearEventoMeeting.this, new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog x = new TimePickerDialog(PopUpCrearEventoMeeting.this, new TimePickerDialog.OnTimeSetListener() {
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
}
