package com.codebusters.erp.domain;

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
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@Size(max = 30)
	@Column(length = 30)
	private String tipo;

	@NotBlank
	@Size(max = 8)
	@Column(nullable = false, length = 8)
	private String cep;

	@NotBlank
	@Size(max = 150)
	@Column(nullable = false, length = 150)
	private String logradouro;

	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, length = 20)
	private String numero;

	@Size(max = 100)
	@Column(length = 100)
	private String complemento;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String bairro;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String cidade;

	@NotBlank
	@Size(max = 2)
	@Column(nullable = false, length = 2)
	private String estado;
}
