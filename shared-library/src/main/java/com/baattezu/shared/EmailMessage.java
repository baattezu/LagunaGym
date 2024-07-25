package com.baattezu.shared;


public class EmailMessage {
    private String email;
    private String message;

    public EmailMessage() {}

    public EmailMessage(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
