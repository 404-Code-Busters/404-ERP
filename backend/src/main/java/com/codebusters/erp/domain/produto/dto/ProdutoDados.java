package com.codebusters.erp.domain.produto.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoDados(

        @NotNull
        Long categoriaId,

        @NotBlank
        @Size(max = 150)
        String nome,

        @NotBlank
        @Size(max = 50)

        String codigoInterno,

        @Size(max = 50)
        String codigoBarras,

        String descricao,

        @NotBlank
        @Size(max = 10)
        String unidadeMedida,

        @NotNull
        BigDecimal precoCusto,

        @NotNull
        BigDecimal precoVenda,

        @NotNull
        BigDecimal estoqueMinimo,

        BigDecimal estoqueMaximo,

        @Size(max = 255)
        String imagem,

        @Size(max = 255)
        Boolean status
) {
}
