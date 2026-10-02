package com.codebusters.erp.domain.compra;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.codebusters.erp.domain.fornecedor.Fornecedor;
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
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "compra")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "fornecedor_id", nullable = false)
	private Fornecedor fornecedor;

	@NotNull
	@Column(name = "data_compra", nullable = false)
	private LocalDateTime dataCompra;

	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, length = 20)
	private String status;

	@Column(precision = 12, scale = 2)
	private BigDecimal desconto = BigDecimal.ZERO;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal total;

	@Size(max = 30)
	@Column(name = "forma_pagamento", length = 30)
	private String formaPagamento;

	@Column(columnDefinition = "TEXT")
	private String observacoes;
}
