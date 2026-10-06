package com.pfc.thindesk.controller;

import com.pfc.thindesk.model.Perfil;
import com.pfc.thindesk.service.ServiceUsuario;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/administrador")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class ControllerUsuario {

    private final ServiceUsuario serviceUsuario;

    public ControllerUsuario(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @GetMapping("/painel")
    public String painel(Model modelo) {
        modelo.addAttribute("usuarios", serviceUsuario.listarUsuarios());
        return "administrador/painel";
    }

    @PostMapping("/promover/{id}")
    public String promoverUsuario(@PathVariable String id, @RequestParam Perfil perfil) {
        serviceUsuario.atualizarPerfil(id, perfil);
        return "redirect:/administrador/painel";
    }
}
