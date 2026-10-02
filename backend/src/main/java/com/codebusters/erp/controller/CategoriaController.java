package com.codebusters.erp.controller;

import java.util.List;

import com.codebusters.erp.domain.categoria.dto.CategoriaDados;
import com.codebusters.erp.domain.categoria.dto.DetalhesCategoria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codebusters.erp.domain.categoria.Categoria;
import com.codebusters.erp.domain.categoria.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;


	@GetMapping
	public List<DetalhesCategoria> listarTodos() {
		return categoriaService.listarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesCategoria> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(categoriaService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<DetalhesCategoria> criar(@RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaService.salvar(categoria));
	}
}
