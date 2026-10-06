package com.pfc.thindesk.service;

import com.pfc.thindesk.model.Usuario;
import com.pfc.thindesk.repository.RepositoryUsuario;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServiceAutenticacao implements UserDetailsService {

    private final RepositoryUsuario repositoryUsuario;

    public ServiceAutenticacao(RepositoryUsuario repositoryUsuario) {
        this.repositoryUsuario = repositoryUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repositoryUsuario.buscarPorEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        // ROLE_ + perfil permite hasRole/hasAnyRole no SecurityConfig e sec:authorize.
        String autoridade = "ROLE_" + usuario.getPerfil().name();
        return new User(usuario.getEmail(), usuario.getSenha(), usuario.isAtivo(),
                true, true, true, List.of(new SimpleGrantedAuthority(autoridade)));
    }
}
