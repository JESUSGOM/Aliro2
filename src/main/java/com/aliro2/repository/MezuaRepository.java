package com.aliro2.repository;

import com.aliro2.model.Mezua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MezuaRepository extends JpaRepository<Mezua, Integer> {
}