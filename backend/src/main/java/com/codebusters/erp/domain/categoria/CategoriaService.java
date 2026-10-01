package com.codebusters.erp.domain.categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;

	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public List<Categoria> listarTodos() {
		return categoriaRepository.findAll();
	}

	public Optional<Categoria> buscarPorId(Long id) {
		return categoriaRepository.findById(id);
	}

	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
}
