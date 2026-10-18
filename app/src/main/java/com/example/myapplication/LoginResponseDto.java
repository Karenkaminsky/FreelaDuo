package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDto {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("usuario")
    private UsuarioData usuario;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public UsuarioData getUsuario() { return usuario; }

    public static class UsuarioData {
        @SerializedName("id_usuario")
        private int idUsuario;

        @SerializedName("nome")
        private String nome;

        @SerializedName("email")
        private String email;

        @SerializedName("tipo_conta")
        private String tipoConta;

        public int getIdUsuario() { return idUsuario; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }
        public String getTipoConta() { return tipoConta; }
    }
}