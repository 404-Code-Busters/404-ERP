package com.codebusters.erp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Cliente;
import com.codebusters.erp.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	public Cliente salvar(Cliente cliente) {
		cliente.setDataCriacao(LocalDateTime.now());
		return clienteRepository.save(cliente);
	}

	public Optional<Cliente> atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);
		if (clienteExistente.isEmpty()) {
			return Optional.empty();
		}

		Cliente clienteAtualizado = clienteExistente.get();
		clienteAtualizado.setNome(cliente.getNome());
		clienteAtualizado.setCpf(cliente.getCpf());
		clienteAtualizado.setEmail(cliente.getEmail());
		clienteAtualizado.setTelefone(cliente.getTelefone());
		clienteAtualizado.setDataNascimento(cliente.getDataNascimento());
		clienteAtualizado.setLimiteCredito(cliente.getLimiteCredito());
		clienteAtualizado.setObservacoes(cliente.getObservacoes());
		clienteAtualizado.setStatus(cliente.getStatus());

		return Optional.of(clienteRepository.save(clienteAtualizado));
	}
}
