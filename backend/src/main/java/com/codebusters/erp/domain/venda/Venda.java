package com.codebusters.erp.domain.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.codebusters.erp.domain.cliente.Cliente;
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
@Table(name = "venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@NotNull
	@Column(name = "data_venda", nullable = false)
	private LocalDateTime dataVenda;

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
