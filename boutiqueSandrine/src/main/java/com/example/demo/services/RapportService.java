package com.example.demo.services;

import com.example.demo.models.Mrapport;
import com.example.demo.repository.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RapportService {

    @Autowired
    private RapportRepository rapportRepository;

    // Récupérer tous les rapports
    public List<Mrapport> getAllRapports() {
        return rapportRepository.findAll();
    }

    // Récupérer un rapport par son ID
    public Mrapport getRapportById(Long id) {
        Optional<Mrapport> rapport = rapportRepository.findById(id);
        return rapport.orElse(null);
    }

    // Créer ou mettre à jour un rapport
    public Mrapport saveRapport(Mrapport rapport) {
        return rapportRepository.save(rapport);
    }

    // Supprimer un rapport par son ID
    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }

    // Rechercher des rapports par personnel
    public List<Mrapport> getRapportsByPersonnel(Long personnelId) {
        return rapportRepository.findByPersonnel_IdUtilisateur(personnelId);
    }
}