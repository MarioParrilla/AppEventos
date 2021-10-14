package com.marioparrillamaroto.myeventsapp;

public class ProximoEvento {
    private String horaInicio, horaFinal, usuarioCitado, tema;

    public ProximoEvento(String horaInicio, String horaFinal, String usuarioCitado, String tema) {
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.usuarioCitado = usuarioCitado;
        this.tema = tema;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getUsuarioCitado() {
        return usuarioCitado;
    }

    public void setUsuarioCitado(String usuarioCitado) {
        this.usuarioCitado = usuarioCitado;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
