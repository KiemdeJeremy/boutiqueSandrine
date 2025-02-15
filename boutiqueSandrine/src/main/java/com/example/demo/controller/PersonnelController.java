package com.example.demo.controller;

import com.example.demo.models.Mpersonnel;
import com.example.demo.models.Mprofil;
import com.example.demo.services.PersonnelService;
import com.example.demo.services.ProfilService;
import com.example.demo.validateurs.PersonnelValidateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/personnel")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private ProfilService profilService;

    @Autowired
    private PersonnelValidateur personnelValidateur;

    @GetMapping
    public String getAllPersonnel(Model model) {
        List<Mpersonnel> personnels = personnelService.getAllPersonnel();
        List<Mprofil> profils = profilService.getAllProfils();
        model.addAttribute("personnels", personnels);
        model.addAttribute("profils", profils);
        model.addAttribute("personnel", new Mpersonnel());
        return "personnel";
    }

    @GetMapping("/{id}")
    public String getPersonnelById(@PathVariable Long id, Model model) {
        Mpersonnel personnel = personnelService.getPersonnelById(id);
        if (personnel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personnel non trouvé");
        }
        model.addAttribute("personnel", personnel);
        return "personnel/detail";
    }

    @GetMapping("/cip/{cip}")
    public String getPersonnelByCip(@PathVariable String cip, Model model) {
        Mpersonnel personnel = personnelService.getPersonnelByCip(cip);
        if (personnel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personnel non trouvé");
        }
        model.addAttribute("personnel", personnel);
        return "personnel/detail";
    }

    @PostMapping("/enregistrer")
    public String createPersonnel(@ModelAttribute Mpersonnel personnel, RedirectAttributes redirectAttributes) {
        List<String> erreurs = personnelValidateur.valider(
                personnel.getNom(),
                personnel.getPrenom(),
                personnel.getEmail(),
                personnel.getPassword(),
                personnel.getCipPersonnel(),
                personnel.getProfil() != null ? personnel.getProfil().getIdProfil().toString() : null);

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("personnel", personnel);
            return "redirect:/api/personnel";
        }

        Mpersonnel savedPersonnel = personnelService.savePersonnel(personnel);
        redirectAttributes.addFlashAttribute("successMessage", "Personnel créé avec succès");
        return "redirect:/api/personnel";
    }

    @PostMapping("/modifier")
    public String updatePersonnel( @ModelAttribute Mpersonnel personnel,
                                   RedirectAttributes redirectAttributes) {
        List<String> erreurs = personnelValidateur.validerQuandModifier(
                personnel.getNom(),
                personnel.getPrenom(),
                personnel.getEmail(),
                personnel.getPassword(),
                personnel.getCipPersonnel(),
                personnel.getProfil() != null ? personnel.getProfil().getIdProfil().toString() : null);

        if (!erreurs.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("personnel", personnel);
            return "redirect:/api/personnel";
        }

        personnelService.savePersonnel(personnel);
        redirectAttributes.addFlashAttribute("successMessage", "Personnel mis à jour avec succès");
        return "redirect:/api/personnel";
    }

    @GetMapping("/supprimer")
    public String deletePersonnel(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        personnelService.deletePersonnel(id);
        redirectAttributes.addFlashAttribute("successMessage", "Personnel supprimé avec succès");
        return "redirect:/api/personnel";
    }
}