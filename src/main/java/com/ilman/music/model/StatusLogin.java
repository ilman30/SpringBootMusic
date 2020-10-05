package com.ilman.music.model;

import java.util.List;

public class StatusLogin {
    private Boolean isValid;
    private String token;
    private List<String> roles;

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

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
