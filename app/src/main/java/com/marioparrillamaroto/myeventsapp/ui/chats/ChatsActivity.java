package com.marioparrillamaroto.myeventsapp.ui.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.marioparrillamaroto.myeventsapp.Chat;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.ui.chats.AdaptadorChats;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {

    private RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

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