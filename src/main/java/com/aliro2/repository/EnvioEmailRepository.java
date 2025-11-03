package com.aliro2.repository;

import com.aliro2.model.EnvioEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioEmailRepository extends JpaRepository<EnvioEmail, Integer> {
}