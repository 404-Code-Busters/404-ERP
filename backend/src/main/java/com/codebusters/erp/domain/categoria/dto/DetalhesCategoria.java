package com.codebusters.erp.domain.categoria.dto;

import com.codebusters.erp.domain.categoria.Categoria;

public record DetalhesCategoria(

        Long id,

        String name,

        String descricao,

        Boolean status

) {
    public DetalhesCategoria(Categoria c) {
        this(
                c.getId(),
                c.getNome(),
                c.getDescricao(),
                c.getStatus()
        );
    }
}
