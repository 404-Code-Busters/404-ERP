package com.codebusters.erp.domain.fornecedor;

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
@Table(name = "fornecedor")
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 2)
	@Column(nullable = false, length = 2)
	private String tipo;

	@NotBlank
	@Size(max = 14)
	@Column(nullable = false, unique = true, length = 14)
	private String documento;

	@NotBlank
	@Size(max = 150)
	@Column(name = "razao_social", nullable = false, length = 150)
	private String razaoSocial;

	@Size(max = 150)
	@Column(name = "nome_fantasia", length = 150)
	private String nomeFantasia;

	@Email
	@Size(max = 150)
	@Column(length = 150)
	private String email;

	@Size(max = 20)
	@Column(length = 20)
	private String telefone;

	@Column(name = "dados_bancarios", columnDefinition = "TEXT")
	private String dadosBancarios;

	@Column(columnDefinition = "TEXT")
	private String observacoes;

	@NotNull
	@Column(nullable = false)
	private Boolean status = true;

	@NotNull
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
}
