package com.codebusters.erp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.ItemCompra;
import com.codebusters.erp.repository.ItemCompraRepository;

@Service
public class ItemCompraService {

    private final ItemCompraRepository itemCompraRepository;

    public ItemCompraService(ItemCompraRepository itemCompraRepository) {
        this.itemCompraRepository = itemCompraRepository;
    }

    public List<ItemCompra> listarTodos() {
        return itemCompraRepository.findAll();
    }

    public Optional<ItemCompra> buscarPorId(Long id) {
        return itemCompraRepository.findById(id);
    }

    public ItemCompra salvar(ItemCompra itemCompra) {
        return itemCompraRepository.save(itemCompra);
    }
}
