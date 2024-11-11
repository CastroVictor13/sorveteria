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
    private ProdutoRepository produtoRepository; // Injeção do ProdutoRepository

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll(); // Usa o método findAll do repositório
        return produtos.stream()
                .map(this::converterParaDTO) // Converte Produto para ProdutoDTO
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
        Produto novoProduto = produtoRepository.save(produto);
        return converterParaDTO(novoProduto);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = converterParaEntity(produtoDTO);
            produtoAtualizado.setId(id); // Mantém o ID do produto existente
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

    // Método auxiliar para converter objeto para entidade
    private ProdutoDTO converterParaDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setPeso(produto.getPeso());
        return produtoDTO;
    }

    // Método auxiliar para converter entidade para objeto
    private Produto converterParaEntity(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setPreco(produtoDTO.getPreco());
        produto.setPeso(produtoDTO.getPeso());
        return produto;
    }

    public List<ProdutoDTO> buscarProdutosPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar produtos por categoria
    public List<ProdutoDTO> buscarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
}
