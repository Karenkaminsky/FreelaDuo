package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("usuario")
    private UsuarioDto usuario;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }
}