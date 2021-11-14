package com.marioparrillamaroto.myeventsapp;

import android.bluetooth.BluetoothDevice;
import android.widget.Button;

import java.util.ArrayList;

public class Chat {
    private String user1;
    private BluetoothDevice user2;
    private ArrayList<MensajeChat> mensajes = new ArrayList<MensajeChat> ();

    public Chat(String user1, BluetoothDevice user2) {
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

    public BluetoothDevice getUser2() {
        return user2;
    }

    public void setUser2(BluetoothDevice user2) {
        this.user2 = user2;
    }

}
