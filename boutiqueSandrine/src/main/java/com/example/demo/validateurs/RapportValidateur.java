package com.example.demo.validateurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RapportValidateur extends Validateur {

    @Override
    public List<String> valider(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String etat = attributs[0]; // État du rapport
        String dateRapport = attributs[1]; // Date du rapport
        String contenuRapport = attributs[2]; // Contenu du rapport
        String idPersonnel = attributs[3]; // ID du personnel

        // Validation de l'état
        if (etat == null || etat.trim().isEmpty()) {
            erreurs.add("L'état du rapport est obligatoire.");
        }

        // Validation de la date du rapport
        if (dateRapport == null || !ValidationUtils.isValidDate(dateRapport)) {
            erreurs.add("La date du rapport est invalide.");
        }

        // Validation du contenu du rapport
        if (contenuRapport == null || contenuRapport.trim().isEmpty()) {
            erreurs.add("Le contenu du rapport est obligatoire.");
        }

        // Validation de l'ID du personnel
        if (idPersonnel == null || !ValidationUtils.isValidLong(idPersonnel)) {
            erreurs.add("L'ID du personnel est invalide.");
        }

        return erreurs;
    }
}