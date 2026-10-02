package com.codebusters.erp.domain.categoria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaDados(

        @NotBlank
        @Size(max = 100)
        String nome,

        String descricao,

        @NotNull
        Boolean status
) {
}
