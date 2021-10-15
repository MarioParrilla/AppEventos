package com.marioparrillamaroto.myeventsapp.ui.buscar;

public class UserBsq {

    String username, description;

    public UserBsq(String username, String description) {
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
