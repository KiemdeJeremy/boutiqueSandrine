package com.example.demo.services;

import com.example.demo.models.Mprofil;
import com.example.demo.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfilService {

    @Autowired
    private ProfilRepository profilRepository;

    // Récupérer tous les profils
    public List<Mprofil> getAllProfils() {
        return profilRepository.findAll();
    }

    // Récupérer un profil par son ID
    public Mprofil getProfilById(Long id) {
        Optional<Mprofil> profil = profilRepository.findById(id);
        return profil.orElse(null);
    }

    // Créer ou mettre à jour un profil
    public Mprofil saveProfil(Mprofil profil) {
        return profilRepository.save(profil);
    }

    // Supprimer un profil par son ID
    public void deleteProfil(Long id) {
        profilRepository.deleteById(id);
    }
}