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

import com.codebusters.erp.entity.Compra;
import com.codebusters.erp.exception.RegraNegocioException;
import com.codebusters.erp.service.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public List<Compra> listarTodos() {
        return compraService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscarPorId(@PathVariable Long id) {
        return compraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Compra criar(@RequestBody Compra compra) {
        return compraService.salvar(compra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> atualizar(
            @PathVariable Long id,
            @RequestBody Compra compra) {

        return compraService.atualizar(id, compra)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {

        try {
            boolean excluida = compraService.excluir(id);

            if (!excluida) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.noContent().build();

        } catch (RegraNegocioException e) {
            return ResponseEntity
                    .status(409)
                    .body(e.getMessage());
        }
    }
}