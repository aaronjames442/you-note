package com.notes_app.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public class RegisterUserDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
    private String role;

    public RegisterUserDto(String username, String password, String confirmPassword, String role) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public boolean isPasswordsMatch(){
        return password.equals(confirmPassword);
    }
}
