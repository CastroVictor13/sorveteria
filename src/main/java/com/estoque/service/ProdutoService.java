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
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        return ProdutoDTO.fromEntity(produto);
    }

    public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoDTO.toEntity();
        Produto produtoSalvo = produtoRepository.save(produto);
        return ProdutoDTO.fromEntity(produtoSalvo);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
        }

        Produto produtoAtualizado = produtoDTO.toEntity();
        produtoAtualizado.setId(id);
        produtoRepository.save(produtoAtualizado);
        return ProdutoDTO.fromEntity(produtoAtualizado);
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
