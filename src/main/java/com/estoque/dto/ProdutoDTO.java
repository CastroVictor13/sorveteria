package com.estoque.dto;

import com.estoque.model.Produto;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String categoria;
    private double preco;
    private double peso;
    private int quantidade; // Incluindo quantidade de estoque

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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Método para converter de objeto para DTO
    public static ProdutoDTO fromEntity(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setPeso(produto.getPeso());
        produtoDTO.setQuantidade(produto.getQuantidade()); // Incluindo quantidade
        return produtoDTO;
    }

    // Método para converter de DTO para entidade
    public Produto toEntity() {
        Produto produto = new Produto();
        produto.setId(this.id);
        produto.setNome(this.nome);
        produto.setCategoria(this.categoria);
        produto.setPreco(this.preco);
        produto.setPeso(this.peso);
        produto.setQuantidade(this.quantidade); // Passando a quantidade
        return produto;
    }
}
