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

    @Autowired
    private LlaveService llaveService;

    @Autowired
    private KeyMoveService keyMoveService;

    /**
     * MUESTRA EL FORMULARIO PARA ENTREGAR UNA LLAVE
     * Se activa con el botón: "Entrega de Llaves"
     */
    @GetMapping("/llaves/entrega")
    public String mostrarFormularioEntrega(Model model) {
        // Necesitamos la lista de llaves disponibles para el <select>
        List<Llave> llavesDisponibles = llaveService.findAll(); // Aquí deberías filtrar solo las no prestadas

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
        DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

        keyMove.setKeyFechaEntrega(LocalDate.now().format(dtfFecha));
        keyMove.setKeyHoraEntrega(LocalTime.now().format(dtfHora));

        // Aquí seteamos el KeyLlvOrden basado en el LlvCodigo seleccionado en el form
        // (Asumimos que el campo 'keyLlvOrden' en el formulario contenía el LlvCodigo)

        keyMoveService.save(keyMove);
        return "redirect:/panel";
    }

    /**
     * MUESTRA LA LISTA DE LLAVES PRESTADAS PARA RECOGER
     * Se activa con el botón: "Recogida de Llaves"
     */
    @GetMapping("/llaves/recogida")
    public String mostrarListaRecogida(Model model) {
        // Debes crear un método en tu servicio para buscar llaves sin fecha de recepción
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
        KeyMove keyMove = keyMoveService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de movimiento inválido:" + id));

        DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

        keyMove.setKeyFechaRecepcion(LocalDate.now().format(dtfFecha));
        keyMove.setKeyHoraRecepcion(LocalTime.now().format(dtfHora));

        keyMoveService.save(keyMove);
        return "redirect:/llaves/recogida"; // Recarga la lista
    }

    /**
     * MUESTRA EL HISTÓRICO DE MOVIMIENTOS DE LLAVES
     * Se activa con el botón: "Informes de Llaves"
     */
    @GetMapping("/llaves/informes")
    public String mostrarInformeLlaves(Model model) {
        model.addAttribute("movimientos", keyMoveService.findAll());
        model.addAttribute("pageTitle", "Informe Movimientos de Llaves");
        model.addAttribute("view", "vistas-listados/list-llaves-informe");
        return "layouts/layout";
    }
}