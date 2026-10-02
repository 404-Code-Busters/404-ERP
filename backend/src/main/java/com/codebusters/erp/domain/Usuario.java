package com.codebusters.erp.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 150)
	@Column(nullable = false, length = 150)
	private String nome;

	@NotBlank
	@Email
	@Size(max = 150)
	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false, length = 255)
	private String senha;

	@Size(max = 20)
	@Column(length = 20)
	private String telefone;

	@NotNull
	@Column(nullable = false)
	private Boolean status = true;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime dataCriacao;

	@Column
	private LocalDateTime ultimoAcesso;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "perfil_id", nullable = false)
	private Perfil perfil;
}
