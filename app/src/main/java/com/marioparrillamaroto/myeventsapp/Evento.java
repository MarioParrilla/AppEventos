package com.marioparrillamaroto.myeventsapp;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable {
    private String nombreEvento, tema, coordenadas, enlaceVideoMeeting;
    private LocalDateTime horaInicio, horaFinal;
    private boolean eventPreference, available;
    private int eventID;
    private int userOwnerID;
    private int userSummonerID;

    public Evento(Integer eventID, String nombreEvento, String tema, LocalDateTime horaInicio, LocalDateTime horaFinal, boolean eventPreference
            , boolean available, Integer userOwnerID, Integer userSummonerID, String coordenadas, String enlaceVideoMeeting) {
        this.nombreEvento = nombreEvento;
        this.tema = tema;
        this.coordenadas = coordenadas;
        this.enlaceVideoMeeting = enlaceVideoMeeting;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.eventPreference = eventPreference;
        this.available = available;
        this.eventID = eventID;
        this.userOwnerID = userOwnerID;
        this.userSummonerID = userSummonerID;
        this.enlaceVideoMeeting=enlaceVideoMeeting;
        this.coordenadas=coordenadas;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getHoraInicio() {
        return horaInicio.toString();
    }

    public String getHoraInicioParsed() {
        return horaInicio.toLocalTime().toString().substring(0,5);
    }

    public void setHoraInicio(String LocalDateTime) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal.toString();
    }

    public String getHoraFinalParsed() {
        return horaFinal.toLocalTime().toString().substring(0,5);
    }

    public void setHoraFinal(LocalDateTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getEnlaceVideoMeeting() {
        return enlaceVideoMeeting;
    }

    public void setEnlaceVideoMeeting(String enlaceVideoMeeting) {
        this.enlaceVideoMeeting = enlaceVideoMeeting;
    }

    public boolean getEventPreference() {
        return eventPreference;
    }

    public void setEventPreference(boolean eventPreference) {
        this.eventPreference = eventPreference;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Integer getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(Integer userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public Integer getUserSummonerID() {
        return userSummonerID;
    }

    public void setUserSummonerID(Integer userSummonerID) {
        this.userSummonerID = userSummonerID;
    }
}
