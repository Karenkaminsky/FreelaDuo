package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginRequestDto {
    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    public LoginRequestDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() { return email; }
    public String getSenha() { return senha; }
}