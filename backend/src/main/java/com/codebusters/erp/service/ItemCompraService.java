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
public class ItemCompraService {

    private final ItemCompraRepository itemCompraRepository;
    private final CompraRepository compraRepository;

    public ItemCompraService(
            ItemCompraRepository itemCompraRepository,
            CompraRepository compraRepository) {

        this.itemCompraRepository = itemCompraRepository;
        this.compraRepository = compraRepository;
    }

    public List<ItemCompra> listarTodos() {
        return itemCompraRepository.findAll();
    }

    public Optional<ItemCompra> buscarPorId(Long id) {
        return itemCompraRepository.findById(id);
    }

    public ItemCompra salvar(ItemCompra itemCompra) {

        ItemCompra itemSalvo = itemCompraRepository.save(itemCompra);

        Long compraId = itemSalvo.getCompra().getId();

        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada."));

        List<ItemCompra> itens = itemCompraRepository.findByCompraId(compraId);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal desconto = compra.getDesconto() != null
                ? compra.getDesconto()
                : BigDecimal.ZERO;

        compra.setTotal(subtotalItens.subtract(desconto));

        compraRepository.save(compra);

        return itemSalvo;
    }

    public Optional<ItemCompra> atualizar(Long id, ItemCompra itemCompra) {

        Optional<ItemCompra> itemExistente = itemCompraRepository.findById(id);

        if (itemExistente.isEmpty()) {
            return Optional.empty();
        }

        ItemCompra existente = itemExistente.get();

        existente.setQuantidade(itemCompra.getQuantidade());
        existente.setPrecoUnitario(itemCompra.getPrecoUnitario());
        existente.setDesconto(itemCompra.getDesconto());

        BigDecimal quantidade = existente.getQuantidade();
        BigDecimal precoUnitario = existente.getPrecoUnitario();
        BigDecimal desconto = existente.getDesconto() != null
                ? existente.getDesconto()
                : BigDecimal.ZERO;

        BigDecimal subtotal = quantidade
                .multiply(precoUnitario)
                .subtract(desconto);

        existente.setSubtotal(subtotal);

        ItemCompra itemSalvo = itemCompraRepository.save(existente);

        Long compraId = existente.getCompra().getId();

        List<ItemCompra> itens = itemCompraRepository.findByCompraId(compraId);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada."));

        BigDecimal descontoCompra = compra.getDesconto() != null
                ? compra.getDesconto()
                : BigDecimal.ZERO;

        compra.setTotal(subtotalItens.subtract(descontoCompra));

        compraRepository.save(compra);

        return Optional.of(itemSalvo);
    }

    public boolean excluir(Long id) {

        Optional<ItemCompra> itemExistente = itemCompraRepository.findById(id);

        if (itemExistente.isEmpty()) {
            return false;
        }

        ItemCompra item = itemExistente.get();

        Long compraId = item.getCompra().getId();

        itemCompraRepository.deleteById(id);

        List<ItemCompra> itens = itemCompraRepository.findByCompraId(compraId);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada."));

        BigDecimal desconto = compra.getDesconto() != null
                ? compra.getDesconto()
                : BigDecimal.ZERO;

        compra.setTotal(subtotalItens.subtract(desconto));

        compraRepository.save(compra);

        return true;
    }
}