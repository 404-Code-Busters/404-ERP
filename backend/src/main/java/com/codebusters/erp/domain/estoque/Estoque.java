package com.codebusters.erp.domain.estoque;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.codebusters.erp.domain.produto.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estoque")
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@OneToOne
	@JoinColumn(name = "produto_id", nullable = false, unique = true)
	private Produto produto;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 3)
	private BigDecimal quantidade = BigDecimal.ZERO;

	@NotNull
	@Column(name = "atualizado_em", nullable = false)
	private LocalDateTime atualizadoEm;
}
