package com.example.demo.validateurs;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DemandeValidateur extends Validateur {

    @Override
    public List<String> valider(String... attributs) {
        List <String>erreurs = new ArrayList<>();

        // Récupération des attributs
        String dateDemande = attributs[0]; // Date de la demande
        String objetDemande = attributs[1]; // Objet de la demande
        String contenuDemande = attributs[2]; // Contenu de la demande
        String idEtudiant = attributs[3]; // ID de l'étudiant
        String idTypeDemande = attributs[4]; // ID du type de demande

        // Validation de la date de la demande
        if (dateDemande == null || !ValidationUtils.isValidDate(dateDemande)) {
            erreurs.add("La date de la demande est invalide.");
        }

        // Validation de l'objet de la demande
        if (objetDemande == null || objetDemande.trim().isEmpty()) {
            erreurs.add("L'objet de la demande est obligatoire.");
        }

        // Validation du contenu de la demande
        if (contenuDemande == null || contenuDemande.trim().isEmpty()) {
            erreurs.add("Le contenu de la demande est obligatoire.");
        }

        // Validation de l'ID de l'étudiant
        if (idEtudiant == null || !ValidationUtils.isValidLong(idEtudiant)) {
            erreurs.add("L'ID de l'étudiant est invalide.");
        }

        // Validation de l'ID du type de demande
        if (idTypeDemande == null || !ValidationUtils.isValidLong(idTypeDemande)) {
            erreurs.add("L'ID du type de demande est invalide.");
        }

        return erreurs;
    }
}