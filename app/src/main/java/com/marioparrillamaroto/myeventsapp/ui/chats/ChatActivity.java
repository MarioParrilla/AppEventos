package com.marioparrillamaroto.myeventsapp.ui.chats;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatActivity  extends AppCompatActivity{
    private BluetoothDevice d;
    private Toolbar toolbar;
    private static ActionMenuItemView volver, switchConectar, infoConexion, infoConectar;
    public static boolean conexionRealizada, puedeConectarse;
    private Button btnEnviar;
    private TextInputLayout textoMensaje;
    private RecyclerView recView;
    private ArrayList<MensajeChat> datos;
    private LinearLayoutManager lym;
    private FunctionsDatabase db;
    private BluetoothAdapter bluetoothAdapter;
    private Handler handler;
    private ChatUtils chatUtils;
    public static final int MESSAGE_STATE_CHANGED = 7;
    public static final int MENSAJELEIDO = 1;
    public static final int MENSAJEESCRITO = 2;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bReceiver, filtro);
        puedeConectarse = true;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        d = (BluetoothDevice) getIntent().getExtras().get("infoDispositivo");

        toolbar = findViewById(R.id.toolBarChatIndividual);
        toolbar.inflateMenu(R.menu.options_chat);
        setTitle("Chat con "+d.getName());

        volver = (ActionMenuItemView) toolbar.findViewById(R.id.volverDelChat);
        switchConectar = (ActionMenuItemView) toolbar.findViewById(R.id.bluetoothConnected);
        infoConexion = (ActionMenuItemView)toolbar.findViewById(R.id.statusChatB);
        infoConectar = (ActionMenuItemView)toolbar.findViewById(R.id.textChatConectar);

        infoConectar.setTextColor(Color.WHITE);
        infoConectar.setEnabled(false);

        infoConexion.setEnabled(false);
        conexionRealizada = false;
        infoConexion.setTextColor(Color.RED);

        db = new FunctionsDatabase(getApplicationContext());
        String username = db.getUsernameLoginUser();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MESSAGE_STATE_CHANGED:
                        switch (msg.arg1) {
                            case ChatUtils.ESCUCHANDO:
                                infoConexion.setTitle("ESCUCHANDO");
                                infoConexion.setTextColor(Color.CYAN);
                                desactivarChat();
                                break;

                            case ChatUtils.CONECTANDO:
                                infoConexion.setTitle("CONECTANDO");
                                infoConexion.setTextColor(Color.YELLOW);
                                break;

                            case ChatUtils.NADA:
                                infoConexion.setTitle("NO CONECTADO");
                                infoConexion.setTextColor(Color.RED);
                                desactivarChat();
                                break;

                            case ChatUtils.CONECTADO:
                                infoConexion.setTitle("CONECTADO");
                                infoConexion.setTextColor(Color.GREEN);
                                activarChat();
                                break;

                            case ChatUtils.CONEXIONFALLADA:
                                infoConexion.setTitle("NO CONECTADO");
                                infoConexion.setTextColor(Color.RED);
                                //conexionRealizada = false;
                                break;
                        }
                    case MENSAJEESCRITO:
                        byte[] writeBuffer = (byte[]) msg.obj;
                        if (writeBuffer!=null){
                            String tmpMessage = new String(writeBuffer);
                            datos.add(new MensajeChat(tmpMessage, username, d.getName(), LocalDateTime.now()));
                            updateAdapter(datos);
                        }
                        break;

                    case MENSAJELEIDO:
                        byte[] readBuffer = (byte[]) msg.obj;
                        if (readBuffer!=null){
                            String readMessage = new String(readBuffer, 0, msg.arg1);
                            datos.add(new MensajeChat(readMessage,d.getName(),username, LocalDateTime.now()));
                            updateAdapter(datos);
                        }
                        break;
                }
                return true;
            }
        });

        chatUtils = new ChatUtils(this, handler);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.volverDelChat:
                    finish();
                    return true;
                case R.id.bluetoothConnected:
                    System.out.println("@@@@@"+conexionRealizada);
                    if (conexionRealizada){
                        Toast.makeText(getApplicationContext(), "Ya estas conectado o estas en ello", Toast.LENGTH_SHORT).show();
                    }else {
                        if (puedeConectarse){
                            conexionRealizada = true;
                            System.out.println("@@@@@Conexion Realizada cambiada "+conexionRealizada);
                            if (conexionRealizada) chatUtils.connect(d);
                        }else{
                            activarBluetooth();
                        }
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

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textoMensaje.getEditText().getText().toString().length()>0 && d != null){
                    String msg = textoMensaje.getEditText().getText().toString();
                    chatUtils.write(msg.getBytes());
                    textoMensaje.getEditText().setText("");
                }else Toast.makeText(getApplicationContext(), "Escribe un mensaje a enviar", Toast.LENGTH_SHORT).show();

            }
        });
        desactivarChat();
    }

    private void updateAdapter(ArrayList<MensajeChat> datos){
        AdaptadorChatIndividual adapterData = new AdaptadorChatIndividual(datos,getApplicationContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);
        recView.getLayoutManager().scrollToPosition(datos.size()-1);
        adapterData.notifyDataSetChanged();
    }

    private final BroadcastReceiver bReceiver = new BroadcastReceiver()
    {

        @SuppressLint("RestrictedApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (estado) {

                    case BluetoothAdapter.STATE_OFF:
                    {
                        conexionRealizada = false;
                        puedeConectarse = false;
                        infoConexion.setTitle("NO CONECTADO");
                        infoConexion.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "Has desconectado el bluetooth", Toast.LENGTH_SHORT).show();
                        desactivarChat();
                        break;
                    }

                    case BluetoothAdapter.STATE_ON:
                    {
                        puedeConectarse = true;
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    };

    public void activarBluetooth(){
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        bluetoothToOn.launch(enableIntent);
    }

    public void activarChat(){
        btnEnviar.setEnabled(true);
    }

    public void desactivarChat(){
        btnEnviar.setEnabled(false);
    }

    private ActivityResultLauncher<Intent> bluetoothToOn = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                puedeConectarse = true;
            }else{
                puedeConectarse = false;
            }
        }
    });

    @Override
    protected void onDestroy() {
        unregisterReceiver(bReceiver);
        chatUtils.stop();
        super.onDestroy();
    }
}
