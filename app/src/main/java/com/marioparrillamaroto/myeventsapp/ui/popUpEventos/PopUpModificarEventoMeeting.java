package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class PopUpModificarEventoMeeting extends AppCompatActivity {

    private Evento e;
    private FloatingActionButton fab;
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento, enlaceVideomeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_modificar_evento_meeting);

        e = (Evento) getIntent().getExtras().getSerializable("infoEventoP");

        fab = (FloatingActionButton)findViewById(R.id.fabEventoMeetingPM);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoMeetingPM);
        temaEvento = (EditText)findViewById(R.id.txtTemaMeetingPM);
        horaInicio = (EditText)findViewById(R.id.dateHoraInicioMeetingPM);
        horaFinal = (EditText)findViewById(R.id.dateHoraFinalMeetingPM);
        enlaceVideomeeting = (EditText)findViewById(R.id.txtEnlaceMeetingPM);

        tituloEvento.setText(e.getNombreEvento());
        temaEvento.setText(e.getTema());
        horaInicio.setText(e.getHoraInicio());
        horaFinal.setText(e.getHoraFinal());
        enlaceVideomeeting.setText(e.getEnlaceVideoMeeting());

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

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

    }
}