package com.codebusters.erp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}