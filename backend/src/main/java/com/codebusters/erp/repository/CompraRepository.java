package com.codebusters.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}