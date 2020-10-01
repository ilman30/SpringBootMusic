package com.ilman.music.model;

public class StatusLogin {
    private Boolean isValid;
    private String token;

    public Boolean getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
