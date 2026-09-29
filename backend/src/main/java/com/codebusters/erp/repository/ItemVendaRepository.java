package com.codebusters.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    List<ItemVenda> findByVendaId(Long vendaId);
}