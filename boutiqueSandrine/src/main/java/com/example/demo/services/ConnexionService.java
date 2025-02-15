package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Metudiant;
import com.example.demo.models.Mpersonnel;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.PersonnelRepository;

@Service
public class ConnexionService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    public String verifierUtilisateur(String email, String password, RedirectAttributes redirectAttributes) {
        List<Metudiant> etudiants = etudiantRepository.findAll();

        for (Metudiant etudiant : etudiants) {
            if (etudiant.getEmail().equals(email) && etudiant.getPassword().equals(password)) {
                return "redirect:/index";
            }
        }

        List<Mpersonnel> personnels = personnelRepository.findAll();
        for (Mpersonnel personnel : personnels) {
            if (personnel.getEmail().equals(email) && personnel.getPassword().equals(password)) {
                return "redirect:/index";
            }
        }

        return "connexion";
    }
}
