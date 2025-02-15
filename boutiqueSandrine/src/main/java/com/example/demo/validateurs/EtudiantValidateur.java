package com.example.demo.validateurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Metudiant;
import com.example.demo.repository.EtudiantRepository;


@Component
public class EtudiantValidateur extends Validateur {

     @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public List<String> valider(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String nom = attributs[0]; // Nom de l'étudiant
        String prenom = attributs[1]; // Prénom de l'étudiant
        String email = attributs[2]; // Email de l'étudiant
        String password = attributs[3]; // Mot de passe de l'étudiant
        String ine = attributs[4]; // INE de l'étudiant
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

        // Validation de l'INE
        if (ine == null || ine.trim().isEmpty()) {
            erreurs.add("L'INE est obligatoire.");
        }

        // Validation de l'ID du profil
        if (idProfil == null || !ValidationUtils.isValidLong(idProfil)) {
            erreurs.add("L'ID du profil est invalide.");
        }

         Metudiant etudiant=null;
         etudiant=etudiantRepository.findByIne(ine);
        if(etudiant!=null){
            erreurs.add("un etudiant est déja enregistré avec cet INE");
        }

        Metudiant etudiant1=null;
        etudiant1=etudiantRepository.findByEmail(email);
        if(etudiant1!=null){
            erreurs.add("un etudiant possède déja cet mail");
        }
        return erreurs;
    }

    public List<String> validerQuandModifier(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String nom = attributs[0]; // Nom de l'étudiant
        String prenom = attributs[1]; // Prénom de l'étudiant
        String email = attributs[2]; // Email de l'étudiant
        String password = attributs[3]; // Mot de passe de l'étudiant
        String ine = attributs[4]; // INE de l'étudiant
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

        // Validation de l'INE
        if (ine == null || ine.trim().isEmpty()) {
            erreurs.add("L'INE est obligatoire.");
        }

        // Validation de l'ID du profil
        if (idProfil == null || !ValidationUtils.isValidLong(idProfil)) {
            erreurs.add("L'ID du profil est invalide.");
        }
        return erreurs;
    }
}