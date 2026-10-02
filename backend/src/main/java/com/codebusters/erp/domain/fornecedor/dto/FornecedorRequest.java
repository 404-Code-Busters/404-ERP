package com.codebusters.erp.domain.fornecedor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorRequest {

    private String tipo;
    private String documento;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String dadosBancarios;
    private String observacoes;

}
