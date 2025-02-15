package com.example.demo.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "personnel")
@Data
public class Mpersonnel extends Mutilisateur {

    @Column(name = "cipPersonnel", unique = true, nullable = false, length = 20)
    private String cipPersonnel;

    public Mpersonnel() {
        
    }

    
    public Mpersonnel(String nom, String prenom, String email, String password, String cipPersonnel, Long idProfil) {
        super(nom, prenom, email, password, idProfil);
        this.cipPersonnel = cipPersonnel;
    }

    @OneToMany(mappedBy = "personnel")
    private List<Mrapport> rapports;
}
