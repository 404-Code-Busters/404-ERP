package com.codebusters.erp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Estoque;
import com.codebusters.erp.entity.MovimentacaoEstoque;
import com.codebusters.erp.repository.EstoqueRepository;
import com.codebusters.erp.repository.MovimentacaoEstoqueRepository;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final EstoqueRepository estoqueRepository;

    public MovimentacaoEstoqueService(
            MovimentacaoEstoqueRepository movimentacaoEstoqueRepository,
            EstoqueRepository estoqueRepository) {

        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<MovimentacaoEstoque> listarTodos() {
        return movimentacaoEstoqueRepository.findAll();
    }

    public Optional<MovimentacaoEstoque> buscarPorId(Long id) {
        return movimentacaoEstoqueRepository.findById(id);
    }

    public MovimentacaoEstoque salvar(MovimentacaoEstoque movimentacaoEstoque) {

        Long produtoId = movimentacaoEstoque.getProduto().getId();

        Optional<Estoque> estoqueExistente = estoqueRepository.findByProdutoId(produtoId);

        if (estoqueExistente.isEmpty()) {
            throw new RuntimeException("Estoque não encontrado para o produto.");
        }

        Estoque estoque = estoqueExistente.get();

        if (movimentacaoEstoque.getTipo().equals("ENTRADA")) {

            estoque.setQuantidade(
                    estoque.getQuantidade().add(movimentacaoEstoque.getQuantidade())
            );

        } else if (movimentacaoEstoque.getTipo().equals("SAIDA")) {

            if (estoque.getQuantidade().compareTo(movimentacaoEstoque.getQuantidade()) < 0) {
                throw new RuntimeException("Estoque insuficiente para realizar a saída.");
            }

            estoque.setQuantidade(
                    estoque.getQuantidade().subtract(movimentacaoEstoque.getQuantidade())
            );

        } else if (movimentacaoEstoque.getTipo().equals("AJUSTE")) {

            estoque.setQuantidade(movimentacaoEstoque.getQuantidade());
        }

        estoque.setAtualizadoEm(LocalDateTime.now());

        estoqueRepository.save(estoque);

        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }
}