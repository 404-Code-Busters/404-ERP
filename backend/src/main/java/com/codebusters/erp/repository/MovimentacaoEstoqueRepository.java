package com.codebusters.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.MovimentacaoEstoque;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
}