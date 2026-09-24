package com.codebusters.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codebusters.erp.entity.ItemCompra;
import com.codebusters.erp.service.ItemCompraService;

@RestController
@RequestMapping("/itens-compra")
public class ItemCompraController {

    private final ItemCompraService itemCompraService;

    public ItemCompraController(ItemCompraService itemCompraService) {
        this.itemCompraService = itemCompraService;
    }

    @GetMapping
    public List<ItemCompra> listarTodos() {
        return itemCompraService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCompra> buscarPorId(@PathVariable Long id) {
        return itemCompraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ItemCompra criar(@RequestBody ItemCompra itemCompra) {
        return itemCompraService.salvar(itemCompra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCompra> atualizar(
            @PathVariable Long id,
            @RequestBody ItemCompra itemCompra) {

        return itemCompraService.atualizar(id, itemCompra)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean excluido = itemCompraService.excluir(id);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}