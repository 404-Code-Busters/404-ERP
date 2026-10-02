package com.codebusters.erp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "movimento_financeiro")
public class MovimentoFinanceiro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, length = 20)
	private String tipo;

	@NotBlank
	@Size(max = 200)
	@Column(nullable = false, length = 200)
	private String descricao;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal valor;

	@NotNull
	@Column(name = "data_lancamento", nullable = false)
	private LocalDateTime dataLancamento;

	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, length = 20)
	private String status;

	@Size(max = 30)
	@Column(name = "forma_pagamento", length = 30)
	private String formaPagamento;

	@Size(max = 30)
	@Column(length = 30)
	private String origem;

	@Column(name = "origem_id")
	private Long origemId;

	@Column(columnDefinition = "TEXT")
	private String observacoes;
}
