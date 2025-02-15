package com.example.demo.controller;

import com.example.demo.models.Mrapport;
import com.example.demo.services.RapportService;
import com.example.demo.validateurs.RapportValidateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/rapport")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @Autowired
    private RapportValidateur rapportValidateur;

    @GetMapping
    public String getAllRapports(Model model) {
        List<Mrapport> rapports = rapportService.getAllRapports();
        model.addAttribute("rapports", rapports);
        return "rapport/list"; // retourne une vue nommée "rapport/list"
    }

    @GetMapping("/{id}")
    public String getRapportById(@PathVariable Long id, Model model) {
        Mrapport rapport = rapportService.getRapportById(id);
        if (rapport == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rapport non trouvé");
        }
        model.addAttribute("rapport", rapport);
        return "rapport/detail"; // retourne une vue nommée "rapport/detail"
    }

    @PostMapping
    public String createRapport(@ModelAttribute Mrapport rapport, RedirectAttributes redirectAttributes) {
        List<String> erreurs = rapportValidateur.valider(
                rapport.getEtat(),
                rapport.getDateRapport().toString(),
                rapport.getContenuRapport(),
                rapport.getPersonnel() != null ? rapport.getPersonnel().getIdUtilisateur().toString() : null);

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("rapport", rapport);
            return "redirect:/rapport/create"; // Redirection vers un endpoint pour afficher le formulaire avec les
                                               // erreurs
        }

        Mrapport savedRapport = rapportService.saveRapport(rapport);
        redirectAttributes.addFlashAttribute("successMessage", "Rapport créé avec succès");
        return "redirect:/api/rapport/" + savedRapport.getIdRapport();
    }

    @PutMapping("/{id}")
    public String updateRapport(@PathVariable Long id, @ModelAttribute Mrapport rapport,
            RedirectAttributes redirectAttributes) {
        if (!id.equals(rapport.getIdRapport())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID mismatch");
        }
        List<String> erreurs = rapportValidateur.valider(
                rapport.getEtat(),
                rapport.getDateRapport().toString(),
                rapport.getContenuRapport(),
                rapport.getPersonnel() != null ? rapport.getPersonnel().getIdUtilisateur().toString() : null);

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("rapport", rapport);
            return "redirect:/rapport/edit/" + id; // Redirection vers un endpoint pour afficher le formulaire avec les
                                                   // erreurs
        }

        rapportService.saveRapport(rapport);
        redirectAttributes.addFlashAttribute("successMessage", "Rapport mis à jour avec succès");
        return "redirect:/api/rapport/" + id; // Redirection après mise à jour
    }

    @DeleteMapping("/{id}")
    public String deleteRapport(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        rapportService.deleteRapport(id);
        redirectAttributes.addFlashAttribute("successMessage", "Rapport supprimé avec succès");
        return "redirect:/api/rapport"; // Redirection après suppression
    }

    @GetMapping("/personnel/{personnelId}")
    public String getRapportsByPersonnel(@PathVariable Long personnelId, Model model) {
        List <Mrapport>rapports = rapportService.getRapportsByPersonnel(personnelId);
        model.addAttribute("rapports", rapports);
        model.addAttribute("personnelId", personnelId);
        return "rapport/personnel-list"; // retourne une vue pour lister les rapports d'un personnel spécifique
    }
}