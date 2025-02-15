package com.example.demo.services;

import com.example.demo.models.Metudiant;
import com.example.demo.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Récupérer tous les étudiants
    public List<Metudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // Récupérer un étudiant par son ID
    public Metudiant getEtudiantById(Long id) {
        Optional<Metudiant> etudiant = etudiantRepository.findById(id);
        return etudiant.orElse(null);
    }

    // Créer ou mettre à jour un étudiant
    public Metudiant saveEtudiant(Metudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Supprimer un étudiant par son ID
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    // Rechercher un étudiant par son INE
    public Metudiant getEtudiantByIne(String ine) {
        return etudiantRepository.findByIne(ine);
    }
}