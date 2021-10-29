package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpInfoEventoMeeting extends AppCompatActivity {

    private Evento e;
    private TextView txtTitulo, txtInicio, txtFinal, txtTema, txtEnlace, txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_info_evento_meeting);

        e = (Evento) getIntent().getExtras().getSerializable("infoEvento");

        txtTitulo = (TextView)findViewById(R.id.lblTituloEventoInfoPEM);
        txtInicio = (TextView)findViewById(R.id.lblHoraInicioEventoInfoPEM);
        txtFinal = (TextView)findViewById(R.id.lblHoraFinalEventoInfoPEM);
        txtTema = (TextView)findViewById(R.id.lblTemaEventoInfoPEM);
        txtEnlace = (TextView)findViewById(R.id.lblEnlaceEventoInfoPEM);
        txtFecha = (TextView)findViewById(R.id.lblFechaEventoMeeting);

        txtTitulo.setText(e.getNombreEvento());
        txtInicio.setText(e.getHoraInicioParsed());
        txtFinal.setText(e.getHoraFinalParsed());
        txtTema.setText(e.getTema());
        txtEnlace.setText(e.getEnlaceVideoMeeting());
        txtFecha.setText(e.getFecha());

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

    }

    public void cerrar(View view){
        finish();
    }
}