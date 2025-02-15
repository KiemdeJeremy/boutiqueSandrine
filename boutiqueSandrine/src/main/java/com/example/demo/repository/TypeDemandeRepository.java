package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.MtypeDemande;

@Repository
public interface TypeDemandeRepository extends JpaRepository<MtypeDemande, Long> {

}
