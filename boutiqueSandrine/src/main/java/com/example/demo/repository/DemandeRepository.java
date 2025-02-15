package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Mdemande;

@Repository
public interface DemandeRepository extends JpaRepository<Mdemande, Long> {
    List<Mdemande> findByTypeDemande_IdTypeDemande(Long typeDemandeId);
}