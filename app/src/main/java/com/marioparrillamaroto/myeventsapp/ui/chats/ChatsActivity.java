package com.marioparrillamaroto.myeventsapp.ui.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marioparrillamaroto.myeventsapp.Chat;
import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.SettingsActivity;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {

    private RecyclerView recView;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        btnVolver = findViewById(R.id.btnVolverChat);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevaPantalla = new Intent(ChatsActivity.this, MainActivity.class);
                startActivity(nuevaPantalla);
            }
        });

        ArrayList<Chat> datos = new ArrayList<Chat>();
        datos.add(new Chat("Admin","admin2",new ArrayList< MensajeChat >()));
        datos.add(new Chat("Admin","pepe",new ArrayList< MensajeChat >()));
        datos.add(new Chat("Admin","pruebe",new ArrayList< MensajeChat >()));

        AdaptadorChats adapterData = new AdaptadorChats(datos);
        LinearLayoutManager lym = new LinearLayoutManager(getApplicationContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) findViewById(R.id.recViewChats);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);
    }
}