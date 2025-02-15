package com.example.demo.controller;

import com.example.demo.models.Metudiant;
import com.example.demo.models.Mprofil;
import com.example.demo.services.EtudiantService;
import com.example.demo.services.ProfilService;
import com.example.demo.validateurs.EtudiantValidateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/etudiant")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private ProfilService profilService;

    @Autowired
    private EtudiantValidateur etudiantValidateur;

    @GetMapping
    public String getAllEtudiants(Model model) {
        List<Metudiant> etudiants = etudiantService.getAllEtudiants();
        List<Mprofil> profils = profilService.getAllProfils();
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("profils", profils);
        model.addAttribute("etudiant", new Metudiant());
        return "etudiant"; // retourne une vue nommée "etudiant.html"
    }

    @GetMapping("/{id}")
    public String getEtudiantById(@PathVariable Long id, Model model) {
        Metudiant etudiant = etudiantService.getEtudiantById(id);
        if (etudiant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Étudiant non trouvé");
        }
        model.addAttribute("etudiant", etudiant);
        return "etudiant/detail"; // retourne une vue nommée "etudiant/detail"
    }

    @GetMapping("/ine/{ine}")
    public String getEtudiantByIne(@PathVariable String ine, Model model) {
        Metudiant etudiant = etudiantService.getEtudiantByIne(ine);
        if (etudiant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Étudiant non trouvé");
        }
        model.addAttribute("etudiant", etudiant);
        return "etudiant/detail"; // retourne une vue nommée "etudiant/detail"
    }

    @PostMapping("/enregistrer")
    public String createEtudiant(@ModelAttribute Metudiant etudiant, RedirectAttributes redirectAttributes) {
        List<String> erreurs = etudiantValidateur.valider(
                etudiant.getNom(),
                etudiant.getPrenom(),
                etudiant.getEmail(),
                etudiant.getPassword(),
                etudiant.getIne(),
                etudiant.getProfil() != null ? etudiant.getProfil().getIdProfil().toString() : null);

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("etudiant", etudiant);
            return "redirect:/api/etudiant"; // Redirection vers un endpoint pour afficher le formulaire avec les
                                             // erreurs
        }

        Metudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);
        redirectAttributes.addFlashAttribute("successMessage", "Étudiant créé avec succès");
        return "redirect:/api/etudiant";
    }

    @PostMapping("/modifier")
    public String updateEtudiant(@ModelAttribute Metudiant etudiant, RedirectAttributes redirectAttributes) {
        List<String> erreurs = etudiantValidateur.validerQuandModifier(
                etudiant.getNom(),
                etudiant.getPrenom(),
                etudiant.getEmail(),
                etudiant.getPassword(),
                etudiant.getIne(),
                etudiant.getProfil() != null ? etudiant.getProfil().getIdProfil().toString() : null);

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("etudiant", etudiant);
            return "redirect:/api/etudiant"; // Redirection vers le formulaire avec erreurs
        }

        etudiantService.saveEtudiant(etudiant);
        redirectAttributes.addFlashAttribute("successMessage", "Étudiant mis à jour avec succès");
        return "redirect:/api/etudiant"; // Redirection après mise à jour
    }

    @PostMapping("/supprimer")
    public String deleteEtudiant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        etudiantService.deleteEtudiant(id);
        redirectAttributes.addFlashAttribute("successMessage", "Étudiant supprimé avec succès");
        return "redirect:/api/etudiant"; // Redirection après suppression
    }
}