package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Mutilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_seq")
    @SequenceGenerator(name = "utilisateur_seq", sequenceName = "utilisateur_sequence", allocationSize = 1)
    private Long idUtilisateur;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "idProfil", nullable = true)
    private Mprofil profil;

    // Constructeur avec tous les champs sauf ID
    protected Mutilisateur(String nom, String prenom, String email, String password, Long idProfil) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        if (idProfil != null) {
            this.profil = new Mprofil();
            this.profil.setIdProfil(idProfil);
        }
    }

    protected Mutilisateur() {
        
    }

}