package com.example.demo.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "etudiant")
@Data
public class Metudiant extends Mutilisateur {

    @Column(name = "ine", unique = true, nullable = false, length = 20)
    private String ine;

    public Metudiant(String nom, String prenom, String email, String password, String ine, Long idProfil) {
        super(nom, prenom, email, password, idProfil);
        this.ine = ine;
    }

    public Metudiant() {
        
    }
    @OneToMany(mappedBy = "etudiant")
	private List<Mdemande> demandes;
}
