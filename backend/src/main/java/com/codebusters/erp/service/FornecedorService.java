package com.codebusters.erp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Fornecedor;
import com.codebusters.erp.repository.FornecedorRepository;

@Service
public class FornecedorService {

	private final FornecedorRepository fornecedorRepository;

	public FornecedorService(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}

	public List<Fornecedor> listarTodos() {
		return fornecedorRepository.findAll();
	}

	public Optional<Fornecedor> buscarPorId(Long id) {
		return fornecedorRepository.findById(id);
	}

	public Fornecedor salvar(Fornecedor fornecedor) {
		fornecedor.setDataCriacao(LocalDateTime.now());
		return fornecedorRepository.save(fornecedor);
	}

	public Optional<Fornecedor> atualizar(Long id, Fornecedor fornecedor) {
		Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findById(id);
		if (fornecedorExistente.isEmpty()) {
			return Optional.empty();
		}

		Fornecedor fornecedorAtualizado = fornecedorExistente.get();
		fornecedorAtualizado.setTipo(fornecedor.getTipo());
		fornecedorAtualizado.setDocumento(fornecedor.getDocumento());
		fornecedorAtualizado.setRazaoSocial(fornecedor.getRazaoSocial());
		fornecedorAtualizado.setNomeFantasia(fornecedor.getNomeFantasia());
		fornecedorAtualizado.setEmail(fornecedor.getEmail());
		fornecedorAtualizado.setTelefone(fornecedor.getTelefone());
		fornecedorAtualizado.setDadosBancarios(fornecedor.getDadosBancarios());
		fornecedorAtualizado.setObservacoes(fornecedor.getObservacoes());
		fornecedorAtualizado.setStatus(fornecedor.getStatus());

		return Optional.of(fornecedorRepository.save(fornecedorAtualizado));
	}

	public boolean excluir(Long id) {
		if (!fornecedorRepository.existsById(id)) {
			return false;
		}

		fornecedorRepository.deleteById(id);
		return true;
	}
}
