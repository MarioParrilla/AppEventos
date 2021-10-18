package com.marioparrillamaroto.myeventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PopUpInfoEventoMeeting extends AppCompatActivity {

    private Evento e;
    private TextView txtTitulo, txtInicio, txtFinal, txtTema, txtEnlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_info_evento_meeting);

        e =(Evento) getIntent().getExtras().getSerializable("infoEvento");

        txtTitulo = (TextView)findViewById(R.id.lblTituloEventoInfoPEM);
        txtInicio = (TextView)findViewById(R.id.lblHoraInicioEventoInfoPEM);
        txtFinal = (TextView)findViewById(R.id.lblHoraFinalEventoInfoPEM);
        txtTema = (TextView)findViewById(R.id.lblTemaEventoInfoPEM);
        txtEnlace = (TextView)findViewById(R.id.lblEnlaceEventoInfoPEM);

        txtTitulo.setText(e.getNombreEvento());
        txtInicio.setText(e.getHoraInicio());
        txtFinal.setText(e.getHoraFinal());
        txtTema.setText(e.getTema());
        txtEnlace.setText(e.getEnlaceVideoMeeting());

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
}