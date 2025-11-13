package com.aliro2.controller;

import com.aliro2.model.Garaje;
import com.aliro2.service.GarajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate; // <- IMPORTADO
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GarajeController {

    private final GarajeService garajeService;

    // --- FORMATEADOR ELIMINADO ---
    // private final DateTimeFormatter dtfFecha = ...

    @Autowired
    public GarajeController(GarajeService garajeService) {
        this.garajeService = garajeService;
    }

    // (Formulario Entrada - Sin cambios)
    @GetMapping("/garaje/entrada")
    public String mostrarFormularioEntrada(Model model) {
        model.addAttribute("garaje", new Garaje());
        model.addAttribute("pageTitle", "Registrar Entrada de Vehículo al Garaje");
        model.addAttribute("view", "vistas-formularios/form-garaje-entrada");
        return "layouts/layout";
    }

    // (Guardar Entrada - ¡ACTUALIZADO!)
    @PostMapping("/garaje/guardar")
    public String guardarEntrada(@ModelAttribute("garaje") Garaje garaje) {

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        garaje.setGrjFechaDt(LocalDate.now());

        garajeService.save(garaje);
        return "redirect:/panel";
    }

    // (Informe - Sin cambios)
    @GetMapping("/garaje/informes")
    public String mostrarInforme(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Garaje> garajePage = garajeService.findAll(keyword, pageable);
        model.addAttribute("garajePage", garajePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/garaje/informes");
        int totalPaginas = garajePage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Informe Histórico de Garaje");
        model.addAttribute("view", "vistas-listados/list-garaje-informe");
        return "layouts/layout";
    }
}