package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Mrapport;

@Repository
public interface RapportRepository extends JpaRepository<Mrapport, Long> {
    List<Mrapport> findByPersonnel_IdUtilisateur(Long personnelId);
}