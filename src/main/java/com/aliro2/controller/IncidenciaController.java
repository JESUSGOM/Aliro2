package com.aliro2.controller;

import com.aliro2.model.Incidencia;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.IncidenciaService;
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
public class IncidenciaController {

    private final IncidenciaService incidenciaService;
    private final UsuarioRepository usuarioRepository;

    // Formateadores para el formato de tu BD (AAAAMMDD y HHMM)
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmm");

    @Autowired
    public IncidenciaController(IncidenciaService incidenciaService, UsuarioRepository usuarioRepository) {
        this.incidenciaService = incidenciaService;
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
     * MUESTRA LA LISTA DE INCIDENCIAS (Paginada y con Búsqueda)
     * Se activa con: "Consultar Incidencias" e "Informes Incidencias"
     */
    @GetMapping({"/incidencias/consultar", "/incidencias/informes"})
    public String mostrarListaIncidencias(Model model, Authentication authentication,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10); // 10 por página

        Page<Incidencia> incidenciasPage = incidenciaService.findByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("incidenciasPage", incidenciasPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = incidenciasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Listado de Incidencias");
        model.addAttribute("view", "vistas-listados/list-incidencias");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA CREAR UNA NUEVA INCIDENCIA
     * Se activa con: "Entrada Incidencias"
     */
    @GetMapping("/incidencias/entrada")
    public String mostrarFormularioNueva(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        Incidencia incidencia = new Incidencia();
        incidencia.setIncCentro(centroUsuario); // Asigna el centro por defecto

        model.addAttribute("incidencia", incidencia);
        model.addAttribute("pageTitle", "Registrar Nueva Incidencia");
        model.addAttribute("view", "vistas-formularios/form-incidencia");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR UNA INCIDENCIA
     * Se activa desde el botón "Editar" en la lista
     */
    @GetMapping("/incidencias/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // Busca la incidencia y se asegura de que pertenezca al centro del usuario
        Incidencia incidencia = incidenciaService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de incidencia inválido o acceso denegado"));

        model.addAttribute("incidencia", incidencia);
        model.addAttribute("pageTitle", "Editar Incidencia");
        model.addAttribute("view", "vistas-formularios/form-incidencia");
        return "layouts/layout";
    }

    /**
     * GUARDA LA INCIDENCIA (Nueva o Editada)
     * Se activa desde la acción POST del formulario
     */
    @PostMapping("/incidencias/guardar")
    public String guardarIncidencia(@ModelAttribute("incidencia") Incidencia incidencia, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        // Seguridad: Asegurarse de que el centro de la incidencia coincide con el del usuario
        if (!incidencia.getIncCentro().equals(centroUsuario)) {
            return "redirect:/incidencias/consultar?error=accesoDenegado";
        }

        // Si es una incidencia nueva (no tiene ID), seteamos fecha, hora y usuario
        if (incidencia.getIncId() == null) {
            incidencia.setIncFecha(LocalDate.now().format(dtfFecha));
            incidencia.setIncHora(LocalTime.now().format(dtfHora));
            incidencia.setIncUsuario(authentication.getName()); // DNI del usuario logueado
        }

        incidenciaService.save(incidencia);
        return "redirect:/incidencias/consultar"; // Vuelve a la lista
    }

    /**
     * ELIMINA UNA INCIDENCIA
     * Se activa desde el botón "Eliminar" en la lista
     */
    @GetMapping("/incidencias/eliminar/{id}")
    public String eliminarIncidencia(@PathVariable("id") Integer id, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);

        // Verificamos que la incidencia exista y sea del centro del usuario antes de borrar
        Incidencia incidencia = incidenciaService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de incidencia inválido o acceso denegado"));

        incidenciaService.deleteById(incidencia.getIncId());

        return "redirect:/incidencias/consultar";
    }
}