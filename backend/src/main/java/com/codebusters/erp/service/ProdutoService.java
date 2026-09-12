package com.codebusters.erp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codebusters.erp.entity.Produto;
import com.codebusters.erp.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> buscarPorId(Long id) {
		return produtoRepository.findById(id);
	}

	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
    public Optional<Produto> atualizar(Long id, Produto produto) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isEmpty()) {
            return Optional.empty();
        }

        Produto existente = produtoExistente.get();

        existente.setCategoria(produto.getCategoria());
        existente.setNome(produto.getNome());
        existente.setCodigoInterno(produto.getCodigoInterno());
        existente.setCodigoBarras(produto.getCodigoBarras());
        existente.setDescricao(produto.getDescricao());
        existente.setUnidadeMedida(produto.getUnidadeMedida());
        existente.setPrecoCusto(produto.getPrecoCusto());
        existente.setPrecoVenda(produto.getPrecoVenda());
        existente.setEstoqueMinimo(produto.getEstoqueMinimo());
        existente.setEstoqueMaximo(produto.getEstoqueMaximo());
        existente.setImagem(produto.getImagem());
        existente.setStatus(produto.getStatus());

        return Optional.of(produtoRepository.save(existente));
    }
    public boolean excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            return false;
        }

        produtoRepository.deleteById(id);
        return true;
    }
}
