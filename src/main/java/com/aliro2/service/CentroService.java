package com.aliro2.service;

import com.aliro2.model.Centro;
import com.aliro2.repository.CentroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * La anotación @Service marca esta clase como un componente de lógica de negocio.
 * Es el "cerebro" que coordina las operaciones.
 */
@Service
public class CentroService {

    // Inyectamos el repositorio para poder acceder a la base de datos
    @Autowired
    private CentroRepository centroRepository;

    /**
     * Obtiene todos los centros de la base de datos.
     * En el futuro, aquí podríamos añadir lógica como filtrar resultados, etc.
     * @return una lista de todos los centros.
     */
    public List<Centro> obtenerTodosLosCentros() {
        return centroRepository.findAll();
    }

    /**
     * Obtiene un centro específico por su ID.
     * @param id El ID del centro a buscar.
     * @return un Optional que contiene el centro si se encuentra.
     */
    public Optional<Centro> obtenerCentroPorId(Integer id) {
        return centroRepository.findById(id);
    }
}