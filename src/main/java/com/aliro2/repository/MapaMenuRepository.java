package com.aliro2.repository;

import com.aliro2.model.MapaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapaMenuRepository extends JpaRepository<MapaMenu, Integer> {
}
