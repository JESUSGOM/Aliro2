package com.aliro2.controller;

import com.aliro2.model.Proveedor;
import com.aliro2.model.ProveedorId;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.ProveedorService;
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
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ProveedorController(ProveedorService proveedorService, UsuarioRepository usuarioRepository) {
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

    /**
     * MUESTRA LA LISTA DE PROVEEDORES (PAGINADA Y CON BÚSQUEDA)
     * Se activa con: "Consultar Proveedores" y "Modificar Proveedor"
     */
    @GetMapping({"/proveedores/consultar", "/proveedores/modificar"})
    public String mostrarListaProveedores(Model model, Authentication authentication,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10); // 10 por página

        Page<Proveedor> proveedoresPage = proveedorService.findByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("proveedoresPage", proveedoresPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = proveedoresPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Listado de Proveedores");
        model.addAttribute("view", "vistas-listados/list-proveedores");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA CREAR UN NUEVO PROVEEDOR
     * Se activa con: "Crear Proveedores"
     */
    @GetMapping("/proveedores/crear")
    public String mostrarFormularioNuevo(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // Preparamos el objeto Proveedor con su clave compuesta
        ProveedorId newId = new ProveedorId();
        newId.setPrdCentro(centroUsuario); // Asignamos el centro

        Proveedor proveedor = new Proveedor();
        proveedor.setId(newId); // Asignamos la clave compuesta (CIF estará vacío)

        model.addAttribute("proveedor", proveedor);
        model.addAttribute("pageTitle", "Crear Nuevo Proveedor");
        model.addAttribute("isEditMode", false); // Para desbloquear el campo CIF
        model.addAttribute("view", "vistas-formularios/form-proveedor");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR UN PROVEEDOR
     * Se activa desde el botón "Editar" en la lista
     */
    @GetMapping("/proveedores/editar/{cif}/{centro}")
    public String mostrarFormularioEditar(@PathVariable("cif") String cif,
                                          @PathVariable("centro") Integer centro, Model model, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        // Seguridad: Comprobamos que el centro del proveedor a editar sea el del usuario
        if (!centro.equals(centroUsuario)) {
            return "redirect:/proveedores/consultar?error=accesoDenegado";
        }

        ProveedorId id = new ProveedorId(cif, centro);
        Proveedor proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de proveedor inválido"));

        model.addAttribute("proveedor", proveedor);
        model.addAttribute("pageTitle", "Editar Proveedor");
        model.addAttribute("isEditMode", true); // Para bloquear los campos de la clave (CIF y Centro)
        model.addAttribute("view", "vistas-formularios/form-proveedor");
        return "layouts/layout";
    }

    /**
     * GUARDA EL PROVEEDOR (Nuevo o Editado)
     * Se activa desde la acción POST del formulario
     */
    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(@ModelAttribute("proveedor") Proveedor proveedor, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        // Seguridad: Asegurarse de que el centro del proveedor coincide con el del usuario
        if (!proveedor.getId().getPrdCentro().equals(centroUsuario)) {
            return "redirect:/proveedores/consultar?error=accesoDenegado";
        }

        proveedorService.save(proveedor);
        return "redirect:/proveedores/consultar"; // Vuelve a la lista
    }

    /**
     * ELIMINA UN PROVEEDOR
     * Se activa desde el botón "Eliminar" en la lista
     */
    @GetMapping("/proveedores/eliminar/{cif}/{centro}")
    public String eliminarProveedor(@PathVariable("cif") String cif,
                                    @PathVariable("centro") Integer centro, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        if (!centro.equals(centroUsuario)) {
            return "redirect:/proveedores/consultar?error=accesoDenegado";
        }

        ProveedorId id = new ProveedorId(cif, centro);

        // TODO: Añadir lógica para verificar que no tenga EmpleadosProveedores asociados antes de borrar

        proveedorService.deleteById(id);
        return "redirect:/proveedores/consultar";
    }
}