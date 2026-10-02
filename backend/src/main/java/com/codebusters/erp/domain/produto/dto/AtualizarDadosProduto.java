package com.codebusters.erp.domain.produto.dto;

import com.codebusters.erp.domain.categoria.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AtualizarDadosProduto (

        @NotNull
        Long id,

        Long categoriaId,

        @Size(max = 150)
        String nome,

        @Size(max = 50)

        String codigoInterno,

        @Size(max = 50)
        String codigoBarras,

        String descricao,

        @Size(max = 10)
        String unidadeMedida,

        BigDecimal precoCusto,

        BigDecimal precoVenda,

        int estoqueMinimo,

        int estoqueMaximo,

        @Size(max = 255)
        String imagem,

        @Size(max = 255)
        Boolean status
) {
}
