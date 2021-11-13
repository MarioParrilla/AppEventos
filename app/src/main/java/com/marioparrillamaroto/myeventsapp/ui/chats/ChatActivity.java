package com.marioparrillamaroto.myeventsapp.ui.chats;

import android.annotation.SuppressLint;

import android.graphics.Color;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.marioparrillamaroto.myeventsapp.DispositivoBluetooth;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatActivity  extends AppCompatActivity{
    private DispositivoBluetooth d;
    private Toolbar toolbar;
    private ActionMenuItemView volver, switchConectar, infoConexion, infoConectar;
    private boolean conexionRealizada;
    private Button btnEnviar;
    private TextInputLayout textoMensaje;
    private RecyclerView recView;
    private ArrayList<MensajeChat> datos;
    private LinearLayoutManager lym;
    private FunctionsDatabase db;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        d = (DispositivoBluetooth) getIntent().getExtras().getSerializable("infoDispositivo");
        toolbar = findViewById(R.id.toolBarChatIndividual);
        toolbar.inflateMenu(R.menu.options_chat);
        setTitle("Chat con "+d.getNombreDispositivo());

        volver = (ActionMenuItemView) toolbar.findViewById(R.id.volverDelChat);
        switchConectar = (ActionMenuItemView) toolbar.findViewById(R.id.bluetoothConnected);
        infoConexion = (ActionMenuItemView)toolbar.findViewById(R.id.statusChatB);
        infoConectar = (ActionMenuItemView)toolbar.findViewById(R.id.textChatConectar);

        infoConectar.setTextColor(Color.WHITE);
        infoConectar.setEnabled(false);

        infoConexion.setEnabled(false);
        conexionRealizada = false;
        infoConexion.setTextColor(Color.RED);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.volverDelChat:
                    finish();
                    return true;
                case R.id.bluetoothConnected:
                    if (conexionRealizada){
                        conexionRealizada = false;
                        infoConexion.setTitle("NO CONECTADO");
                        infoConexion.setTextColor(Color.RED);
                    }else{
                        conexionRealizada = true;
                        infoConexion.setTitle("CONECTADO");
                        infoConexion.setTextColor(Color.GREEN);
                    }
                    return true;
                default:
                    return false;
            }
        });

        datos = new ArrayList<>();
        recView = (RecyclerView) findViewById(R.id.recViewChatIndividual);
        lym = new LinearLayoutManager(getApplicationContext());

        btnEnviar = (Button) findViewById(R.id.btnEnviarB);
        textoMensaje = (TextInputLayout) findViewById(R.id.mensajeChat);

        datos.add(new MensajeChat("Holaaa!",d.getNombreDispositivo(),d.getNombreDispositivo(), LocalDateTime.now()));
        updateAdapter(datos, R.layout.tarjeta_chatindividual2);
        db = new FunctionsDatabase(getApplicationContext());
        String username = db.getUsernameLoginUser();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textoMensaje.getEditText().getText().toString().length()>0 && d != null){
                    datos.add(new MensajeChat(textoMensaje.getEditText().getText().toString(),username,d.getNombreDispositivo(), LocalDateTime.now()));
                    updateAdapter(datos,R.layout.tarjeta_chatindividual1);
                    textoMensaje.getEditText().setText("");
                }else Toast.makeText(getApplicationContext(), "Escribe un mensaje a enviar", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateAdapter(ArrayList<MensajeChat> datos, int Layout){
        AdaptadorChatIndividual adapterData = new AdaptadorChatIndividual(datos,getApplicationContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);
        adapterData.notifyDataSetChanged();
    }
}
