package com.marioparrillamaroto.myeventsapp;

import java.time.LocalDateTime;

public class MensajeChat {

    private String mensaje;
    private String usuarioSender, usuarioReceptor;
    private LocalDateTime fechaMensaje;

    public MensajeChat(String mensaje, String usuarioApp, String usuarioReceptor, LocalDateTime fechaMensaje) {
        this.mensaje = mensaje;
        this.fechaMensaje = fechaMensaje;
        this.usuarioSender = usuarioApp;
        this.usuarioReceptor = usuarioReceptor;
    }

    public LocalDateTime getFechaMensaje() {
        return fechaMensaje;
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
