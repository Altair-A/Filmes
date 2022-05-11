package com.example.filmes;
public class FilmeModel {
    public int idFilme;
    public int idcategoria;
    public String titulo;
    public int ano;
    public CategoriaModel categoria;

    public int avaliacao;
    public String tempo;

    public FilmeModel(int idFilme, int idcategoria, String titulo, int ano, CategoriaModel categoria, int avaliacao, String tempo) {
        this.idFilme = idFilme;
        this.idcategoria = idcategoria;
        this.titulo = titulo;
        this.ano = ano;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
        this.tempo = tempo;
    }
}
