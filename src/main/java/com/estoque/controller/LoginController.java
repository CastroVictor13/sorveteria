package com.estoque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String exibirTelaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 Model model) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/produtos";
        } else {
            model.addAttribute("error", "Credenciais inválidas.");
            return "login";
        }
    }
}
