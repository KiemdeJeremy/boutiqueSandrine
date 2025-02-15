package com.example.demo.controller;

import com.example.demo.models.MtypeDemande;
import com.example.demo.services.TypeDemandeService;
import com.example.demo.validateurs.TypeDemandeValidateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/typeDemande")
public class TypeDemandeController {

    @Autowired
    private TypeDemandeService typeDemandeService;

    @Autowired
    private TypeDemandeValidateur typeDemandeValidateur;

    @GetMapping
    public String getAllTypesDemande(Model model) {
        List<MtypeDemande> typesDemande = typeDemandeService.getAllTypesDemande();
        model.addAttribute("typesDemande", typesDemande);
        model.addAttribute("typeDemande", new MtypeDemande()); // Pour le formulaire
        return "demande";
    }

    @GetMapping("/{id}")
    public String getTypeDemandeById(@PathVariable Long id, Model model) {
        MtypeDemande typeDemande = typeDemandeService.getTypeDemandeById(id);
        if (typeDemande == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type de demande non trouvé");
        }
        model.addAttribute("typeDemande", typeDemande);
        return "typeDemande/detail";
    }

    @PostMapping("/enregistrer")
    public String createTypeDemande(@ModelAttribute MtypeDemande typeDemande, 
                                  RedirectAttributes redirectAttributes) {
        List<String> erreurs = typeDemandeValidateur.valider(
                typeDemande.getLibelle(),
                typeDemande.getTypeService()
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("typeDemande", typeDemande);
            return "redirect:/api/demande";
        }

        MtypeDemande savedTypeDemande = typeDemandeService.saveTypeDemande(typeDemande);
        redirectAttributes.addFlashAttribute("successMessage", "Type de demande créé avec succès");
        return "redirect:/api/demande";
    }

    @PostMapping("/modifier")
    public String updateTypeDemande(@ModelAttribute MtypeDemande typeDemande, 
                                  RedirectAttributes redirectAttributes) {
        List<String> erreurs = typeDemandeValidateur.valider(
                typeDemande.getLibelle(),
                typeDemande.getTypeService()
        );

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("typeDemande", typeDemande);
            return "redirect:/api/demande";
        }

        typeDemandeService.saveTypeDemande(typeDemande);
        redirectAttributes.addFlashAttribute("successMessage", "Type de demande mis à jour avec succès");
        return "redirect:/api/demande";
    }

    @GetMapping("/supprimer")
    public String deleteTypeDemande(@RequestParam("id") Long id, 
                                  RedirectAttributes redirectAttributes) {
        typeDemandeService.deleteTypeDemande(id);
        redirectAttributes.addFlashAttribute("successMessage", "Type de demande supprimé avec succès");
        return "redirect:/api/demande";
    }
}