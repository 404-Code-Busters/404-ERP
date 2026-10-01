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

import com.codebusters.erp.domain.item_venda.ItemVenda;
import com.codebusters.erp.domain.item_venda.ItemVendaService;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    @GetMapping
    public List<ItemVenda> listarTodos() {
        return itemVendaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> buscarPorId(@PathVariable Long id) {
        return itemVendaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ItemVenda criar(@RequestBody ItemVenda itemVenda) {
        return itemVendaService.salvar(itemVenda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVenda> atualizar(
            @PathVariable Long id,
            @RequestBody ItemVenda itemVenda) {

        return itemVendaService.atualizar(id, itemVenda)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean excluido = itemVendaService.excluir(id);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}