package com.example.IceCream_SpringBoot.controller;

import com.example.IceCream_SpringBoot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "Login";
    }

    @GetMapping("/register")
    public String register() {
        return "Registro";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String pin,
                               Model model) {
        if (authService.usuarioExiste(username)) {
            model.addAttribute("error", "El usuario ya existe");
            return "Registro";
        }

        boolean success = authService.registrarNuevoUsuario(username, password, pin);

        if (success) {
            return "redirect:/login"; // Despues de registrar, redirige a la página de login
        } else {
            model.addAttribute("error", "Error al registrar el usuario. Inténtalo de nuevo.");
            return "Registro";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/ventas")
    public String ventas() {
        return "VenderHelados";
    }
}
