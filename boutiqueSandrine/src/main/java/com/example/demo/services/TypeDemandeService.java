package com.example.demo.services;

import com.example.demo.models.MtypeDemande;
import com.example.demo.repository.TypeDemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeDemandeService {

    @Autowired
    private TypeDemandeRepository typeDemandeRepository;

    // Récupérer tous les types de demande
    public List<MtypeDemande> getAllTypesDemande() {
        return typeDemandeRepository.findAll();
    }

    // Récupérer un type de demande par son ID
    public MtypeDemande getTypeDemandeById(Long id) {
        Optional<MtypeDemande> typeDemande = typeDemandeRepository.findById(id);
        return typeDemande.orElse(null);
    }

    // Créer ou mettre à jour un type de demande
    public MtypeDemande saveTypeDemande(MtypeDemande typeDemande) {
        return typeDemandeRepository.save(typeDemande);
    }

    // Supprimer un type de demande par son ID
    public void deleteTypeDemande(Long id) {
        typeDemandeRepository.deleteById(id);
    }
}