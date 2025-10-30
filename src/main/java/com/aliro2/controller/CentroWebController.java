package com.aliro2.controller; // O el paquete que estés usando

import com.aliro2.model.Centro;
import com.aliro2.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CentroWebController {

    // 1. Inyectamos el servicio directamente. ¡Adiós RestTemplate!
    @Autowired
    private CentroService centroService;

    @GetMapping("/centros")
    public String listarCentros(Model model) {

        // 2. Llamamos al método del servicio directamente para obtener los datos.
        List<Centro> centros = centroService.obtenerTodosLosCentros();

        // 3. Pasamos la lista de entidades al modelo para que Thymeleaf la use.
        model.addAttribute("centros", centros);

        // 4. Devolvemos el nombre de la plantilla HTML.
        return "centros/listados"; // El nombre de tu archivo es listados.html
    }
}