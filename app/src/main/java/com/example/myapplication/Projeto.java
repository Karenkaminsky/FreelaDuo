package com.example.myapplication;

public class Projeto {
    private int id_projeto;
    private int id_usuario;
    private String titulo;
    private String descricao;
    private String categoria;
    private double orcamento;
    private String status;
    private String data_publicacao;

    // Getters e Setters necessários para o Gson e para a RecyclerView
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public double getOrcamento() { return orcamento; }
}