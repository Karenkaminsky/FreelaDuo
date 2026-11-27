package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class DeletarUsuarioDto {
    @SerializedName("id_usuario")
    private int id_usuario;

    public DeletarUsuarioDto(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}