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
        return produtoRepository.findAll().stream()
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

        // Para Picolé, o volume não é necessário, então é setado como null
        if ("Picolé".equalsIgnoreCase(produtoDTO.getCategoria())) {
            produto.setVolume(null);
        } else if ("Sorvete".equalsIgnoreCase(produtoDTO.getCategoria()) && produtoDTO.getQuantidade() > 0) {
            produto.setVolume((double) (produtoDTO.getQuantidade() * 2)); // Calcula o volume para Sorvete
        }

        Produto novoProduto = produtoRepository.save(produto);
        return converterParaDTO(novoProduto);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = converterParaEntity(produtoDTO);
            produtoAtualizado.setId(id);

            if ("Picolé".equalsIgnoreCase(produtoDTO.getCategoria())) {
                produtoAtualizado.setVolume(null);
            } else if ("Sorvete".equalsIgnoreCase(produtoDTO.getCategoria()) && produtoDTO.getQuantidade() > 0) {
                produtoAtualizado.setVolume((double) (produtoDTO.getQuantidade() * 2)); // Calcula o volume para Sorvete
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
}
