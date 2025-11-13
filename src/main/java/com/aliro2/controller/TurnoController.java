package com.aliro2.controller;

import com.aliro2.model.EntreTurno;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.EntreTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime; // <- IMPORTADO
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TurnoController {

    private final EntreTurnoService entreTurnoService;
    private final UsuarioRepository usuarioRepository;

    // --- FORMATEADORES ELIMINADOS ---
    // private final DateTimeFormatter dtfFecha = ...
    // private final DateTimeFormatter dtfHora = ...

    @Autowired
    public TurnoController(EntreTurnoService entreTurnoService, UsuarioRepository usuarioRepository) {
        this.entreTurnoService = entreTurnoService;
        this.usuarioRepository = usuarioRepository;
    }

    // --- Métodos Auxiliares (Sin cambios) ---
    private String getDniUsuario(Authentication authentication) {
        return authentication.getName();
    }
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = getDniUsuario(authentication);
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    // (Formulario Comunicar - Sin cambios)
    @GetMapping("/turnos/comunicar")
    public String mostrarFormularioComunicar(Model model, Authentication authentication) {
        EntreTurno turno = new EntreTurno();
        turno.setEntCentro(getCentroUsuario(authentication));
        model.addAttribute("turno", turno);
        model.addAttribute("pageTitle", "Comunicar al Siguiente Turno");
        model.addAttribute("view", "vistas-formularios/form-comunicar-turno");
        return "layouts/layout";
    }

    // (Guardar Comunicación - ¡ACTUALIZADO!)
    @PostMapping("/turnos/guardar-comunicacion")
    public String guardarComunicacion(@ModelAttribute("turno") EntreTurno turno, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        String dniUsuario = getDniUsuario(authentication);

        if (!turno.getEntCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        turno.setEntOperario(dniUsuario);
        turno.setEntFechaHoraEscritoDt(LocalDateTime.now());
        // entUsuario y entFechaHoraLeidoDt quedan NULL

        entreTurnoService.save(turno);
        return "redirect:/panel";
    }

    // (Lista Pendientes - Sin cambios)
    @GetMapping("/turnos/leer")
    public String mostrarListaPendientes(Model model, Authentication authentication,
                                         @RequestParam(name = "page", defaultValue = "0") int page) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);
        Page<EntreTurno> turnosPage = entreTurnoService.findPendientesByCentro(centroUsuario, pageable);
        model.addAttribute("turnosPage", turnosPage);
        model.addAttribute("baseURL", "/turnos/leer");
        int totalPaginas = turnosPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Leer Comunicación del Turno Anterior");
        model.addAttribute("view", "vistas-listados/list-turnos-pendientes");
        return "layouts/layout";
    }

    // (Marcar Leído - ¡ACTUALIZADO!)
    @PostMapping("/turnos/marcar-leido/{id}")
    public String marcarLeido(@PathVariable("id") Integer id, Authentication authentication,
                              @RequestParam(name = "page", defaultValue = "0") int page) {
        Integer centroUsuario = getCentroUsuario(authentication);
        String dniUsuario = getDniUsuario(authentication);
        EntreTurno turno = entreTurnoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de turno inválido o acceso denegado"));

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        turno.setEntUsuario(dniUsuario);
        turno.setEntFechaHoraLeidoDt(LocalDateTime.now());

        entreTurnoService.save(turno);
        return "redirect:/turnos/leer?page=" + page;
    }

    // (Informe - Sin cambios)
    @GetMapping("/turnos/informes")
    public String mostrarInforme(Model model, Authentication authentication,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);
        Page<EntreTurno> turnosPage = entreTurnoService.findAllByCentro(centroUsuario, keyword, pageable);
        model.addAttribute("turnosPage", turnosPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/turnos/informes");
        int totalPaginas = turnosPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Informe Histórico Entre Turnos");
        model.addAttribute("view", "vistas-listados/list-turnos-informe");
        return "layouts/layout";
    }
}