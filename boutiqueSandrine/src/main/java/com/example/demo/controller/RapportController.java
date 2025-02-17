package com.example.demo.controller;

import com.example.demo.models.Mpersonnel;
import com.example.demo.models.Mrapport;
import com.example.demo.services.PersonnelService;
import com.example.demo.services.RapportService;
import com.example.demo.validateurs.RapportValidateur;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/rapport")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private RapportValidateur rapportValidateur;

    @GetMapping
    public String getAllRapports(Model model) {
        List<Mrapport> rapports = rapportService.getAllRapports();
        List<Mpersonnel>  personnels = personnelService.getAllPersonnel();
        model.addAttribute("rapports", rapports);
        model.addAttribute("personnels", personnels);
        model.addAttribute("rapport", new Mrapport()); // Pour le formulaire
        return "rapport";
    }

    @GetMapping("/{id}")
    public String getRapportById(@PathVariable Long id, Model model) {
        Mrapport rapport = rapportService.getRapportById(id);
        if (rapport == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rapport non trouvé");
        }
        model.addAttribute("rapport", rapport);
        return "rapport/detail";
    }

    @PostMapping("/enregistrer")
    public String createRapport(@ModelAttribute Mrapport rapport, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        List<String> erreurs = rapportValidateur.valider(
            rapport.getEtat(),
            rapport.getDateRapport().toString(),
            rapport.getContenuRapport(),
            rapport.getPersonnel() != null ? rapport.getPersonnel().getIdUtilisateur().toString() : null
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("rapport", rapport);
            return "redirect:/api/rapport";
        }

        Mpersonnel perso= personnelService.getPersonnelById(rapport.getPersonnel().getIdUtilisateur());
        Mrapport savedRapport = rapportService.saveRapport(rapport);
        redirectAttributes.addFlashAttribute("successMessage", "Rapport créé avec succès");

        rapportService.generateRapport(savedRapport,perso);
        
        return "redirect:/api/rapport";
    }

    @PostMapping("/modifier")
    public String updateRapport(@ModelAttribute Mrapport rapport, RedirectAttributes redirectAttributes) {
        List<String> erreurs = rapportValidateur.valider(
            rapport.getEtat(),
            rapport.getDateRapport().toString(),
            rapport.getContenuRapport(),
            rapport.getPersonnel() != null ? rapport.getPersonnel().getIdUtilisateur().toString() : null
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("rapport", rapport);
            return "redirect:/api/rapport";
        }

        rapportService.saveRapport(rapport);
        redirectAttributes.addFlashAttribute("successMessage", "Rapport mis à jour avec succès");
        return "redirect:/api/rapport";
    }

    @GetMapping("/supprimer")
    public String deleteRapport(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        rapportService.deleteRapport(id);
        redirectAttributes.addFlashAttribute("successMessage", "Rapport supprimé avec succès");
        return "redirect:/api/rapport";
    }

    @GetMapping("/personnel/{personnelId}")
    public String getRapportsByPersonnel(@PathVariable Long personnelId, Model model) {
        List<Mrapport> rapports = rapportService.getRapportsByPersonnel(personnelId);
        model.addAttribute("rapports", rapports);
        model.addAttribute("personnelId", personnelId);
        return "rapport/personnel-list";
    }
}