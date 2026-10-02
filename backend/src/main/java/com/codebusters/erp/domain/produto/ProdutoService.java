package com.codebusters.erp.domain.produto;

import java.util.List;

import com.codebusters.erp.domain.produto.dto.AtualizarDadosProduto;
import com.codebusters.erp.domain.produto.dto.DetalhesDoProduto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public List<DetalhesDoProduto> listarTodos() {
        return produtoRepository.findAll().stream().map(DetalhesDoProduto::new).toList();
    }

    public DetalhesDoProduto buscarPorId(Long id) {
        var produto = produtoRepository.getReferenceById(id);
        return new DetalhesDoProduto(produto);
    }

    @Transactional
    public DetalhesDoProduto salvar(Produto produto) {
        var produtoSalvo = produtoRepository.save(produto);
        return new DetalhesDoProduto(produtoSalvo);
    }

    @Transactional
    public DetalhesDoProduto atualizar(Long id, AtualizarDadosProduto dados) {
        var produto = produtoRepository.getReferenceById(id);
        produtoMapper.atualizar(dados, produto);
        return new DetalhesDoProduto(produto);
    }

    @Transactional
    public DetalhesDoProduto excluir(Long id) {
        var produto = produtoRepository.getReferenceById(id);
        produto.excluir();
        return new DetalhesDoProduto(produto);
    }
}
