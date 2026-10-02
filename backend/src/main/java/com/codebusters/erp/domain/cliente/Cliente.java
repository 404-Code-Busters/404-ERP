package com.codebusters.erp.domain.cliente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 150)
	@Column(nullable = false, length = 150)
	private String nome;

	@NotBlank
	@Size(max = 11)
	@Column(nullable = false, unique = true, length = 11)
	private String cpf;

	@Email
	@Size(max = 150)
	@Column(length = 150)
	private String email;

	@Size(max = 20)
	@Column(length = 20)
	private String telefone;

	private LocalDate dataNascimento;

	@Column(precision = 12, scale = 2)
	private BigDecimal limiteCredito = BigDecimal.ZERO;

	@Column(columnDefinition = "TEXT")
	private String observacoes;

	@NotNull
	@Column(nullable = false)
	private Boolean status = true;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime dataCriacao;
}
