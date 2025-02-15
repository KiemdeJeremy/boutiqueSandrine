package com.example.demo.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "typeDemande")
public class MtypeDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTypeDemande")
    private Long idTypeDemande;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @Column(name = "typeService", nullable = false, length = 100)
    private String typeService;

    @OneToMany(mappedBy = "typeDemande")
    private List<Mdemande> demandes;

}
