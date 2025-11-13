package com.aliro2.controller;

import com.aliro2.model.Mezua;
import com.aliro2.service.MezuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
// Solo Superusuarios (Y, Z) pueden gestionar esta agenda global
@PreAuthorize("hasAnyRole('ROLE_Y', 'ROLE_Z')")
@RequestMapping("/mezua") // Todas las URLs de este controlador empezarán con /mezua
public class MezuaController {

    private final MezuaService mezuaService;

    @Autowired
    public MezuaController(MezuaService mezuaService) {
        this.mezuaService = mezuaService;
    }

    /**
     * MUESTRA LA LISTA DE CONTACTOS DE LA AGENDA
     * URL: /mezua/listado
     */
    @GetMapping("/listado")
    public String mostrarListado(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, 10); // 10 por página

        Page<Mezua> mezuaPage = mezuaService.findAll(keyword, pageable);
        model.addAttribute("mezuaPage", mezuaPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/mezua/listado");

        int totalPaginas = mezuaPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Gestión de Agenda Global (Mezua)");
        model.addAttribute("view", "vistas-listados/list-mezua");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA UN NUEVO CONTACTO
     * URL: /mezua/nuevo
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("mezua", new Mezua());
        model.addAttribute("pageTitle", "Nuevo Contacto de Agenda");
        model.addAttribute("isEditMode", false);
        model.addAttribute("view", "vistas-formularios/form-mezua");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR UN CONTACTO
     * URL: /mezua/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Mezua mezua = mezuaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de contacto inválido: " + id));

        model.addAttribute("mezua", mezua);
        model.addAttribute("pageTitle", "Editar Contacto de Agenda");
        model.addAttribute("isEditMode", true);
        model.addAttribute("view", "vistas-formularios/form-mezua");
        return "layouts/layout";
    }

    /**
     * GUARDA EL CONTACTO (NUEVO O EDITADO)
     * URL: /mezua/guardar
     */
    @PostMapping("/guardar")
    public String guardarMezua(@ModelAttribute("mezua") Mezua mezua) {
        mezuaService.save(mezua);
        return "redirect:/mezua/listado";
    }

    /**
     * ELIMINA UN CONTACTO
     * URL: /mezua/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarMezua(@PathVariable("id") Integer id) {
        // Verificamos que existe antes de borrar
        Mezua mezua = mezuaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de contacto inválido: " + id));

        mezuaService.deleteById(mezua.getMezId());
        return "redirect:/mezua/listado";
    }
}