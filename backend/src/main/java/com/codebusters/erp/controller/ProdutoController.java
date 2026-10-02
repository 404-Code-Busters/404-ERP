package com.codebusters.erp.controller;

import java.util.List;

import com.codebusters.erp.domain.produto.dto.AtualizarDadosProduto;
import com.codebusters.erp.domain.produto.dto.DetalhesDoProduto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codebusters.erp.domain.produto.Produto;
import com.codebusters.erp.domain.produto.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<DetalhesDoProduto>> listarTodos() {
		return ResponseEntity.ok(produtoService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoProduto> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(produtoService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<DetalhesDoProduto> criar(@RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produto));
	}
    
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesDoProduto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarDadosProduto dados) {
        return ResponseEntity.ok(produtoService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
		produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
