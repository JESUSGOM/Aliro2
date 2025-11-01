package com.aliro2.service;

import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Convertimos el DNI intrudicido a maýusculas antes de buscarlo
        final String upperCaseUsername = username.toUpperCase();
        // Buscamos el usuario en nuestra base de datos por el DNI
        Usuario usuario = usuarioRepository.findByUsuDni(upperCaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con DNI: " + username));

        // Creamos una autoridad (rol) para Spring Security a partir de UsuTipo
        // Es una buena práctica prefijar los roles con "ROLE_"
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getUsuTipo());

        // Devolvemos un objeto User que Spring Security entiende
        return new User(usuario.getUsuDni(), usuario.getUsuClave(), Collections.singleton(authority));
    }
}
