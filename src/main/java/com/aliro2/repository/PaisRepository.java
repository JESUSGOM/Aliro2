package com.aliro2.repository;

import com.aliro2.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
}