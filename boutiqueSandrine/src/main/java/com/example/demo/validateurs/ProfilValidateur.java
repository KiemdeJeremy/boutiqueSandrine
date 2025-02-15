package com.example.demo.validateurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProfilValidateur extends Validateur {

    @Override
    public List<String> valider(String... attributs) {
        List<String> erreurs = new ArrayList<>();

        // Récupération des attributs
        String libelle = attributs[0]; // Libellé du profil

        // Validation du libellé
        if (libelle == null || libelle.trim().isEmpty()) {
            erreurs.add("Le libellé du profil est obligatoire.");
        }

        return erreurs;
    }
}