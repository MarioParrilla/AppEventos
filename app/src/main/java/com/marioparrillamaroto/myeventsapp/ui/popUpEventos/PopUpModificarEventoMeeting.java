package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;



import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.CoreFuntions;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.ui.perfil.PerfilFragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PopUpModificarEventoMeeting extends AppCompatActivity {

    private Evento e;
    private FloatingActionButton fabModificar, fabEliminar;
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento, enlaceVideomeeting, fechaInicio;
    private boolean titulo = false, tema = false, fecha = false, hInicio = false, hFinal = false, enlace = false;
    private FunctionsDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_modificar_evento_meeting);
        fd = new FunctionsDatabase(getApplicationContext());

        e = (Evento) getIntent().getExtras().getSerializable("infoEventoP");

        fabModificar = (FloatingActionButton)findViewById(R.id.fabEventoMeetingPM);
        fabEliminar = (FloatingActionButton)findViewById(R.id.fabEventoMeetingPME);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoMeetingPM);
        temaEvento = (EditText)findViewById(R.id.txtTemaMeetingPM);
        fechaInicio = (EditText)findViewById(R.id.dateFechaInicioMeetingPM);
        horaInicio = (EditText)findViewById(R.id.dateHoraInicioMeetingPM);
        horaFinal = (EditText)findViewById(R.id.dateHoraFinalMeetingPM);
        enlaceVideomeeting = (EditText)findViewById(R.id.txtEnlaceMeetingPM);

        tituloEvento.setText(e.getNombreEvento());
        temaEvento.setText(e.getTema());
        fechaInicio.setText(e.getFecha());
        horaInicio.setText(e.getHoraInicioParsed());
        horaFinal.setText(e.getHoraFinalParsed());
        enlaceVideomeeting.setText(e.getEnlaceVideoMeeting());

        if (!e.getAvailable()){
            fabModificar.setEnabled(false);
            tituloEvento.setEnabled(false);
            temaEvento.setEnabled(false);
            fechaInicio.setEnabled(false);
            horaInicio.setEnabled(false);
            horaFinal.setEnabled(false);
            enlaceVideomeeting.setEnabled(false);
        }


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

        enlaceVideomeeting.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    checkEnlace();
                }
            }
        });

        fechaInicio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0 && e.getAvailable()){
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
                if (s.length()>0 && e.getAvailable()){
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
                if (s.length()>0 && e.getAvailable()){
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
                    fd.modifyEvent(new Evento(e.getEventID(), CoreFuntions.antiSQL(tituloEvento.getText().toString()), CoreFuntions.antiSQL(temaEvento.getText().toString()), LocalDateTime.parse(CoreFuntions.antiSQL(fechaInicio.getText().toString())+"T"+CoreFuntions.antiSQL(horaInicio.getText().toString())),LocalDateTime.parse(CoreFuntions.antiSQL(fechaInicio.getText().toString())+"T"+CoreFuntions.antiSQL(horaFinal.getText().toString())),
                            true, true, fd.getIDLoginUser().intValue(), null, "",CoreFuntions.antiSQL(enlaceVideomeeting.getText().toString())));
                    Toast.makeText(getApplicationContext(), "Evento Modificado", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(PopUpModificarEventoMeeting.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialogo = new AlertDialog.Builder(PopUpModificarEventoMeeting.this)
                        .setPositiveButton("S??, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fd.deleteEvent(e.getEventID().longValue());
                                finish();
                                Intent i = new Intent(PopUpModificarEventoMeeting.this, MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar") // El t??tulo
                        .setMessage("??Deseas eliminar el evento "+e.getNombreEvento()+"?")
                        .create();

                dialogo.show();

            }   });



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
                DatePickerDialog x =  new DatePickerDialog(PopUpModificarEventoMeeting.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog x = new TimePickerDialog(PopUpModificarEventoMeeting.this, new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog x = new TimePickerDialog(PopUpModificarEventoMeeting.this, new TimePickerDialog.OnTimeSetListener() {
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

    private void comprobarTodo(){
        checkTitulo();
        checkTema();
        checkEnlace();

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
            if (horaInicio.length()>0){
                checkHoraFin();
            }else{
                Toast.makeText(getApplicationContext(), "Introduce una hora de inicio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean comprobarInputs(){
        boolean allRigth = false;
        if (titulo&&tema&&fecha&&hInicio&&hFinal&&enlace) allRigth = true;
        System.out.println("Titulo: "+titulo+" tema: "+tema+" fecha: "+fecha+" hInicio: "+hInicio+ "hfinal: "+hFinal);
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
        if (CoreFuntions.antiSQL(temaEvento.getText().toString()).length()>4 && temaEvento.getText().length()<=30){
            temaEvento.setTextColor(Color.BLACK);
            tema=true;
        }
        else if(CoreFuntions.antiSQL(temaEvento.getText().toString()).length()<=4){
            temaEvento.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce un tema mayor a 5 digitos", Toast.LENGTH_SHORT).show();
        }
        else{
            temaEvento.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce un tema menor a 31 digitos", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkEnlace(){
        if (CoreFuntions.antiSQL(enlaceVideomeeting.getText().toString()).length()>9 && enlaceVideomeeting.getText().length()<=100){
            enlaceVideomeeting.setTextColor(Color.BLACK);
            enlace=true;
        }
        else if(CoreFuntions.antiSQL(enlaceVideomeeting.getText().toString()).length()<9){
            enlaceVideomeeting.setTextColor(Color.RED);
            enlace=false;
            Toast.makeText(getApplicationContext(), "Introduce un enlace de videollamada mayor a 9 digitos", Toast.LENGTH_SHORT).show();
        }
        else{
            enlaceVideomeeting.setTextColor(Color.RED);
            enlace=false;
            Toast.makeText(getApplicationContext(), "Introduce un enlace de videollamada menor a 15 digitos", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkFecha(){
        if (LocalDate.now().isEqual(LocalDate.parse(fechaInicio.getText())) || LocalDate.now().isBefore(LocalDate.parse(fechaInicio.getText()))){
            fechaInicio.setTextColor(Color.BLACK);
            fecha=true;
        }else{
            fechaInicio.setTextColor(Color.RED);
            tema=false;
            Toast.makeText(getApplicationContext(), "Introduce una fecha que ya haya pasado o que sea de hoy", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkHoraInicio(){
        if (horaInicio.getText().length()>0){
            if (fechaInicio.getText().length()>0){
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
            }else{
                fechaInicio.setTextColor(Color.RED);
                tema=false;
                Toast.makeText(getApplicationContext(), "Introduce una fecha!", Toast.LENGTH_SHORT).show();
            }
        }else{
            horaInicio.setTextColor(Color.RED);
            hInicio=false;
            Toast.makeText(getApplicationContext(), "Introduce una hora de inicio", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkHoraFin(){
        if (fechaInicio.length()>0){
            if (horaInicio.getText().length()>0){
                if (LocalTime.parse(horaInicio.getText()).isBefore(LocalTime.parse(horaFinal.getText()))){
                    horaFinal.setTextColor(Color.BLACK);
                    hFinal=true;
                    horaInicio.setTextColor(Color.BLACK);
                    hInicio=true;
                }else{
                    horaFinal.setTextColor(Color.RED);
                    hFinal=false;
                    Toast.makeText(getApplicationContext(), "Introduce una hora que sea despues de la hora de inicio", Toast.LENGTH_SHORT).show();
                }
            }else{
                horaInicio.setTextColor(Color.RED);
                hInicio=false;
                Toast.makeText(getApplicationContext(), "Introduce una hora de inicio", Toast.LENGTH_SHORT).show();
            }
        }
    }
}