package com.aliro2.controller;

import com.aliro2.model.Telefono;
import com.aliro2.repository.TelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/telefonos") // Todas las URLs empiezan con /telefonos
public class TelefonoController {

    @Autowired
    private TelefonoRepository telefonoRepository;

    /**
     * Muestra el FORMULARIO para crear una nueva llamada.
     * Carga el archivo: /templates/vistas-formularios/form-llamada.html
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaLlamada(Model model) {
        // Objeto vacío para enlazar con el formulario th:object
        model.addAttribute("nuevaLlamada", new Telefono());

        // Devuelve la RUTA al fragmento del formulario
        return "vistas-formularios/form-llamada";
    }

    /**
     * Muestra el LISTADO de todas las llamadas registradas.
     * Carga el archivo: /templates/vistas-listados/list-llamadas-informe.html
     */
    @GetMapping("/listado")
    public String mostrarListadoLlamadas(Model model) {
        // Busca todas las llamadas en la BBDD
        List<Telefono> llamadas = telefonoRepository.findAll();

        // Las añade al modelo para que la tabla las lea
        model.addAttribute("listaDeLlamadas", llamadas);

        // Devuelve la RUTA al fragmento del listado
        return "vistas-listados/list-llamadas-informe"; // O el .html que vayas a usar para listar
    }


    /**
     * Procesa el envío del formulario para GUARDAR una nueva llamada.
     */
    @PostMapping("/guardar")
    public String guardarNuevaLlamada(@ModelAttribute("nuevaLlamada") Telefono telefono) {

        // --- Rellenar campos automáticos ---

        // !! AJUSTA ESTO !! (Deberías sacarlo del usuario logueado)
        telefono.setTelCentro(1);

        // Formato de fecha y hora (varchar(8) y varchar(6))
        DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmmss");

        telefono.setTelFecha(LocalDate.now().format(dtfFecha));
        telefono.setTelHora(LocalTime.now().format(dtfHora));

        // Guardar en la BBDD
        telefonoRepository.save(telefono);

        // Redirigir al listado (al método @GetMapping("/listado"))
        return "redirect:/telefonos/listado";
    }
}