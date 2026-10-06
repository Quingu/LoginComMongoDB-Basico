package com.pfc.thindesk.service;

import com.pfc.thindesk.dto.CadastroUsuarioDTO;
import com.pfc.thindesk.model.Perfil;
import com.pfc.thindesk.model.Usuario;
import com.pfc.thindesk.repository.RepositoryUsuario;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceUsuario {

    private final RepositoryUsuario repositoryUsuario;
    private final PasswordEncoder codificadorSenha;

    public ServiceUsuario(RepositoryUsuario repositoryUsuario, PasswordEncoder codificadorSenha) {
        this.repositoryUsuario = repositoryUsuario;
        this.codificadorSenha = codificadorSenha;
    }

    // Fluxo: validações -> BCrypt -> save no MongoDB.
    public Usuario cadastrarUsuario(CadastroUsuarioDTO dados) {
        if (!dados.getSenha().equals(dados.getSenhaConfirmacao())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }
        verificarEmailExistente(dados.getEmail());

        Usuario usuario = new Usuario();
        usuario.setNome(dados.getNome().trim());
        usuario.setEmail(dados.getEmail().trim().toLowerCase());
        // Nunca armazena a senha original — apenas o hash BCrypt.
        usuario.setSenha(codificadorSenha.encode(dados.getSenha()));
        usuario.setPerfil(Perfil.USUARIO);
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());
        return repositoryUsuario.save(usuario);
    }

    public void verificarEmailExistente(String email) {
        String normalizado = email.trim().toLowerCase();
        if (repositoryUsuario.verificarEmailExistente(normalizado)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
    }

    // Busca o usuário pelo e-mail informado no formulário de login.
    public Usuario buscarPorEmail(String email) {
        return repositoryUsuario.buscarPorEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    public List<Usuario> listarUsuarios() {
        return repositoryUsuario.findAll();
    }

    public Usuario atualizarPerfil(String id, Perfil novoPerfil) {
        Usuario usuario = repositoryUsuario.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setPerfil(novoPerfil);
        usuario.setDataAtualizacao(LocalDateTime.now());
        return repositoryUsuario.save(usuario);
    }
}
