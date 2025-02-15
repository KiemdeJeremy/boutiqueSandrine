package com.example.demo.controller;

import com.example.demo.models.Mprofil;
import com.example.demo.services.ProfilService;
import com.example.demo.validateurs.ProfilValidateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/profil")
public class ProfilController {

    @Autowired
    private ProfilService profilService;

    @Autowired
    private ProfilValidateur profilValidateur;

    @GetMapping
    public String getAllProfils(Model model) {
        List<Mprofil> profils = profilService.getAllProfils();
        model.addAttribute("profils", profils);
        return "profil/list";
    }

    @GetMapping("/{id}")
    public String getProfilById(@PathVariable Long id, Model model) {
        Mprofil profil = profilService.getProfilById(id);
        if (profil == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profil non trouvé");
        }
        model.addAttribute("profil", profil);
        return "profil/detail";
    }

    @PostMapping("/enregistrer")
    public String createProfil(@ModelAttribute Mprofil profil, RedirectAttributes redirectAttributes) {

        List<String> erreurs = profilValidateur.valider( profil.getLibelle());

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("profil", profil);
            return "redirect:/api/personnel";
        }

        Mprofil savedProfil = profilService.saveProfil(profil);
        redirectAttributes.addFlashAttribute("successMessage", "Profil créé avec succès");
        return "redirect:/api/personnel";
    }

    @PostMapping("/modifier")
    public String updateProfil( @ModelAttribute Mprofil profil, RedirectAttributes redirectAttributes) {
       
        List<String> erreurs = profilValidateur.valider(
            profil.getLibelle()
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("profil", profil);
            return "redirect:/api/personnel";
        }

        profilService.saveProfil(profil);
        redirectAttributes.addFlashAttribute("successMessage", "Profil mis à jour avec succès");
        return "redirect:/api/personnel";
    }

    @GetMapping("/supprimer")
    public String deleteProfil(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        profilService.deleteProfil(id);
        redirectAttributes.addFlashAttribute("successMessage", "Profil supprimé avec succès");
        return "redirect:/api/personnel";
    }
}