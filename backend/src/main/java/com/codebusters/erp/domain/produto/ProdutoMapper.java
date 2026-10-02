package com.codebusters.erp.domain.produto;

import com.codebusters.erp.domain.produto.dto.AtualizarDadosProduto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void atualizar(AtualizarDadosProduto dados, @MappingTarget Produto produto);
}
