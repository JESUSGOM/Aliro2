package com.aliro2.repository;

import com.aliro2.model.Llave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Integer> {
}