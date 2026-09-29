package com.codebusters.erp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.ItemVenda;
import com.codebusters.erp.entity.Venda;
import com.codebusters.erp.repository.ItemVendaRepository;
import com.codebusters.erp.repository.VendaRepository;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final VendaRepository vendaRepository;

    public ItemVendaService(
            ItemVendaRepository itemVendaRepository,
            VendaRepository vendaRepository) {

        this.itemVendaRepository = itemVendaRepository;
        this.vendaRepository = vendaRepository;
    }

    public List<ItemVenda> listarTodos() {
        return itemVendaRepository.findAll();
    }

    public Optional<ItemVenda> buscarPorId(Long id) {
        return itemVendaRepository.findById(id);
    }

    public ItemVenda salvar(ItemVenda itemVenda) {

        ItemVenda itemSalvo = itemVendaRepository.save(itemVenda);

        Long vendaId = itemSalvo.getVenda().getId();

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada."));

        List<ItemVenda> itens = itemVendaRepository.findByVendaId(vendaId);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal desconto = venda.getDesconto() != null
                ? venda.getDesconto()
                : BigDecimal.ZERO;

        venda.setTotal(subtotalItens.subtract(desconto));

        vendaRepository.save(venda);

        return itemSalvo;
    }

    public Optional<ItemVenda> atualizar(Long id, ItemVenda itemVenda) {

        Optional<ItemVenda> itemExistente = itemVendaRepository.findById(id);

        if (itemExistente.isEmpty()) {
            return Optional.empty();
        }

        ItemVenda existente = itemExistente.get();

        existente.setQuantidade(itemVenda.getQuantidade());
        existente.setPrecoUnitario(itemVenda.getPrecoUnitario());
        existente.setDesconto(itemVenda.getDesconto());

        BigDecimal quantidade = existente.getQuantidade();
        BigDecimal precoUnitario = existente.getPrecoUnitario();
        BigDecimal desconto = existente.getDesconto() != null
                ? existente.getDesconto()
                : BigDecimal.ZERO;

        BigDecimal subtotal = quantidade
                .multiply(precoUnitario)
                .subtract(desconto);

        existente.setSubtotal(subtotal);

        ItemVenda itemSalvo = itemVendaRepository.save(existente);

        Long vendaId = existente.getVenda().getId();

        List<ItemVenda> itens = itemVendaRepository.findByVendaId(vendaId);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada."));

        BigDecimal descontoVenda = venda.getDesconto() != null
                ? venda.getDesconto()
                : BigDecimal.ZERO;

        venda.setTotal(subtotalItens.subtract(descontoVenda));

        vendaRepository.save(venda);

        return Optional.of(itemSalvo);
    }

    public boolean excluir(Long id) {

        Optional<ItemVenda> itemExistente = itemVendaRepository.findById(id);

        if (itemExistente.isEmpty()) {
            return false;
        }

        ItemVenda item = itemExistente.get();

        Long vendaId = item.getVenda().getId();

        itemVendaRepository.deleteById(id);

        List<ItemVenda> itens = itemVendaRepository.findByVendaId(vendaId);

        BigDecimal subtotalItens = itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada."));

        BigDecimal desconto = venda.getDesconto() != null
                ? venda.getDesconto()
                : BigDecimal.ZERO;

        venda.setTotal(subtotalItens.subtract(desconto));

        vendaRepository.save(venda);

        return true;
    }
}