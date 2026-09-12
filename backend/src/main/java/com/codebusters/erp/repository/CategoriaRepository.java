package com.codebusters.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codebusters.erp.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
