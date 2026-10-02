package com.codebusters.erp.domain.item_compra;

import java.math.BigDecimal;

import com.codebusters.erp.domain.compra.Compra;
import com.codebusters.erp.domain.produto.Produto;
import com.codebusters.erp.domain.Servico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item_compra")
public class ItemCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "compra_id", nullable = false)
	private Compra compra;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "servico_id")
	private Servico servico;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 3)
	private BigDecimal quantidade;

	@NotNull
	@Column(name = "preco_unitario", nullable = false, precision = 12, scale = 2)
	private BigDecimal precoUnitario;

	@Column(precision = 12, scale = 2)
	private BigDecimal desconto = BigDecimal.ZERO;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal subtotal;
}
