package com.example.demo.services;

import com.example.demo.models.Mpersonnel;
import com.example.demo.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    // Récupérer tous les personnels
    public List<Mpersonnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    // Récupérer un personnel par son ID
    public Mpersonnel getPersonnelById(Long id) {
        Optional<Mpersonnel> personnel = personnelRepository.findById(id);
        return personnel.orElse(null);
    }

    // Créer ou mettre à jour un personnel
    public Mpersonnel savePersonnel(Mpersonnel personnel) {
        return personnelRepository.save(personnel);
    }

    // Supprimer un personnel par son ID
    public void deletePersonnel(Long id) {
        personnelRepository.deleteById(id);
    }

    // Rechercher un personnel par son CIP
    public Mpersonnel getPersonnelByCip(String cip) {
        return personnelRepository.findByCipPersonnel(cip);
    }
}