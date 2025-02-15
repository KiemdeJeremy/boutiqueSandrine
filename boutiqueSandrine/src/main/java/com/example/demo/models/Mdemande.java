package com.example.demo.models;


import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "demande")
public class Mdemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDemande")
    private Long idDemande;

    @Column(name = "dateDemande", nullable = false)
    private LocalDate dateDemande;

    @Column(name = "objetDemande", nullable = false, length = 100)
    private String objetDemande;

    @Column(name = "contenuDemande", nullable = false)
    private String contenuDemande;

    @ManyToOne
    @JoinColumn(name = "idTypeDemande", nullable = true)
    private MtypeDemande typeDemande;

    @ManyToOne
    @JoinColumn(name = "idEtudiant", nullable = true)
    private Metudiant etudiant;

    public Mdemande() {
        
    }
    public Mdemande(LocalDate dateDemande, String objetDemande, String contenuDemande, MtypeDemande typeDemande,Metudiant etudiant) {
        this.dateDemande = dateDemande;
        this.objetDemande = objetDemande;
        this.contenuDemande = contenuDemande;
        this.typeDemande = typeDemande;
        this.etudiant = etudiant;
    }
}
