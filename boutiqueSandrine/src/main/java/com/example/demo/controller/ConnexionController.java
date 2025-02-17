package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.services.ConnexionService;


@Controller
public class ConnexionController {
    
    @Autowired
    private ConnexionService connexionService;
    
    @GetMapping("/")
    public String home() {
        return "connexion";
    }
    
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    @PostMapping("/api/connexion")
    public String seConnecter(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        RedirectAttributes redirectAttributes
    ) {
        String result = connexionService.verifierUtilisateur(email, password, redirectAttributes );
        if (result.equals("connexion")) {
            redirectAttributes.addFlashAttribute("error", "Email ou mot de passe incorrect");
            return "redirect:/";
        }
        return result;
    }
}
