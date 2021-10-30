package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

public class PopUpCitarEventoPresencial extends AppCompatActivity {


    private Evento e;
    private TextView txtTitulo, txtInicio, txtFinal, txtTema, txtFecha;
    private Button btnCitar, btnMostrarMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_citar_evento_presencial);

        e = (Evento) getIntent().getExtras().getSerializable("infoEvento");

        txtTitulo = (TextView)findViewById(R.id.lblTituloEventoCitarP);
        txtInicio = (TextView)findViewById(R.id.lblHoraInicioEventoCitarP);
        txtFinal = (TextView)findViewById(R.id.lblHoraFinalEventoCitarP);
        txtTema = (TextView)findViewById(R.id.lblTemaEventoCitarP);
        btnCitar = (Button)findViewById(R.id.btnCitarPresencial);
        btnMostrarMapa = (Button)findViewById(R.id.btnVerMapaCitar);
        txtFecha = (TextView)findViewById(R.id.lblFechaEventoCitarP);

        txtTitulo.setText(e.getNombreEvento());
        txtInicio.setText(e.getHoraInicioParsed());
        txtFinal.setText(e.getHoraFinalParsed());
        txtTema.setText(e.getTema());
        txtFecha.setText(e.getFecha());

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

        btnMostrarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PopUpMostrarUbicacion.class);
                i.putExtra("coordenadas",e.getCoordenadas());
                startActivity(i);
            }
        });
        btnCitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void cerrar(View view){
        finish();
    }

}