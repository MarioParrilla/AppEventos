package com.marioparrillamaroto.myeventsapp;

import java.time.LocalDateTime;

public class MensajeChat {

    private String mensaje;
    private String usuarioSender, usuarioReceptor;
    private LocalDateTime fechaMensaje;

    public MensajeChat(String mensaje, String usuarioSender, String usuarioReceptor, LocalDateTime fechaMensaje) {
        this.mensaje = mensaje;
        this.fechaMensaje = fechaMensaje;
        this.usuarioSender = usuarioSender;
        this.usuarioReceptor = usuarioReceptor;
    }

    public String getFechaMensaje() {
        if (fechaMensaje.toString().length()>=19) return fechaMensaje.toString().substring(0,fechaMensaje.toString().length()-7).replace("T"," : ");
        else return fechaMensaje.toString().substring(0,fechaMensaje.toString().length()).replace("T"," : ");
    }

    public void setFechaMensaje(LocalDateTime fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuarioSender() {
        return usuarioSender;
    }

    public void setUsuarioSender(String usuarioSender) {
        this.usuarioSender = usuarioSender;
    }

    public String getUsuarioReceptor() {
        return usuarioReceptor;
    }

    public void setUsuarioReceptor(String usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }
}
