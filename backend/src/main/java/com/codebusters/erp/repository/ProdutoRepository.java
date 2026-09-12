package com.codebusters.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
