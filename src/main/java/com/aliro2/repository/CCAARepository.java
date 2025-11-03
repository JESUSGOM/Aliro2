package com.aliro2.repository;

import com.aliro2.model.CCAA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CCAARepository extends JpaRepository<CCAA, Integer> {
}