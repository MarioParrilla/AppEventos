package com.marioparrillamaroto.myeventsapp;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Long userID;

    private String username;

    private String email;

    private String password;

    private String phonenumber;

    private Boolean cmsAdmin;

    private Boolean enabled;

    public Usuario(Long userID, String username, String email, String password, String phonenumber, Boolean cmsAdmin, Boolean enabled) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.cmsAdmin = cmsAdmin;
        this.enabled = enabled;
    }


    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Boolean getCmsAdmin() {
        return cmsAdmin;
    }
    public void setCmsAdmin(Boolean cmsAdmin) {
        this.cmsAdmin = cmsAdmin;
    }
}
