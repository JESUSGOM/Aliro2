package com.aliro2.repository;

import com.aliro2.model.AperturaExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AperturaExtraRepository extends JpaRepository<AperturaExtra, Integer> {
}