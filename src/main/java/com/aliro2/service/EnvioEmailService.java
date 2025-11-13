package com.aliro2.service;

import com.aliro2.model.EnvioEmail;
import com.aliro2.repository.EnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // <- IMPORTADO
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // <- IMPORTADO
import java.time.format.DateTimeFormatter;

@Service
public class EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EnvioEmailRepository envioEmailRepository;

    // --- FORMATEADORES ELIMINADOS ---
    // private final DateTimeFormatter dtfFechaLog = ...
    // private final DateTimeFormatter dtfHoraLog = ...

    @Value("${spring.mail.username}")
    private String emailFrom;

    /**
     * Envía el email de aviso de llamada.
     * (Cambiado 'emisorDni' por 'enEmEmisor' para que coincida con el modelo)
     */
    public void enviarEmailLlamada(String emailDestino,
                                   String nombreConserje,
                                   String centroDenominacion,
                                   String quienLlama,
                                   String paraQuien,
                                   String mensaje,
                                   String enEmEmisor) { // DNI del conserje

        String cuerpo = "Se ha recibido una llamada telefónica de: " + quienLlama + "\n" +
                "Para: " + paraQuien + "\n" +
                "Mensaje: " + mensaje;
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(emailDestino);
            email.setSubject("Aviso de llamada telefónica desde " + centroDenominacion);
            email.setFrom(nombreConserje + " <" + emailFrom + ">");
            email.setText(cuerpo);

            mailSender.send(email);

            // Guardar log en la BD
            guardarLogEmail(emailDestino, cuerpo, enEmEmisor);

        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
        }
    }

    /**
     * (¡ACTUALIZADO!)
     * Guarda un registro en la tabla EnvioEmail
     */
    private void guardarLogEmail(String destinatario, String texto, String enEmEmisor) {
        try {
            EnvioEmail log = new EnvioEmail();
            log.setEnEmDestinatario(destinatario);
            log.setEnEmTexto(texto);
            log.setEnEmEmisor(enEmEmisor); // DNI del que envía

            // --- LÓGICA DE FECHA ACTUALIZADA ---
            log.setEnEmFechaHoraDt(LocalDateTime.now());

            envioEmailRepository.save(log);
        } catch (Exception e) {
            System.err.println("Error al guardar log de email: " + e.getMessage());
        }
    }
}