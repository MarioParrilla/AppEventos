package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpCitarEventoMeeting extends AppCompatActivity {

    private Evento e;
    private TextView txtTitulo, txtInicio, txtFinal, txtTema, txtEnlace, txtFecha;
    private Button btnCitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_citar_evento_meeting);
        e = (Evento) getIntent().getExtras().getSerializable("infoEvento");

        txtTitulo = (TextView)findViewById(R.id.lblTituloEventoCitarM);
        txtInicio = (TextView)findViewById(R.id.lblHoraInicioEventoCitarM);
        txtFinal = (TextView)findViewById(R.id.lblHoraFinalEventoCitarM);
        txtTema = (TextView)findViewById(R.id.lblTemaEventoCitarM);
        txtEnlace = (TextView)findViewById(R.id.lblEnlaceEventoCitarM);
        btnCitar = (Button)findViewById(R.id.btnCitarMeeting);
        txtFecha = (TextView)findViewById(R.id.lblFechaEventoCitarM);

        txtTitulo.setText(e.getNombreEvento());
        txtInicio.setText(e.getHoraInicioParsed());
        txtFinal.setText(e.getHoraFinalParsed());
        txtTema.setText(e.getTema());
        txtFecha.setText(e.getFecha());
        txtEnlace.setText(e.getEnlaceVideoMeeting());

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