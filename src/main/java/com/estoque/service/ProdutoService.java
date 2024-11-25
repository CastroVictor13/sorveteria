package com.estoque.service;

import com.estoque.dto.ProdutoDTO;
import com.estoque.exception.ResourceNotFoundException;
import com.estoque.model.Produto;
import com.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ProdutoDTO> relatorioTotalAbaixoDe20() {
        return produtoRepository.findByQuantidadeLessThan(20).stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> relatorioEstoqueAcimaDe100() {
        return produtoRepository.findByQuantidadeGreaterThan(100).stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        return ProdutoDTO.fromEntity(produto);
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

    public List<ProdutoDTO> relatorioCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria).stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public void salvarProduto(ProdutoDTO produtoDTO) {
        produtoRepository.save(produtoDTO.toEntity());
    }
}

