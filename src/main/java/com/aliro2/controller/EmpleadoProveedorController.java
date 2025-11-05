package com.aliro2.controller;

import com.aliro2.model.EmpleadosProveedores;
import com.aliro2.model.Proveedor;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.EmpleadosProveedoresService;
import com.aliro2.service.ProveedorService; // <- Importante: se necesita el servicio de Proveedores
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class EmpleadoProveedorController {

    private final EmpleadosProveedoresService empleadoService;
    private final ProveedorService proveedorService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EmpleadoProveedorController(EmpleadosProveedoresService empleadoService, ProveedorService proveedorService, UsuarioRepository usuarioRepository) {
        this.empleadoService = empleadoService;
        this.proveedorService = proveedorService;
        this.usuarioRepository = usuarioRepository;
    }

    // Método auxiliar para obtener el centro del usuario logueado
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    // Método auxiliar para cargar la lista de proveedores del centro al modelo
    private void cargarProveedoresDelCentro(Model model, Integer centroUsuario) {
        List<Proveedor> proveedores = proveedorService.findByCentro(centroUsuario);
        model.addAttribute("proveedores", proveedores);
    }

    /**
     * MUESTRA LA LISTA DE EMPLEADOS DE PROVEEDORES (PAGINADA Y CON BÚSQUEDA)
     * Se activa con: "Consultar", "Modificar" y "Eliminar" Empleado
     */
    @GetMapping({"/empleados-proveedor/consultar", "/empleados-proveedor/modificar", "/empleados-proveedor/eliminar"})
    public String mostrarListaEmpleados(Model model, Authentication authentication,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10); // 10 por página

        Page<EmpleadosProveedores> empleadosPage = empleadoService.findByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("empleadosPage", empleadosPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = empleadosPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Listado de Empleados de Proveedores");
        model.addAttribute("view", "vistas-listados/list-empleados-proveedor");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA CREAR UN NUEVO EMPLEADO
     * Se activa con: "Crear Empleado en Proveedor"
     */
    @GetMapping("/empleados-proveedor/crear")
    public String mostrarFormularioNuevo(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        EmpleadosProveedores empleado = new EmpleadosProveedores();
        empleado.setEmpCentro(centroUsuario); // Asigna el centro por defecto

        model.addAttribute("empleado", empleado);
        model.addAttribute("pageTitle", "Crear Nuevo Empleado de Proveedor");
        model.addAttribute("isEditMode", false);

        // Carga la lista de proveedores para el <select>
        cargarProveedoresDelCentro(model, centroUsuario);

        model.addAttribute("view", "vistas-formularios/form-empleado-proveedor");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR UN EMPLEADO
     * Se activa desde el botón "Editar" en la lista
     */
    @GetMapping("/empleados-proveedor/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // Busca al empleado y se asegura de que pertenezca al centro del usuario
        EmpleadosProveedores empleado = empleadoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de empleado inválido o acceso denegado"));

        model.addAttribute("empleado", empleado);
        model.addAttribute("pageTitle", "Editar Empleado de Proveedor");
        model.addAttribute("isEditMode", true);

        // Carga la lista de proveedores para el <select>
        cargarProveedoresDelCentro(model, centroUsuario);

        model.addAttribute("view", "vistas-formularios/form-empleado-proveedor");
        return "layouts/layout";
    }

    /**
     * GUARDA EL EMPLEADO (Nuevo o Editado)
     * Se activa desde la acción POST del formulario
     */
    @PostMapping("/empleados-proveedor/guardar")
    public String guardarEmpleado(@ModelAttribute("empleado") EmpleadosProveedores empleado, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        // Seguridad: Asegurarse de que el centro del empleado coincide con el del usuario
        if (!empleado.getEmpCentro().equals(centroUsuario)) {
            return "redirect:/empleados-proveedor/consultar?error=accesoDenegado";
        }

        // Lógica para formatear fechas si es necesario (asumiendo que el input de HTML es yyyy-MM-dd)
        // Si usas <input type="date">, Spring lo convierte a LocalDate automáticamente.

        empleadoService.save(empleado);
        return "redirect:/empleados-proveedor/consultar"; // Vuelve a la lista
    }

    /**
     * ELIMINA UN EMPLEADO
     * Se activa desde el botón "Eliminar" en la lista
     */
    @GetMapping("/empleados-proveedor/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable("id") Integer id, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);

        // Primero verificamos que el empleado exista y sea del centro del usuario
        EmpleadosProveedores empleado = empleadoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de empleado inválido o acceso denegado"));

        // Si existe y es del centro, lo eliminamos
        empleadoService.deleteById(empleado.getEmpId());

        return "redirect:/empleados-proveedor/consultar";
    }
}