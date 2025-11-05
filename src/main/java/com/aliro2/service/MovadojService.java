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
     * Busca visitas activas SÓLO DE HOY y POR CENTRO.
     * (Usado para la pantalla /visitas/salida)
     */
    public Page<Movadoj> findVisitasActivas(String keyword, Integer movCentro, Pageable pageable) {

        String fechaHoy = LocalDate.now().format(dtfFecha);

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Llama al método de búsqueda CON FECHA y CON CENTRO
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(fechaHoy, movCentro, keyword, keyword, pageable);
        } else {
            // Llama al método simple CON FECHA y CON CENTRO
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(fechaHoy, movCentro, pageable);
        }
    }

    /**
     * Busca TODAS las visitas activas (de cualquier fecha) y POR CENTRO.
     * (Usado para la pantalla /visitas/informes)
     */
    public Page<Movadoj> findTodasVisitasActivasPorCentro(String keyword, Integer movCentro, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return movadojRepository.findByMovFechaSalidaIsNullAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(movCentro, keyword, keyword, pageable);
        } else {
            return movadojRepository.findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(movCentro, pageable);
        }
    }

    // --- MÉTODOS CRUD ESTÁNDAR ---

    // Este método ya no es seguro porque no filtra por centro.
    // Lo reemplazamos por 'findAllByCentro'.
    // public List<Movadoj> findAll() { ... }

    // Método para buscar todas las visitas de un centro (para informes)
    public List<Movadoj> findAllByCentro(Integer movCentro) {
        return movadojRepository.findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(movCentro);
    }

    public Optional<Movadoj> findById(Integer id) {
        return movadojRepository.findById(id);
    }

    // (Seguridad) Busca solo si el ID pertenece al centro del usuario
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