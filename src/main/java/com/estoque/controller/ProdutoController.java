package com.estoque.controller;

import com.estoque.dto.ProdutoDTO;
import com.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos") // Mapeia a base da URL para esta classe
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Método para buscar produto por ID (endpoint API)
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produtoDTO);
    }

    // Método para salvar um novo produto (endpoint API)
    @PostMapping
    public ResponseEntity<ProdutoDTO> salvarProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.salvarProduto(produtoDTO);
        return ResponseEntity.status(201).body(novoProduto);
    }

    // Método para atualizar um produto existente (endpoint API)
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // Método para deletar um produto (endpoint API)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    // Método para buscar produtos por nome (endpoint API)
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosPorNome(@RequestParam String nome) {
        List<ProdutoDTO> produtos = produtoService.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    // Método para buscar produtos por categoria (endpoint API)
    @GetMapping("/categoria")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosPorCategoria(@RequestParam String categoria) {
        List<ProdutoDTO> produtos = produtoService.buscarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    // Exibe a lista de produtos na interface
    @GetMapping
    public String mostrarProdutos(Model model) {
        List<ProdutoDTO> produtos = produtoService.listarProdutos(); // Obtém a lista de produtos
        model.addAttribute("produtos", produtos); // Adiciona os produtos ao modelo
        return "produtos"; // Retorna a view produtos.html
    }

    // Exibe a tela de cadastro de produto
    @GetMapping("/cadastrar")
    public String mostrarCadastroProduto(Model model) {
        model.addAttribute("produtoDTO", new ProdutoDTO()); // Cria um novo objeto ProdutoDTO
        return "cadastrar-produto"; // Retorna a view cadastrar-produto.html
    }

    // Processa o cadastro de um novo produto
    @PostMapping("/cadastrar")
    public String cadastrarProduto(@ModelAttribute ProdutoDTO produtoDTO) {
        produtoService.salvarProduto(produtoDTO); // Chama o serviço para adicionar o produto
        return "redirect:/produtos"; // Redireciona para a lista de produtos após o cadastro
    }
}
