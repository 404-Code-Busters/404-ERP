package com.codebusters.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
