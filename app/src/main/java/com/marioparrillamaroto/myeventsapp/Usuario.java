package com.marioparrillamaroto.myeventsapp;

import java.io.Serializable;

public class Usuario implements Serializable {

    String username, description;

    public Usuario(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
