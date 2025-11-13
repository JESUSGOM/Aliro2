package com.aliro2.repository;

import com.aliro2.model.EnvioEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;

@Repository
public interface EnvioEmailRepository extends JpaRepository<EnvioEmail, Integer> {
    // Métodos de búsqueda útiles (opcionales)

    List<EnvioEmail> findByDestinatario(String destinatario);

    List<EnvioEmail> findByEmisorDni(String emisorDni);

    // Para el informe paginado (ordenado por más reciente primero)
    Page<EnvioEmail> findAllByOrderByFechaDescHoraDesc(Pageable pageable);

}
