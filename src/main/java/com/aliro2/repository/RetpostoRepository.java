package com.aliro2.repository;

import com.aliro2.model.Retposto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetpostoRepository extends JpaRepository<Retposto, Integer> {
}