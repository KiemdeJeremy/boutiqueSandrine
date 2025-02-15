package com.example.demo.services;

import com.example.demo.models.Mdemande;
import com.example.demo.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    // Récupérer toutes les demandes
    public List<Mdemande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    // Récupérer une demande par son ID
    public Mdemande getDemandeById(Long id) {
        Optional<Mdemande> demande = demandeRepository.findById(id);
        return demande.orElse(null);
    }

    // Créer ou mettre à jour une demande
    public Mdemande saveDemande(Mdemande demande) {
        return demandeRepository.save(demande);
    }

    // Supprimer une demande par son ID
    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    // Correction : Utiliser la méthode corrigée du repository
    public List<Mdemande> getDemandesByTypeDemande(Long typeDemandeId) {
        return demandeRepository.findByTypeDemande_IdTypeDemande(typeDemandeId);
    }

}