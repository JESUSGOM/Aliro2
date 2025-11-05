package com.aliro2.controller;

import com.aliro2.model.EmpleadosProveedores;
import com.aliro2.model.MovimientoEmpleado;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.EmpleadosProveedoresService;
import com.aliro2.service.MovimientoEmpleadoService;
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

import java.time.LocalDateTime; // Importamos LocalDateTime
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MovimientoEmpleadoController {

    private final MovimientoEmpleadoService movimientoService;
    private final EmpleadosProveedoresService empleadoService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MovimientoEmpleadoController(MovimientoEmpleadoService movimientoService,
                                        EmpleadosProveedoresService empleadoService,
                                        UsuarioRepository usuarioRepository) {
        this.movimientoService = movimientoService;
        this.empleadoService = empleadoService;
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
     * MUESTRA EL FORMULARIO DE NUEVA ENTRADA
     * Se activa con: "Entrada Empleados de Proveedores"
     */
    @GetMapping("/movimientos-empleados/entrada")
    public String mostrarFormularioEntrada(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        MovimientoEmpleado movimiento = new MovimientoEmpleado();
        movimiento.setMovCentro(centroUsuario);

        // Obtiene la lista de empleados de ESE centro para el <select>
        List<EmpleadosProveedores> empleados = empleadoService.findByCentro(centroUsuario);

        model.addAttribute("movimiento", movimiento);
        model.addAttribute("empleados", empleados); // Pasa la lista de empleados
        model.addAttribute("pageTitle", "Registrar Entrada de Empleado de Proveedor");
        model.addAttribute("view", "vistas-formularios/form-mov-empleado-entrada");
        return "layouts/layout";
    }

    /**
     * GUARDA LA NUEVA ENTRADA
     * Se activa desde el formulario form-mov-empleado-entrada.html
     */
    @PostMapping("/movimientos-empleados/guardar-entrada")
    public String guardarEntrada(@ModelAttribute("movimiento") MovimientoEmpleado movimiento, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        // Seguridad: Asegurarse de que el centro coincide
        if (!movimiento.getMovCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // Obtenemos el empleado seleccionado (por NIF) para coger su CIF
        // El NIF viene del th:field="*{movEmpNif}" del formulario
        EmpleadosProveedores empleado = (EmpleadosProveedores) empleadoService.findByNifAndCentro(movimiento.getMovEmpNif(), centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no válido"));

        movimiento.setMovPrdCif(empleado.getEmpPrdCif()); // Asignamos el CIF del proveedor
        movimiento.setMovFechaHoraEntrada(LocalDateTime.now());

        movimientoService.save(movimiento);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    /**
     * MUESTRA LA LISTA DE MOVIMIENTOS ACTIVOS (PARA DAR SALIDA)
     * Se activa con: "Salida Empleados de Proveedores"
     */
    @GetMapping("/movimientos-empleados/salida")
    public String mostrarListaSalida(Model model, Authentication authentication,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        Page<MovimientoEmpleado> movimientosPage = movimientoService.findActivosByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("movimientosPage", movimientosPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = movimientosPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Registrar Salida de Empleado");
        model.addAttribute("view", "vistas-listados/list-mov-empleados-salida");
        return "layouts/layout";
    }

    /**
     * REGISTRA LA SALIDA (ACTUALIZA)
     * Se activa desde el botón "Registrar Salida" en la lista
     */
    @PostMapping("/movimientos-empleados/registrar-salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id, Authentication authentication,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);

        // Seguridad: Busca el movimiento y valida que pertenezca al centro del usuario
        MovimientoEmpleado movimiento = movimientoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de movimiento inválido o acceso denegado"));

        movimiento.setMovFechaHoraSalida(LocalDateTime.now());
        movimientoService.save(movimiento);

        String redirectUrl = "/movimientos-empleados/salida?page=" + page;
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * MUESTRA EL INFORME DE TODOS LOS MOVIMIENTOS
     * Se activa con: "Consultar Movimientos" y "Eliminar Movimientos"
     */
    @GetMapping({"/movimientos-empleados/consultar", "/movimientos-empleados/eliminar"})
    public String mostrarInforme(Model model, Authentication authentication,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        Page<MovimientoEmpleado> movimientosPage = movimientoService.findAllByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("movimientosPage", movimientosPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = movimientosPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Informe de Movimientos de Empleados");
        model.addAttribute("view", "vistas-listados/list-mov-empleados-informe");
        return "layouts/layout";
    }

    /**
     * ELIMINA UN MOVIMIENTO (Lógica para el botón "Eliminar")
     */
    @GetMapping("/movimientos-empleados/eliminar/{id}")
    public String eliminarMovimiento(@PathVariable("id") Integer id, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);

        // Seguridad: Valida que el registro pertenezca al centro del usuario
        MovimientoEmpleado movimiento = movimientoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de movimiento inválido o acceso denegado"));


        movimientoService.deleteById(movimiento.getMovId());

        return "redirect:/movimientos-empleados/consultar";
    }
}