package com.marioparrillamaroto.myeventsapp.ui.popUpEventos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

public class PopUpInfoEventoMeeting extends AppCompatActivity {

    private Evento e;
    private TextView txtTitulo, txtInicio, txtFinal, txtTema, txtEnlace, txtFecha;
    private Button btnCancelarCita;
    private FunctionsDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_info_evento_meeting);

        e = (Evento) getIntent().getExtras().getSerializable("infoEvento");
        fd = new FunctionsDatabase(getApplicationContext());

        txtTitulo = (TextView)findViewById(R.id.lblTituloEventoInfoPEM);
        txtInicio = (TextView)findViewById(R.id.lblHoraInicioEventoInfoPEM);
        txtFinal = (TextView)findViewById(R.id.lblHoraFinalEventoInfoPEM);
        txtTema = (TextView)findViewById(R.id.lblTemaEventoInfoPEM);
        txtEnlace = (TextView)findViewById(R.id.lblEnlaceEventoInfoPEM);
        txtFecha = (TextView)findViewById(R.id.lblFechaEventoMeeting);
        btnCancelarCita = (Button) findViewById(R.id.btnCancelarCitaM);

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

        btnCancelarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setAvailable(true);
                e.setUserSummonerID(null);
                fd.modifyEvent(e);
                Toast.makeText(getApplicationContext(), "Cita Cancelada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void cerrar(View view){
        finish();
    }
}