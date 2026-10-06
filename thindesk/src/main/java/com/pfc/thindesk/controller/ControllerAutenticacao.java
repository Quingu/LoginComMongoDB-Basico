package com.pfc.thindesk.controller;

import com.pfc.thindesk.dto.CadastroUsuarioDTO;
import com.pfc.thindesk.service.ServiceUsuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerAutenticacao {

    private final ServiceUsuario serviceUsuario;

    public ControllerAutenticacao(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @GetMapping("/cadastro")
    public String exibirCadastro(Model modelo) {
        modelo.addAttribute("cadastro", new CadastroUsuarioDTO());
        return "autenticacao/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@Valid @ModelAttribute("cadastro") CadastroUsuarioDTO dados,
                                   BindingResult resultado, Model modelo) {
        if (resultado.hasErrors()) {
            return "autenticacao/cadastro";
        }
        try {
            serviceUsuario.cadastrarUsuario(dados);
        } catch (IllegalArgumentException e) {
            modelo.addAttribute("erro", e.getMessage());
            return "autenticacao/cadastro";
        }
        return "redirect:/login?cadastrado";
    }

    @GetMapping("/dashboard")
    public String exibirDashboard() {
        return "usuario/dashboard";
    }

    @GetMapping("/perfil")
    public String exibirPerfil() {
        return "usuario/perfil";
    }

    @GetMapping("/gerente/painel")
    public String painelGerente() {
        return "gerente/painel";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "erro/acesso-negado";
    }
}
