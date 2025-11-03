package com.aliro2.repository;

import com.aliro2.model.EntreTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntreTurnoRepository extends JpaRepository<EntreTurno, Integer> {
}