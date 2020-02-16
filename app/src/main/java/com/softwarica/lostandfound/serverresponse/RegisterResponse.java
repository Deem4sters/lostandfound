package com.softwarica.lostandfound.serverresponse;

import com.softwarica.lostandfound.module.User;

public class RegisterResponse {
    private String status;
    private String token;
    private User user;

    public RegisterResponse (String status, String token) {
        this.status = status;
        this.token = token;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() { return user;
    }
}

