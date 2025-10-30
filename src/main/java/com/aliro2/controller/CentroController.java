package com.aliro2.controller;

import com.aliro2.model.Centro;
import com.aliro2.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CentroController {

    // ¡CAMBIO! Ahora inyectamos nuestro servicio
    @Autowired
    private CentroService centroService;

    @GetMapping
    public List<Centro> getAllCentros() {
        // ¡CAMBIO! El controlador ahora llama al servicio
        return centroService.obtenerTodosLosCentros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centro> getCentroById(@PathVariable Integer id) {
        // ¡CAMBIO! El controlador ahora llama al servicio
        return centroService.obtenerCentroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
