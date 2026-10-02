package com.codebusters.erp.domain.categoria;

import com.codebusters.erp.domain.categoria.dto.CategoriaDados;
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
@Table(name = "categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 100)
	private String nome;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Column(nullable = false)
	private Boolean status = true;

	public Categoria(CategoriaDados dados) {
		this.nome = dados.nome();
		this.descricao = dados.descricao();
		this.status = true;
	}
}
