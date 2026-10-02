package com.codebusters.erp.domain.movimentacao_estoque;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.codebusters.erp.domain.produto.Produto;
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
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;

	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, length = 20)
	private String tipo;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 3)
	private BigDecimal quantidade;

	@NotBlank
	@Size(max = 30)
	@Column(nullable = false, length = 30)
	private String origem;

	@Column(name = "origem_id")
	private Long origemId;

	@NotNull
	@Column(name = "data_movimentacao", nullable = false)
	private LocalDateTime dataMovimentacao;

	@Column(columnDefinition = "TEXT")
	private String observacao;
}
