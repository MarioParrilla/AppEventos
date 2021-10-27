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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.gms.maps.MapView;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpCrearEventoMeeting extends AppCompatActivity{

    private FloatingActionButton fab;
    private EditText horaInicio, horaFinal,tituloEvento, temaEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_crear_evento_meeting);

        fab = (FloatingActionButton)findViewById(R.id.fabEventoMeeting);
        tituloEvento = (EditText)findViewById(R.id.txtTituloEventoMeeting);
        temaEvento = (EditText)findViewById(R.id.txtTemaMeeting);
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

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

    }
}
