package com.codebusters.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.ItemCompra;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

    List<ItemCompra> findByCompraId(Long compraId);
}