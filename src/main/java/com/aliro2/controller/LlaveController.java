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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class LlaveController {

    private final LlaveService llaveService;
    private final KeyMoveService keyMoveService;
    private final UsuarioRepository usuarioRepository;

    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmm");

    @Autowired
    public LlaveController(LlaveService llaveService, KeyMoveService keyMoveService, UsuarioRepository usuarioRepository) {
        this.llaveService = llaveService;
        this.keyMoveService = keyMoveService;
        this.usuarioRepository = usuarioRepository;
    }

    // Método auxiliar para obtener el centro del usuario logueado
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    /**
     * MUESTRA EL FORMULARIO PARA ENTREGAR UNA LLAVE
     * Se activa con el botón: "Entrega de Llaves"
     */
    @GetMapping("/llaves/entrega")
    public String mostrarFormularioEntrega(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // 1. Obtener TODAS las llaves del centro
        List<Llave> todasLasLlavesDelCentro = llaveService.findByCentro(centroUsuario);

        // 2. Obtener los CÓDIGOS de las llaves que ya están PRESTADAS
        List<String> codigosLlavesPrestadas = keyMoveService.findLlavesPrestadasPorCentro(centroUsuario)
                .stream()
                .map(KeyMove::getKeyLlvOrden)
                .collect(Collectors.toList());

        // 3. Filtrar la lista: mostrar solo llaves que NO están en la lista de prestadas
        List<Llave> llavesDisponibles = todasLasLlavesDelCentro.stream()
                .filter(llave -> !codigosLlavesPrestadas.contains(llave.getLlvCodigo()))
                .collect(Collectors.toList());

        KeyMove keyMove = new KeyMove();
        keyMove.setKeyCentro(centroUsuario); // Asignamos el centro

        model.addAttribute("keyMove", keyMove);
        model.addAttribute("llavesDisponibles", llavesDisponibles); // Pasamos solo las disponibles
        model.addAttribute("pageTitle", "Registrar Entrega de Llave");
        model.addAttribute("view", "vistas-formularios/form-entrega-llave");
        return "layouts/layout";
    }

    /**
     * GUARDA EL MOVIMIENTO DE ENTREGA DE LLAVE
     * Se activa desde el formulario form-entrega-llave.html
     */
    @PostMapping("/llaves/guardar-entrega")
    public String guardarEntregaLlave(@ModelAttribute("keyMove") KeyMove keyMove, Authentication authentication) {

        // Seguridad: Asegurarse de que el centro del movimiento es el del usuario
        Integer centroUsuario = getCentroUsuario(authentication);
        if (!keyMove.getKeyCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        keyMove.setKeyFechaEntrega(LocalDate.now().format(dtfFecha));
        keyMove.setKeyHoraEntrega(LocalTime.now().format(dtfHora));

        keyMoveService.save(keyMove);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    /**
     * MUESTRA LA LISTA DE LLAVES PRESTADAS (PARA RECOGER)
     * Se activa con el botón: "Recogida de Llaves"
     */
    @GetMapping("/llaves/recogida")
    public String mostrarListaRecogida(Model model, Authentication authentication,
                                       @RequestParam(name = "page", defaultValue = "0") int page) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);

        // Usamos el método paginado que filtra por centro
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
     * Se activa desde el botón "Recoger" en la lista
     */
    @PostMapping("/llaves/registrar-recogida/{id}")
    public String registrarRecogida(@PathVariable("id") Integer id, Authentication authentication,
                                    @RequestParam(name = "page", defaultValue = "0") int page) {

        Integer centroUsuario = getCentroUsuario(authentication);

        // Verificamos que el movimiento de llave sea de este centro
        KeyMove keyMove = keyMoveService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de movimiento inválido o acceso denegado:" + id));

        keyMove.setKeyFechaRecepcion(LocalDate.now().format(dtfFecha));
        keyMove.setKeyHoraRecepcion(LocalTime.now().format(dtfHora));

        keyMoveService.save(keyMove);
        return "redirect:/llaves/recogida?page=" + page; // Vuelve a la misma página de la lista
    }

    /**
     * MUESTRA EL HISTÓRICO DE MOVIMIENTOS DE LLAVES
     * Se activa con el botón: "Informes de Llaves"
     */
    @GetMapping("/llaves/informes")
    public String mostrarInformeLlaves(Model model, Authentication authentication,
                                       @RequestParam(name = "page", defaultValue = "0") int page) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);

        // Usamos el método paginado que filtra por centro
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