package com.codebusters.erp.service;

import java.util.List;

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
}
