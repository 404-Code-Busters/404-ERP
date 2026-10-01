package com.codebusters.erp.domain.estoque;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Optional<Estoque> buscarPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    public Estoque salvar(Estoque estoque) {
        estoque.setAtualizadoEm(LocalDateTime.now());
        return estoqueRepository.save(estoque);
    }
}