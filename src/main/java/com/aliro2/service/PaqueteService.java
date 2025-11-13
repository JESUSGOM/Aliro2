package com.aliro2.service;

import com.aliro2.model.Paquete;
import com.aliro2.repository.PaqueteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService {

    private final PaqueteRepository paqueteRepository;

    // Constante para el estado pendiente
    private static final String ESTADO_PENDIENTE = "No";

    @Autowired
    public PaqueteService(PaqueteRepository paqueteRepository) {
        this.paqueteRepository = paqueteRepository;
    }

    /**
     * Busca TODOS los paquetes por centro, con paginación y búsqueda opcional.
     * (Para "Informes")
     */
    public Page<Paquete> findAllByCentro(Integer pktCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return paqueteRepository.findByPktCentroEqualsAndPktEmisorContainingIgnoreCaseOrPktDestinatarioContainingIgnoreCaseOrderByPktIdDesc(
                    pktCentro, keyword, keyword, pageable);
        } else {
            return paqueteRepository.findByPktCentroEqualsOrderByPktIdDesc(pktCentro, pageable);
        }
    }

    /**
     * Busca paquetes PENDIENTES por centro, con paginación y búsqueda opcional.
     * (Para "Entregar correspondencia")
     */
    public Page<Paquete> findPendientesByCentro(Integer pktCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return paqueteRepository.findByPktCentroEqualsAndPktComunicadoEqualsAndPktEmisorContainingIgnoreCaseOrPktDestinatarioContainingIgnoreCaseOrderByPktIdDesc(
                    pktCentro, ESTADO_PENDIENTE, keyword, keyword, pageable);
        } else {
            return paqueteRepository.findByPktCentroEqualsAndPktComunicadoEqualsOrderByPktIdDesc(pktCentro, ESTADO_PENDIENTE, pageable);
        }
    }

    /**
     * Busca un paquete por ID y Centro (para validación de seguridad).
     */
    public Optional<Paquete> findByIdAndCentro(Integer pktId, Integer pktCentro) {
        return paqueteRepository.findByPktIdAndPktCentroEquals(pktId, pktCentro);
    }

    // --- Métodos CRUD Estándar ---

    public Paquete save(Paquete paquete) {
        return paqueteRepository.save(paquete);
    }

    public Optional<Paquete> findById(Integer id) {
        return paqueteRepository.findById(id);
    }

    public List<Paquete> findAll() {
        return paqueteRepository.findAll();
    }

    public void deleteById(Integer id) {
        paqueteRepository.deleteById(id);
    }
}