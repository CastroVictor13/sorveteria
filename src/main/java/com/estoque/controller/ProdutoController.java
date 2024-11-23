package com.estoque.controller;

import com.estoque.dto.ProdutoDTO;
import com.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produto")  // Já está mapeado corretamente
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String mostrarProdutos(Model model) {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        model.addAttribute("produtos", produtos);
        return "produtos"; // Retorna a view produtos.html
    }

    @GetMapping("/cadastrar")
    public String mostrarCadastroProduto(Model model) {
        model.addAttribute("produtoDTO", new ProdutoDTO()); // Cria um novo objeto ProdutoDTO
        return "cadastrar-produto"; // Retorna a view cadastrar-produto.html
    }

    @PostMapping("/cadastrar")
    public String cadastrarProduto(@ModelAttribute ProdutoDTO produtoDTO) {
        produtoService.salvarProduto(produtoDTO);
        return "redirect:/produto"; // Redireciona para a lista de produtos após o cadastro
    }
}
