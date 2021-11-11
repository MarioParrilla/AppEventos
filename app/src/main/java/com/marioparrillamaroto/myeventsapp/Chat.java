package com.marioparrillamaroto.myeventsapp;

import android.widget.Button;

import java.util.ArrayList;

public class Chat {
    private String user1;
    private DispositivoBluetooth user2;
    private ArrayList<MensajeChat> mensajes = new ArrayList<MensajeChat> ();

    public Chat(String user1, DispositivoBluetooth user2, ArrayList<MensajeChat> mensajes) {
        this.user1 = user1;
        this.user2 = user2;
        this.mensajes = mensajes;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public DispositivoBluetooth getUser2() {
        return user2;
    }

    public void setUser2(DispositivoBluetooth user2) {
        this.user2 = user2;
    }

    public ArrayList<MensajeChat> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<MensajeChat> mensajes) {
        this.mensajes = mensajes;
    }
}
