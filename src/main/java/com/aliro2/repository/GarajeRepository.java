package com.aliro2.repository;

import com.aliro2.model.Garaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarajeRepository extends JpaRepository<Garaje, Integer> {
}