package com.codebusters.erp.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;

	@NotBlank
	@Size(max = 150)
	@Column(nullable = false, length = 150)
	private String nome;

	@NotBlank
	@Size(max = 50)
	@Column(name = "codigo_interno", nullable = false, unique = true, length = 50)
	private String codigoInterno;

	@Size(max = 50)
	@Column(name = "codigo_barras", unique = true, length = 50)
	private String codigoBarras;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@NotBlank
	@Size(max = 10)
	@Column(name = "unidade_medida", nullable = false, length = 10)
	private String unidadeMedida;

	@NotNull
	@Column(name = "preco_custo", nullable = false, precision = 12, scale = 2)
	private BigDecimal precoCusto;

	@NotNull
	@Column(name = "preco_venda", nullable = false, precision = 12, scale = 2)
	private BigDecimal precoVenda;

	@NotNull
	@Column(name = "estoque_minimo", precision = 12, scale = 3)
	private BigDecimal estoqueMinimo = BigDecimal.ZERO;

	@Column(name = "estoque_maximo", precision = 12, scale = 3)
	private BigDecimal estoqueMaximo;

	@Size(max = 255)
	@Column(length = 255)
	private String imagem;

	@NotNull
	@Column(nullable = false)
	private Boolean status = true;

	public Produto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(BigDecimal estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public BigDecimal getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public void setEstoqueMaximo(BigDecimal estoqueMaximo) {
		this.estoqueMaximo = estoqueMaximo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
