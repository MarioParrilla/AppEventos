package com.marioparrillamaroto.myeventsapp;

import java.util.ArrayList;

public class Chat {
    private String user1, user2;
    private ArrayList<MensajeChat> mensajes = new ArrayList<MensajeChat> ();

    public Chat(String user1, String user2, ArrayList<MensajeChat> mensajes) {
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

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public ArrayList<MensajeChat> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<MensajeChat> mensajes) {
        this.mensajes = mensajes;
    }
}
