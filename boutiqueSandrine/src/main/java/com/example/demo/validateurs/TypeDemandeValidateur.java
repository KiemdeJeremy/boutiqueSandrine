package com.example.demo.validateurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TypeDemandeValidateur extends Validateur {

    @Override
    public List<String> valider(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String libelle = attributs[0]; // Libellé du type de demande
        String typeService = attributs[1]; // Type de service

        // Validation du libellé
        if (libelle == null || libelle.trim().isEmpty()) {
            erreurs.add("Le libellé est obligatoire.");
        }

        // Validation du type de service
        if (typeService == null || typeService.trim().isEmpty()) {
            erreurs.add("Le type de service est obligatoire.");
        }

        return erreurs;
    }
}