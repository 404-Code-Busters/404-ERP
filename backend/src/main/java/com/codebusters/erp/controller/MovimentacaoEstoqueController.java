package com.codebusters.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codebusters.erp.entity.MovimentacaoEstoque;
import com.codebusters.erp.service.MovimentacaoEstoqueService;

@RestController
@RequestMapping("/movimentacoes-estoque")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @GetMapping
    public List<MovimentacaoEstoque> listarTodos() {
        return movimentacaoEstoqueService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoEstoque> buscarPorId(@PathVariable Long id) {
        return movimentacaoEstoqueService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MovimentacaoEstoque salvar(@RequestBody MovimentacaoEstoque movimentacaoEstoque) {
        return movimentacaoEstoqueService.salvar(movimentacaoEstoque);
    }
}