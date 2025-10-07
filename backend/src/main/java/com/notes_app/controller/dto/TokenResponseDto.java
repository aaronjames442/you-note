package com.notes_app.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notes_app.model.User;

public class TokenResponseDto {
    private String token;
    private User user;

    public TokenResponseDto(String token, User user) {
        this.token = token;
        this.user = user;
    }


    @JsonProperty("token")
    String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
