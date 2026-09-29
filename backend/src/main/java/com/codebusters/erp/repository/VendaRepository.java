package com.codebusters.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}