
package com.codebusters.erp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Venda;
import com.codebusters.erp.repository.ItemVendaRepository;
import com.codebusters.erp.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ItemVendaRepository itemVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public boolean possuiItens(Long id) {
        return !itemVendaRepository.findByVendaId(id).isEmpty();
    }

    public Venda salvar(Venda venda) {
        return vendaRepository.save(venda);
    }

    public Optional<Venda> atualizar(Long id, Venda venda) {

        Optional<Venda> vendaExistente = vendaRepository.findById(id);

        if (vendaExistente.isEmpty()) {
            return Optional.empty();
        }

        Venda existente = vendaExistente.get();

        existente.setCliente(venda.getCliente());
        existente.setDataVenda(venda.getDataVenda());
        existente.setStatus(venda.getStatus());
        existente.setDesconto(venda.getDesconto());
        existente.setFormaPagamento(venda.getFormaPagamento());
        existente.setObservacoes(venda.getObservacoes());
        existente.setTotal(venda.getTotal());

        return Optional.of(vendaRepository.save(existente));
    }

    public boolean excluir(Long id) {

        if (!vendaRepository.existsById(id)) {
            return false;
        }

        // Verifica se a venda possui itens vinculados
        if (!itemVendaRepository.findByVendaId(id).isEmpty()) {
            return false;
        }

        vendaRepository.deleteById(id);

        return true;
    }
}