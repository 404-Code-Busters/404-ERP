package com.codebusters.erp.domain;

import java.math.BigDecimal;

import com.codebusters.erp.domain.fornecedor.Fornecedor;
import com.codebusters.erp.domain.produto.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
	name = "produto_fornecedor",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = { "produto_id", "fornecedor_id" })
	}
)
public class ProdutoFornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "fornecedor_id", nullable = false)
	private Fornecedor fornecedor;

	@Size(max = 50)
	@Column(name = "codigo_fornecedor", length = 50)
	private String codigoFornecedor;

	@Column(name = "preco_custo", precision = 12, scale = 2)
	private BigDecimal precoCusto;

	@NotNull
	@Column(nullable = false)
	private Boolean principal = false;
}
