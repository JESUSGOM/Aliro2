package com.aliro2.service;

import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Método específico que ya usamos para el login
    public Optional<Usuario> findByUsuDni(String id) {
        return usuarioRepository.findByUsuDni(id);
    }

    /**
     * Valida si las credenciales (DNI y clave) proporcionadas por el usuario son correctas.
     *
     * @param dni   El DNI del usuario que intenta iniciar sesión.
     * @param clave La contraseña proporcionada.
     * @return Un Optional que contiene el objeto Usuario si la validación es exitosa,
     * o un Optional vacío si las credenciales son incorrectas.
     */
    public Optional<Usuario> validarUsuario(String dni, String clave) {
        // 1. Busca un usuario en la base de datos por su DNI.
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuDni(dni);

        // 2. Comprueba si el usuario existe Y si la contraseña coincide.
        //    (NOTA: En un proyecto real, aquí se usaría un comparador de contraseñas hasheadas).
        if (usuarioOpt.isPresent() && usuarioOpt.get().getUsuClave().equals(clave)) {
            // Si ambas condiciones son verdaderas, las credenciales son válidas.
            return usuarioOpt;
        }

        // 3. Si el usuario no existe o la contraseña no coincide, devuelve un Optional vacío.
        return Optional.empty();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }
}
