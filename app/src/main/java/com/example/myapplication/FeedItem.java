package com.example.myapplication;

public class FeedItem {
    // TIPOS: 1 = VAGA DE EMPRESA, 2 = TALENTO FREELANCER
    private int tipoCard;

    // Dados da Empresa / Vaga
    private String nomeEmpresa;
    private String tituloVaga;
    private String descricaoVaga;
    private String data;
    private String local;
    private String valorHora;

    // Dados do Freelancer
    private String nomeFreelancer;
    private String cargo;
    private String bio;

    // Comum a ambos
    private String avaliacao;

    // Construtor para Vaga de Empresa
    public FeedItem(String nomeEmpresa, String tituloVaga, String descricaoVaga, String data, String local, String valorHora, String avaliacao) {
        this.tipoCard = 1;
        this.nomeEmpresa = nomeEmpresa;
        this.tituloVaga = tituloVaga;
        this.descricaoVaga = descricaoVaga;
        this.data = data;
        this.local = local;
        this.valorHora = valorHora;
        this.avaliacao = avaliacao;
    }

    // Construtor para Perfil Freelancer
    public FeedItem(String nomeFreelancer, String cargo, String bio, String avaliacao) {
        this.tipoCard = 2;
        this.nomeFreelancer = nomeFreelancer;
        this.cargo = cargo;
        this.bio = bio;
        this.avaliacao = avaliacao;
    }

    // Getters
    public int getTipoCard() { return tipoCard; }
    public String getNomeEmpresa() { return nomeEmpresa; }
    public String getTituloVaga() { return tituloVaga; }
    public String getDescricaoVaga() { return descricaoVaga; }
    public String getData() { return data; }
    public String getLocal() { return local; }
    public String getValorHora() { return valorHora; }
    public String getNomeFreelancer() { return nomeFreelancer; }
    public String getCargo() { return cargo; }
    public String getBio() { return bio; }
    public String getAvaliacao() { return avaliacao; }
}