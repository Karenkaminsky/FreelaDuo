package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestDto {
    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    @SerializedName("tipo_conta")
    private String tipo_conta;

    @SerializedName("documento")
    private String documento;

    // O construtor exige a ordem: nome, email, senha, tipo_conta, documento
    public RegisterRequestDto(String nome, String email, String senha, String tipo_conta, String documento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo_conta = tipo_conta;
        this.documento = documento;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getTipo_conta() { return tipo_conta; }
    public String getDocumento() { return documento; }
}