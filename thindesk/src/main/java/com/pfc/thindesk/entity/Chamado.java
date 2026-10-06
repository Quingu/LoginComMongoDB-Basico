package com.pfc.thindesk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chamados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {

    @Id
    private String id;
    private String descricao;
    private String status;
    private String tipo;
    private String tecnico;
    private String usuario;
}
