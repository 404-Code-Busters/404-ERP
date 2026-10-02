package com.codebusters.erp.domain.produto.dto;

import com.codebusters.erp.domain.categoria.dto.DetalhesCategoria;
import com.codebusters.erp.domain.produto.Produto;
import java.math.BigDecimal;

public record DetalhesDoProduto(
        Long id,

        String nome,

        String codigoInterno,

        String codigoBarras,

        String descricao,

        String unidadeMedida,

        BigDecimal precoCusto,

        BigDecimal precoVenda,

        BigDecimal estoqueMinimo,

        BigDecimal estoqueMaximo,

        String imagem,

        Boolean status,

        DetalhesCategoria categoria
) {
    public DetalhesDoProduto(Produto p) {
        this(
                p.getId(),
                p.getNome(),
                p.getCodigoInterno(),
                p.getCodigoBarras(),
                p.getDescricao(),
                p.getUnidadeMedida(),
                p.getPrecoCusto(),
                p.getPrecoVenda(),
                p.getEstoqueMinimo(),
                p.getEstoqueMaximo(),
                p.getImagem(),
                p.getStatus(),
                new DetalhesCategoria(p.getCategoria())
        );
    }
}
