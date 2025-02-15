package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Mpersonnel;


@Repository
public interface PersonnelRepository extends JpaRepository<Mpersonnel, Long> {
    Mpersonnel findByCipPersonnel(String cipPersonnel);
    Mpersonnel findByEmail(String email);
}
