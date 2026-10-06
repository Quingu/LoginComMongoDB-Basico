package com.pfc.thindesk.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastroUsuarioDTO {

    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 3, message = "Nome deve ter ao menos 3 caracteres.")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 8, message = "Senha deve ter ao menos 8 caracteres.")
    private String senha;

    @NotBlank(message = "Confirmação de senha é obrigatória.")
    private String senhaConfirmacao;
}
