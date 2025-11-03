package com.aliro2.repository;

import com.aliro2.model.KeyMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyMoveRepository extends JpaRepository<KeyMove, Integer> {
}