package com.example.demo.validateurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Mpersonnel;
import com.example.demo.repository.PersonnelRepository;

@Component
public class PersonnelValidateur extends Validateur {

    @Autowired
    private PersonnelRepository personnelRepository;
    @Override
    public List<String> valider(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String nom = attributs[0]; // Nom du personnel
        String prenom = attributs[1]; // Prénom du personnel
        String email = attributs[2]; // Email du personnel
        String password = attributs[3]; // Mot de passe du personnel
        String cip = attributs[4]; // CIP du personnel
        String idProfil = attributs[5]; // ID du profil (clé étrangère)

        // Validation du nom
        if (nom == null || nom.trim().isEmpty()) {
            erreurs.add("Le nom est obligatoire.");
        }

        // Validation du prénom
        if (prenom == null || prenom.trim().isEmpty()) {
            erreurs.add("Le prénom est obligatoire.");
        }

        // Validation de l'email
        if (email == null || !ValidationUtils.isValidEmail(email)) {
            erreurs.add("L'email est invalide.");
        }

        // Validation du mot de passe
        if (password == null || !ValidationUtils.isValidPassword(password)) {
            erreurs.add("Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule et un chiffre.");
        }

        // Validation du CIP
        if (cip == null || cip.trim().isEmpty()) {
            erreurs.add("Le CIP est obligatoire.");
        }

        Mpersonnel personnel=null;
        personnel=personnelRepository.findByCipPersonnel(cip);
        if(personnel!=null){
            erreurs.add("un utilisateur est déja enregistré avec cet cip");
        }

        Mpersonnel personnel1=null;
        personnel1=personnelRepository.findByEmail(email);
        if(personnel1!=null){
            erreurs.add("un utilisateur possède déja cet mail");
        }

        // Validation de l'ID du profil
        if (idProfil == null || !ValidationUtils.isValidLong(idProfil)) {
            erreurs.add("L'ID du profil est invalide.");
        }

        return erreurs;
    }

    public List<String> validerQuandModifier(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String nom = attributs[0]; // Nom du personnel
        String prenom = attributs[1]; // Prénom du personnel
        String email = attributs[2]; // Email du personnel
        String password = attributs[3]; // Mot de passe du personnel
        String cip = attributs[4]; // CIP du personnel
        String idProfil = attributs[5]; // ID du profil (clé étrangère)

        // Validation du nom
        if (nom == null || nom.trim().isEmpty()) {
            erreurs.add("Le nom est obligatoire.");
        }

        // Validation du prénom
        if (prenom == null || prenom.trim().isEmpty()) {
            erreurs.add("Le prénom est obligatoire.");
        }

        // Validation de l'email
        if (email == null || !ValidationUtils.isValidEmail(email)) {
            erreurs.add("L'email est invalide.");
        }

        // Validation du mot de passe
        if (password == null || !ValidationUtils.isValidPassword(password)) {
            erreurs.add("Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule et un chiffre.");
        }

        // Validation du CIP
        if (cip == null || cip.trim().isEmpty()) {
            erreurs.add("Le CIP est obligatoire.");
        }
        
        // Validation de l'ID du profil
        if (idProfil == null || !ValidationUtils.isValidLong(idProfil)) {
            erreurs.add("L'ID du profil est invalide.");
        }

        return erreurs;
    }
}