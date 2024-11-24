package com.estoque.service;

import com.estoque.dto.ProdutoDTO;
import com.estoque.exception.ResourceNotFoundException;
import com.estoque.model.Produto;
import com.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return converterParaDTO(produto.get());
        } else {
            throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
        }
    }

    public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
        Produto produto = converterParaEntity(produtoDTO);

        if ("sorvete".equalsIgnoreCase(produtoDTO.getCategoria())) {
            produto.setVolume(produtoDTO.getQuantidade() * 2.0);  // Volume = Quantidade * 2
        }

        Produto novoProduto = produtoRepository.save(produto);
        return converterParaDTO(novoProduto);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = converterParaEntity(produtoDTO);
            produtoAtualizado.setId(id);

            // Ajuste automático do volume para "Sorvete"
            if ("sorvete".equalsIgnoreCase(produtoDTO.getCategoria())) {
                produtoAtualizado.setVolume(produtoDTO.getQuantidade() * 2.0);  // Volume = Quantidade * 2
            }

            produtoRepository.save(produtoAtualizado);
            return converterParaDTO(produtoAtualizado);
        } else {
            throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
        }
    }

    public void deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
        }
    }

    private ProdutoDTO converterParaDTO(Produto produto) {
        return ProdutoDTO.fromEntity(produto);
    }

    private Produto converterParaEntity(ProdutoDTO produtoDTO) {
        return produtoDTO.toEntity();
    }

    public List<ProdutoDTO> buscarProdutosPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> buscarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
}
