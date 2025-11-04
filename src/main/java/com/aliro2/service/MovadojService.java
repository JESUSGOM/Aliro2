package com.aliro2.service;

import com.aliro2.model.Movadoj;
import com.aliro2.repository.MovadojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MovadojService {

    private final MovadojRepository movadojRepository;

    // El formato de fecha de tu BD es AAAAMMDD
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    public MovadojService(MovadojRepository movadojRepository) {
        this.movadojRepository = movadojRepository;
    }

    /**
     * MÉTODO CORREGIDO:
     * Ahora este método filtra las visitas activas
     * para que muestre SÓLO las del día actual Y del centro del usuario.
     */
    public Page<Movadoj> findVisitasActivas(String keyword, Integer movCentro, Pageable pageable) {

        // 1. Obtenemos la fecha de hoy y la formateamos como "yyyyMMdd"
        String fechaHoy = LocalDate.now().format(dtfFecha);

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 2. Si hay búsqueda, llama al método de búsqueda CON FECHA y CON CENTRO
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(fechaHoy, movCentro, keyword, keyword, pageable);
        } else {
            // 3. Si no hay búsqueda, llama al método simple CON FECHA y CON CENTRO
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(fechaHoy, movCentro, pageable);
        }
    }

    // --- MÉTODOS CRUD ESTÁNDAR ---

    public List<Movadoj> findAll() {
        return movadojRepository.findAll();
    }

    // Método para buscar todas las visitas de un centro (para informes)
    public List<Movadoj> findAllByCentro(Integer movCentro) {
        // Asumiendo que el informe general debe ser de visitas activas
        return movadojRepository.findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(movCentro);
    }

    public Optional<Movadoj> findById(Integer id) {
        return movadojRepository.findById(id);
    }

    // Servicio para asegurar que solo se editen visitas del centro del usuario
    public Optional<Movadoj> findByIdAndMovCentro(Integer id, Integer movCentro) {
        return movadojRepository.findById(id)
                .filter(movadoj -> movadoj.getMovCentro().equals(movCentro));
    }

    public Movadoj save(Movadoj movadoj) {
        return movadojRepository.save(movadoj);
    }

    public void deleteById(Integer id) {
        movadojRepository.deleteById(id);
    }
}