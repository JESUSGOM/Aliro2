package com.aliro2.controller;

import com.aliro2.model.KeyMove;
import com.aliro2.model.Llave;
import com.aliro2.service.KeyMoveService;
import com.aliro2.service.LlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class LlaveController {

    // --- INICIO DE LA MODIFICACIÓN (Inyección por Constructor) ---

    // 1. Declara los servicios como 'final' para asegurar que se inicialicen.
    private final LlaveService llaveService;
    private final KeyMoveService keyMoveService;

    // 2. Crea un constructor que reciba los servicios.
    // Spring usará @Autowired aquí automáticamente para "inyectar"
    // las instancias de LlaveService y KeyMoveService.
    @Autowired
    public LlaveController(LlaveService llaveService, KeyMoveService keyMoveService) {
        this.llaveService = llaveService;
        this.keyMoveService = keyMoveService;
    }

    // --- FIN DE LA MODIFICACIÓN ---


    // Define los formateadores de fecha/hora una sola vez
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * MUESTRA EL FORMULARIO PARA ENTREGAR UNA LLAVE
     * Se activa con el botón: "Entrega de Llaves"
     */
    @GetMapping("/llaves/entrega")
    public String mostrarFormularioEntrega(Model model) {
        // 1. Necesitamos la lista de llaves (la entidad Llave) para el <select>
        //    (Idealmente, aquí filtrarías solo las llaves que no están ya prestadas)
        List<Llave> llavesDisponibles = llaveService.findAll();

        // 2. Pasamos un objeto 'KeyMove' vacío para el formulario
        model.addAttribute("keyMove", new KeyMove());
        model.addAttribute("llavesDisponibles", llavesDisponibles);
        model.addAttribute("pageTitle", "Registrar Entrega de Llave");
        model.addAttribute("view", "vistas-formularios/form-entrega-llave");
        return "layouts/layout";
    }

    /**
     * GUARDA EL MOVIMIENTO DE ENTREGA DE LLAVE
     * Se activa desde el formulario form-entrega-llave.html
     */
    @PostMapping("/llaves/guardar-entrega")
    public String guardarEntregaLlave(@ModelAttribute("keyMove") KeyMove keyMove) {

        keyMove.setKeyFechaEntrega(LocalDate.now().format(dtfFecha));
        keyMove.setKeyHoraEntrega(LocalTime.now().format(dtfHora));

        // El campo 'keyLlvOrden' se rellena desde el formulario (es el LlvCodigo)

        keyMoveService.save(keyMove);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    /**
     * MUESTRA LA LISTA DE LLAVES PRESTADAS (PARA RECOGER)
     * Se activa con el botón: "Recogida de Llaves"
     */
    @GetMapping("/llaves/recogida")
    public String mostrarListaRecogida(Model model) {
        // Usamos el método personalizado para encontrar solo llaves sin fecha de recepción
        List<KeyMove> llavesPrestadas = keyMoveService.findLlavesPrestadas();

        model.addAttribute("llavesPrestadas", llavesPrestadas);
        model.addAttribute("pageTitle", "Registrar Recogida de Llave");
        model.addAttribute("view", "vistas-listados/list-llaves-recogida");
        return "layouts/layout";
    }

    /**
     * REGISTRA LA RECOGIDA (DEVOLUCIÓN) DE UNA LLAVE
     * Se activa desde el botón "Recoger" en la lista
     */
    @PostMapping("/llaves/registrar-recogida/{id}")
    public String registrarRecogida(@PathVariable("id") Integer id) {
        // Busca el movimiento de llave específico
        KeyMove keyMove = keyMoveService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de movimiento inválido:" + id));

        // Setea la fecha y hora de recepción
        keyMove.setKeyFechaRecepcion(LocalDate.now().format(dtfFecha));
        keyMove.setKeyHoraRecepcion(LocalTime.now().format(dtfHora));

        keyMoveService.save(keyMove); // Guarda la actualización
        return "redirect:/llaves/recogida"; // Recarga la lista de llaves pendientes
    }

    /**
     * MUESTRA EL HISTÓRICO DE MOVIMIENTOS DE LLAVES
     * Se activa con el botón: "Informes de Llaves"
     */
    @GetMapping("/llaves/informes")
    public String mostrarInformeLlaves(Model model) {
        List<KeyMove> movimientos = keyMoveService.findAll(); // Obtiene todos los movimientos

        model.addAttribute("movimientos", movimientos);
        model.addAttribute("pageTitle", "Informe Movimientos de Llaves");
        model.addAttribute("view", "vistas-listados/list-llaves-informe");
        return "layouts/layout";
    }
}