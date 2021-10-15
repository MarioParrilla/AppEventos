package com.marioparrillamaroto.myeventsapp.ui.notifications;

public class Notifications {
    String usernameEventOwner, message;

    public Notifications(String usernameEventOwner, String message) {
        this.usernameEventOwner = usernameEventOwner;
        this.message = message;
    }

    public String getUsernameEventOwner() {
        return usernameEventOwner;
    }

    public void setUsernameEventOwner(String usernameEventOwner) {
        this.usernameEventOwner = usernameEventOwner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
