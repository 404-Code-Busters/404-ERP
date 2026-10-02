package com.codebusters.erp.domain.categoria;

import java.util.List;
import java.util.Optional;

import com.codebusters.erp.domain.categoria.dto.CategoriaDados;
import com.codebusters.erp.domain.categoria.dto.DetalhesCategoria;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;


	public List<DetalhesCategoria> listarTodos() {
		return categoriaRepository.findAll().stream().map(DetalhesCategoria::new).toList();
	}

	public DetalhesCategoria buscarPorId(Long id) {
		var categoria = categoriaRepository.getReferenceById(id);
		return new DetalhesCategoria(categoria);
	}

	@Transactional
	public DetalhesCategoria salvar(Categoria categoria) {
		categoriaRepository.save(categoria);
		return new DetalhesCategoria(categoria);
	}

}
