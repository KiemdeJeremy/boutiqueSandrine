package com.example.demo.controller;

import com.example.demo.models.Mdemande;
import com.example.demo.models.Metudiant;
import com.example.demo.models.MtypeDemande;
import com.example.demo.services.DemandeService;
import com.example.demo.services.EtudiantService;
import com.example.demo.services.TypeDemandeService;
import com.example.demo.validateurs.DemandeValidateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/demande")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private TypeDemandeService typeDemandeService;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private DemandeValidateur demandeValidateur;

    @GetMapping
    public String getAllDemandes(Model model) {
        List<Mdemande> demandes = demandeService.getAllDemandes();
        List<MtypeDemande> typeDemandes = typeDemandeService.getAllTypesDemande();
        List<Metudiant> etudiants = etudiantService.getAllEtudiants(); 
        model.addAttribute("demandes", demandes);
        model.addAttribute("typeDemandes", typeDemandes);
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("demande", new Mdemande()); // Pour le formulaire
        return "demande";
    }

    @GetMapping("/{id}")
    public String getDemandeById(@PathVariable Long id, Model model) {
        Mdemande demande = demandeService.getDemandeById(id);
        if (demande == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Demande non trouvée");
        }
        model.addAttribute("demande", demande);
        return "demande/detail";
    }

    @PostMapping("/enregistrer")
    public String createDemande(@ModelAttribute Mdemande demande, RedirectAttributes redirectAttributes) {
        List<String> erreurs = demandeValidateur.valider(
            demande.getDateDemande().toString(),
            demande.getObjetDemande(),
            demande.getContenuDemande(),
            demande.getEtudiant() != null ? demande.getEtudiant().getIdUtilisateur().toString() : null,
            demande.getTypeDemande() != null ? demande.getTypeDemande().getIdTypeDemande().toString() : null
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("demande", demande);
            return "redirect:/api/demande";
        }

        Mdemande savedDemande = demandeService.saveDemande(demande);
        redirectAttributes.addFlashAttribute("successMessage", "Demande créée avec succès");
        return "redirect:/api/demande";
    }

    @PostMapping("/modifier")
    public String updateDemande(@ModelAttribute Mdemande demande, RedirectAttributes redirectAttributes) {
        List<String> erreurs = demandeValidateur.valider(
            demande.getDateDemande().toString(),
            demande.getObjetDemande(),
            demande.getContenuDemande(),
            demande.getEtudiant() != null ? demande.getEtudiant().getIdUtilisateur().toString() : null,
            demande.getTypeDemande() != null ? demande.getTypeDemande().getIdTypeDemande().toString() : null
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("demande", demande);
            return "redirect:/api/demande";
        }

        demandeService.saveDemande(demande);
        redirectAttributes.addFlashAttribute("successMessage", "Demande mise à jour avec succès");
        return "redirect:/api/demande";
    }

    @GetMapping("/supprimer")
    public String deleteDemande(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        demandeService.deleteDemande(id);
        redirectAttributes.addFlashAttribute("successMessage", "Demande supprimée avec succès");
        return "redirect:/api/demande";
    }

    @GetMapping("/type/{typeDemandeId}")
    public String getDemandesByTypeDemande(@PathVariable Long typeDemandeId, Model model) {
        List<Mdemande> demandes = demandeService.getDemandesByTypeDemande(typeDemandeId);
        model.addAttribute("demandes", demandes);
        model.addAttribute("typeDemandeId", typeDemandeId);
        return "demande/type-list";
    }

}