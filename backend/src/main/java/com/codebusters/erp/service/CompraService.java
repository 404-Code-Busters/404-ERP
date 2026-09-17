package com.codebusters.erp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Compra;
import com.codebusters.erp.repository.CompraRepository;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<Compra> listarTodos() {
        return compraRepository.findAll();
    }

    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }

    public Compra salvar(Compra compra) {
        return compraRepository.save(compra);
    }
}