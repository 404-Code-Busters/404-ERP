package com.codebusters.erp.domain;

import java.math.BigDecimal;
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
@Table(name = "servico")
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 150)
	@Column(nullable = false, length = 150)
	private String nome;

	@NotBlank
	@Size(max = 50)
	@Column(name = "codigo_interno", nullable = false, unique = true, length = 50)
	private String codigoInterno;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Column(name = "preco_custo", precision = 12, scale = 2)
	private BigDecimal precoCusto = BigDecimal.ZERO;

	@NotNull
	@Column(name = "preco_venda", nullable = false, precision = 12, scale = 2)
	private BigDecimal precoVenda;

	@NotNull
	@Column(nullable = false)
	private Boolean status = true;

	@NotNull
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;

}
