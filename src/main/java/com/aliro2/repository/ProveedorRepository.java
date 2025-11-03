package com.aliro2.repository;

import com.aliro2.model.Proveedor;
import com.aliro2.model.ProveedorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, ProveedorId> {
}