package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("id_usuario")
    private int id_usuario;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("tipo_conta")
    private String tipo_conta;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public int getId_usuario() { return id_usuario; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTipo_conta() { return tipo_conta; }
}