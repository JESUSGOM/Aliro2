package com.aliro2.controller;

import com.aliro2.model.KeyMove;
import com.aliro2.model.Llave;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.KeyMoveService;
import com.aliro2.service.LlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime; // <- IMPORTADO
// import java.time.LocalDate;     // <- ELIMINADO
// import java.time.LocalTime;     // <- ELIMINADO
// import java.time.format.DateTimeFormatter; // <- ELIMINADO
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class LlaveController {

    private final LlaveService llaveService;
    private final KeyMoveService keyMoveService;
    private final UsuarioRepository usuarioRepository;

    // --- FORMATEADORES ELIMINADOS ---
    // private final DateTimeFormatter dtfFecha = ...
    // private final DateTimeFormatter dtfHora = ...

    @Autowired
    public LlaveController(LlaveService llaveService, KeyMoveService keyMoveService, UsuarioRepository usuarioRepository) {
        this.llaveService = llaveService;
        this.keyMoveService = keyMoveService;
        this.usuarioRepository = usuarioRepository;
    }

    // Método auxiliar (sin cambios)
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    /**
     * MUESTRA EL FORMULARIO PARA ENTREGAR UNA LLAVE
     * (Sin cambios, la lógica de servicio fue refactorizada)
     */
    @GetMapping("/llaves/entrega")
    public String mostrarFormularioEntrega(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        List<Llave> todasLasLlavesDelCentro = llaveService.findByCentro(centroUsuario);

        // Esta llamada ahora usa el servicio refactorizado (keyFechaHoraRecepcionDt IS NULL)
        List<String> codigosLlavesPrestadas = keyMoveService.findLlavesPrestadasPorCentro(centroUsuario)
                .stream()
                .map(KeyMove::getKeyLlvOrden)
                .collect(Collectors.toList());

        List<Llave> llavesDisponibles = todasLasLlavesDelCentro.stream()
                .filter(llave -> !codigosLlavesPrestadas.contains(llave.getLlvCodigo()))
                .collect(Collectors.toList());
        KeyMove keyMove = new KeyMove();
        keyMove.setKeyCentro(centroUsuario);
        model.addAttribute("keyMove", keyMove);
        model.addAttribute("llavesDisponibles", llavesDisponibles);
        model.addAttribute("pageTitle", "Registrar Entrega de Llave");
        model.addAttribute("view", "vistas-formularios/form-entrega-llave");
        return "layouts/layout";
    }

    /**
     * GUARDA EL MOVIMIENTO DE ENTREGA DE LLAVE
     * (¡ACTUALIZADO!)
     */
    @PostMapping("/llaves/guardar-entrega")
    public String guardarEntregaLlave(@ModelAttribute("keyMove") KeyMove keyMove, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        if (!keyMove.getKeyCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        keyMove.setKeyFechaHoraEntregaDt(LocalDateTime.now());
        // keyFechaHoraRecepcionDt permanece NULL

        keyMoveService.save(keyMove);
        return "redirect:/panel";
    }

    /**
     * MUESTRA LA LISTA DE LLAVES PRESTADAS (PARA RECOGER)
     * (Sin cambios, la lógica de servicio fue refactorizada)
     */
    @GetMapping("/llaves/recogida")
    public String mostrarListaRecogida(Model model, Authentication authentication,
                                       @RequestParam(name = "page", defaultValue = "0") int page) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);

        // Esta llamada ahora usa el servicio refactorizado (filtrado por 'hoy' y '...Dt')
        Page<KeyMove> llavesPage = keyMoveService.findLlavesPrestadasPorCentro(centroUsuario, pageable);

        model.addAttribute("llavesPage", llavesPage);
        model.addAttribute("pageTitle", "Registrar Recogida de Llave");
        int totalPaginas = llavesPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("view", "vistas-listados/list-llaves-recogida");
        return "layouts/layout";
    }

    /**
     * REGISTRA LA RECOGIDA (DEVOLUCIÓN) DE UNA LLAVE
     * (¡ACTUALIZADO!)
     */
    @PostMapping("/llaves/registrar-recogida/{id}")
    public String registrarRecogida(@PathVariable("id") Integer id, Authentication authentication,
                                    @RequestParam(name = "page", defaultValue = "0") int page) {

        Integer centroUsuario = getCentroUsuario(authentication);
        KeyMove keyMove = keyMoveService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de movimiento inválido o acceso denegado:" + id));

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        keyMove.setKeyFechaHoraRecepcionDt(LocalDateTime.now());

        keyMoveService.save(keyMove);
        return "redirect:/llaves/recogida?page=" + page;
    }

    /**
     * MUESTRA EL HISTÓRICO DE MOVIMIENTOS DE LLAVES
     * (Sin cambios)
     */
    @GetMapping("/llaves/informes")
    public String mostrarInformeLlaves(Model model, Authentication authentication,
                                       @RequestParam(name = "page", defaultValue = "0") int page) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);
        Page<KeyMove> llavesPage = keyMoveService.findByCentro(centroUsuario, pageable);

        model.addAttribute("llavesPage", llavesPage);
        model.addAttribute("pageTitle", "Informe Movimientos de Llaves");
        int totalPaginas = llavesPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("view", "vistas-listados/list-llaves-informe");
        return "layouts/layout";
    }
}