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

import com.codebusters.erp.dto.FornecedorRequest;
import com.codebusters.erp.entity.Fornecedor;
import com.codebusters.erp.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	private final FornecedorService fornecedorService;

	public FornecedorController(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@GetMapping
	public List<Fornecedor> listarTodos() {
		return fornecedorService.listarTodos();
	}

	@GetMapping("/{id}")
	public Fornecedor buscarPorId(@PathVariable Long id) {
		return fornecedorService.buscarPorId(id).orElse(null);
	}

	@PostMapping
	public Fornecedor criar(@RequestBody FornecedorRequest request) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setTipo(request.getTipo());
		fornecedor.setDocumento(request.getDocumento());
		fornecedor.setRazaoSocial(request.getRazaoSocial());
		fornecedor.setNomeFantasia(request.getNomeFantasia());
		fornecedor.setEmail(request.getEmail());
		fornecedor.setTelefone(request.getTelefone());
		fornecedor.setDadosBancarios(request.getDadosBancarios());
		fornecedor.setObservacoes(request.getObservacoes());
		return fornecedorService.salvar(fornecedor);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Long id, @RequestBody FornecedorRequest request) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setTipo(request.getTipo());
		fornecedor.setDocumento(request.getDocumento());
		fornecedor.setRazaoSocial(request.getRazaoSocial());
		fornecedor.setNomeFantasia(request.getNomeFantasia());
		fornecedor.setEmail(request.getEmail());
		fornecedor.setTelefone(request.getTelefone());
		fornecedor.setDadosBancarios(request.getDadosBancarios());
		fornecedor.setObservacoes(request.getObservacoes());

		return fornecedorService.atualizar(id, fornecedor)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		if (fornecedorService.excluir(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
