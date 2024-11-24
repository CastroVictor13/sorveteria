package com.estoque.controller;

import com.estoque.dto.ProdutoDTO;
import com.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String mostrarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "produtos";
    }

    @GetMapping("/cadastrar")
    public String mostrarCadastroProduto(Model model) {
        model.addAttribute("produtoDTO", new ProdutoDTO());
        return "cadastrarProduto";
    }

    @PostMapping("/cadastrar")
    public String cadastrarProduto(@ModelAttribute ProdutoDTO produtoDTO) {
        if ("Picolé".equals(produtoDTO.getCategoria())) {
            produtoDTO.setVolume(null);
        } else if ("Sorvete".equals(produtoDTO.getCategoria())) {
            if (produtoDTO.getQuantidade() > 0) {
                produtoDTO.setVolume((double) (produtoDTO.getQuantidade() * 2));
            }
        }

        produtoService.salvarProduto(produtoDTO);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        model.addAttribute("produtoDTO", produtoDTO);
        return "editarProduto";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @ModelAttribute ProdutoDTO produtoDTO) {
        if ("Picolé".equals(produtoDTO.getCategoria())) {
            produtoDTO.setVolume(null); // Deixa o volume nulo para Picolé
        } else if ("Sorvete".equals(produtoDTO.getCategoria())) {
            if (produtoDTO.getQuantidade() > 0) {
                produtoDTO.setVolume(produtoDTO.getQuantidade() * 2.0); // Volume calculado para Sorvete
            }
        }

        produtoService.atualizarProduto(id, produtoDTO);
        return "redirect:/produtos";
    }

    @PostMapping("/excluir")
    public String excluirProduto(@RequestParam Long id) {
        produtoService.deletarProduto(id);
        return "redirect:/produtos";
    }
}
