package com.marioparrillamaroto.myeventsapp.ui.ExternalProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;

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
        lblDescription = (TextView)findViewById(R.id.lblPhoneNumberExternalProfile);

        lblUsername.setText(u.getUsername());
        lblDescription.setText(u.getPhonenumber());

        lblDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+u.getPhonenumber())));
            }
        });

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

        ExternalProfileModel epa = new ExternalProfileModel(getApplicationContext());

        ArrayList<Evento> datos = epa.getEventsOfUser(u.getUserID());
        if (datos.size()==0) datos.add(new Evento(1, "No tiene eventos", "null", LocalDateTime.now(),LocalDateTime.now(),false,true,1,1,"",""));

        AdaptadorEventoExternalProfile adapterData = new AdaptadorEventoExternalProfile(datos);
        LinearLayoutManager lym = new LinearLayoutManager(getApplicationContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) findViewById(R.id.recViewEventosEP);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);

    }
}