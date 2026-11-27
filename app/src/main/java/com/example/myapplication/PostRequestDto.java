package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class PostRequestDto {
    @SerializedName("id_usuario")
    private int id_usuario;

    @SerializedName("conteudo")
    private String conteudo;

    public PostRequestDto(int id_usuario, String conteudo) {
        this.id_usuario = id_usuario;
        this.conteudo = conteudo;
    }
}