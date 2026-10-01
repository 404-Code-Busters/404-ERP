package com.codebusters.erp.domain.item_compra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

    List<ItemCompra> findByCompraId(Long compraId);
}