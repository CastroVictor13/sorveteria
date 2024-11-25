package com.estoque.controller;

import com.estoque.dto.ProdutoDTO;
import com.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/relatorios")
    public String gerarRelatorios(@RequestParam(value = "relatorio", required = false) Integer relatorio, Model model) {
        List<ProdutoDTO> produtos;
        switch (relatorio) {
            case 1:
                produtos = produtoService.relatorioTotalAbaixoDe20();
                break;
            case 2:
                produtos = produtoService.relatorioEstoqueAcimaDe100();
                break;
            case 3:
                produtos = produtoService.relatorioCategoria("Picolé");
                break;
            case 4:
                produtos = produtoService.relatorioCategoria("Sorvete");
                break;
            default:
                produtos = produtoService.listarProdutos();
                break;
        }
        model.addAttribute("produtos", produtos);
        return "relatorios";
    }

    @PostMapping("/excluir")
    public String excluirProduto(@RequestParam("id") Long id) {
        produtoService.excluirProduto(id);
        return "redirect:/produtos";
    }

    @GetMapping("/cadastrar")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("produto", new ProdutoDTO());
        return "cadastrarProduto";
    }

    @PostMapping("/cadastrar")
    public String cadastrarProduto(@ModelAttribute ProdutoDTO produtoDTO) {
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
            produtoDTO.setVolume(null); // Volume nulo para Picolé
        }

        produtoService.atualizarProduto(id, produtoDTO);
        return "redirect:/produtos";
    }
}

