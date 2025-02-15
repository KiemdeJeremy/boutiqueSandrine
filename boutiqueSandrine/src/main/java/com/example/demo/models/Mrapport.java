package com.example.demo.models;

import java.security.Timestamp;

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
@Table(name = "rapport")
public class Mrapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRapport")
    private Long idRapport;

    @Column(name = "etat", nullable = false, length = 100)
    private String etat;

    @Column(name = "dateRapport", nullable = false)
    private Timestamp dateRapport;

    @Column(name = "contenuRapport", nullable = false)
    private String contenuRapport;

    @ManyToOne
    @JoinColumn(name = "idPersonnel", nullable = true)
    private Mpersonnel personnel;
}
