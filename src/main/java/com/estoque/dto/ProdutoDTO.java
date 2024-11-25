package com.estoque.dto;

import com.estoque.model.Produto;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String categoria;
    private double preco;
    private Double volume;
    private int quantidade;

    public ProdutoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public static ProdutoDTO fromEntity(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setVolume(produto.getVolume());
        produtoDTO.setQuantidade(produto.getQuantidade());
        return produtoDTO;
    }

    public Produto toEntity() {
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setCategoria(this.categoria);
        produto.setPreco(this.preco);
        produto.setQuantidade(this.quantidade);

        produto.setVolume(this.volume);
        return produto;
    }
}
