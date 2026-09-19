package com.codebusters.erp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Compra;
import com.codebusters.erp.entity.ItemCompra;
import com.codebusters.erp.repository.CompraRepository;
import com.codebusters.erp.repository.ItemCompraRepository;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ItemCompraRepository itemCompraRepository;

    public CompraService(
            CompraRepository compraRepository,
            ItemCompraRepository itemCompraRepository) {

        this.compraRepository = compraRepository;
        this.itemCompraRepository = itemCompraRepository;
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

    public Optional<Compra> atualizar(Long id, Compra compra) {

        Optional<Compra> compraExistente = compraRepository.findById(id);

        if (compraExistente.isEmpty()) {
            return Optional.empty();
        }

        Compra existente = compraExistente.get();

        existente.setFornecedor(compra.getFornecedor());
        existente.setDataCompra(compra.getDataCompra());
        existente.setStatus(compra.getStatus());
        existente.setDesconto(compra.getDesconto());
        existente.setFormaPagamento(compra.getFormaPagamento());
        existente.setObservacoes(compra.getObservacoes());

        List<ItemCompra> itens = itemCompraRepository.findByCompraId(id);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal desconto = existente.getDesconto() != null
                ? existente.getDesconto()
                : BigDecimal.ZERO;

        existente.setTotal(subtotalItens.subtract(desconto));

        return Optional.of(compraRepository.save(existente));
    }
}