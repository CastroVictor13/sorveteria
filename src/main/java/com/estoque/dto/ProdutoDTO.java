package com.estoque.dto;

import com.estoque.model.Produto;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String categoria;
    private double preco;
    private double peso;

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
        return peso; // Getter para peso
    }

    public void setPeso(double peso) {
        this.peso = peso; // Setter para peso
    }

    // Método para converter de objeto para entidade
    public static ProdutoDTO fromEntity(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setPeso(produto.getPeso());
        return produtoDTO;
    }

    // Método para converter de entidade para objeto
    public Produto toEntity() {
        Produto produto = new Produto();
        produto.setId(this.id); // Preserva o ID
        produto.setNome(this.nome);
        produto.setCategoria(this.categoria);
        produto.setPreco(this.preco);
        produto.setPeso(this.peso);
        return produto;
    }
}
