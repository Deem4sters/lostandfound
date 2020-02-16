package com.softwarica.lostandfound.module;

public class Feedback {

    private String fullname;
    private String email;
    private String feedback;

    public Feedback(String fullname, String email, String message) {
        this.fullname = fullname;
        this.email = email;
        this.feedback = message;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String message) {
        this.feedback = message;
    }

}

