package com.aliro2.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Objects;

/**
 * Un PasswordEncoder personalizado que convierte la contraseña introducida
 * a mayúsculas antes de compararla.
 * Utiliza un "delegado" para la comparación real (ej. NoOpPasswordEncoder).
 */
public class UpperCasePasswordEncoder implements PasswordEncoder {

    private final PasswordEncoder delegate;

    public UpperCasePasswordEncoder(PasswordEncoder delegate) {
        this.delegate = delegate;
    }

    /**
     * Compara la contraseña.
     * @param rawPassword la contraseña introducida por el usuario.
     * @param encodedPassword la contraseña guardada en la base de datos.
     * @return true si coinciden, false si no.
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        // --- AQUÍ ESTÁ LA MAGIA ---
        // Convertimos a mayúsculas la contraseña introducida antes de delegar la comparación.
        String upperCaseRawPassword = rawPassword.toString().toUpperCase();

        // Para una comparación de texto plano, también convertimos a mayúsculas la de la BD.
        // Si usaras BCrypt, la contraseña de la BD ya estaría "hasheada" en mayúsculas.
        return delegate.matches(upperCaseRawPassword, encodedPassword.toUpperCase());
    }

    /**
     * Codifica la contraseña. Convierte a mayúsculas ANTES de codificar.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword.toString().toUpperCase());
    }
}
