package com.codebusters.erp.domain.produto;

import java.math.BigDecimal;

import com.codebusters.erp.domain.categoria.Categoria;
import com.codebusters.erp.domain.produto.dto.AtualizarDadosProduto;
import com.codebusters.erp.domain.produto.dto.ProdutoDados;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(name = "codigo_interno", nullable = false, unique = true, length = 50)
	private String codigoInterno;

	@Column(name = "codigo_barras", unique = true, length = 50)
	private String codigoBarras;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Column(name = "unidade_medida", nullable = false, length = 10)
	private String unidadeMedida;

	@Column(name = "preco_custo", nullable = false, precision = 12, scale = 2)
	private BigDecimal precoCusto;

	@Column(name = "preco_venda", nullable = false, precision = 12, scale = 2)
	private BigDecimal precoVenda;

	@Column(name = "estoque_minimo", precision = 12, scale = 3)
	private BigDecimal estoqueMinimo = BigDecimal.ZERO;

	@Column(name = "estoque_maximo", precision = 12, scale = 3)
	private BigDecimal estoqueMaximo;

	@Column(length = 255)
	private String imagem;

	private Boolean status = true;

	public Produto(ProdutoDados dados, Categoria categoria) {
		this.categoria = categoria;
		this.nome = dados.nome();
		this.codigoInterno = dados.codigoInterno();
		this.codigoBarras = dados.codigoBarras();
		this.descricao = dados.descricao();
		this.unidadeMedida = dados.unidadeMedida();
		this.precoCusto = dados.precoCusto();
		this.precoVenda = dados.precoVenda();
		this.estoqueMinimo = dados.estoqueMinimo();
		this.estoqueMaximo = dados.estoqueMaximo();
		this.imagem = dados.imagem();
		this.status = true;
	}

	public void excluir() {
		this.status = false;
	}

}
