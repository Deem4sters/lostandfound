package com.softwarica.lostandfound.serverresponse;

public class LostResponse {
    private String status;
    private String token;

    public LostResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }
}
