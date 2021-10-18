package com.marioparrillamaroto.myeventsapp.ui.ExternalProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.ui.perfil.AdaptadorEvento;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ExternalProfileActivity extends AppCompatActivity {

    private RecyclerView recView;
    private Usuario u;
    private TextView lblUsername, lblDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_profile);

        u = (Usuario) getIntent().getExtras().getSerializable("infoUsuario");

        lblUsername = (TextView)findViewById(R.id.lblUsernameExternalProfile);
        lblDescription = (TextView)findViewById(R.id.lblDescripcionExternalProfile);

        lblUsername.setText(u.getUsername());
        lblDescription.setText(u.getDescription());

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


        ArrayList<Evento> datos = new ArrayList<Evento>();

        datos.add(new Evento("Prueba8", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin5","Pruebas5", true,true,0,0,0,"Preuba"));
        datos.add(new Evento("Prueba9", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin6","Pruebas6", false,true,0,0,0,"Preuba"));
        datos.add(new Evento("Prueba7", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin6","Pruebas6", true,true,0,0,0,"Preuba"));
        datos.add(new Evento("¡No tiene Eventos!", LocalDateTime.now(),LocalDateTime.now(),"null","", false,true,0,0,0,""));

        AdaptadorEventoExternalProfile adapterData = new AdaptadorEventoExternalProfile(datos);
        LinearLayoutManager lym = new LinearLayoutManager(getApplicationContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) findViewById(R.id.recViewEventosEP);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);

    }
}